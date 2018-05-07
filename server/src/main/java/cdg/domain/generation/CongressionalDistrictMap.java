package cdg.domain.generation;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.wololo.jts2geojson.GeoJSONReader;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.vividsolutions.jts.geom.Geometry;

import cdg.dao.CongressionalDistrict;
import cdg.dao.Precinct;
import cdg.dao.Region;
import cdg.dao.State;

public class CongressionalDistrictMap {
	private State state;
	private BiMap<Integer, CongressionalDistrict> districts;
	private Map<Integer, CongressionalDistrict> ignoredDistricts;
	private PriorityQueue<CongressionalDistrict> lowestGoodnessDistrict;
	private Map<Integer, LinkedList<Integer>> borderPrecinctQueues;
	private Random randGenerator;
	private final int MAXID;
	
	public CongressionalDistrictMap(State state, GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval) {
		if (goodnessEval == null || constraintEval == null || state == null) {
			throw new IllegalArgumentException();
		}
		this.state = state;
		validateEvaluators(goodnessEval, constraintEval);
		Map<Integer, CongressionalDistrict> allDistricts = state.getConDistricts();
		if (allDistricts == null || allDistricts.size() == 0) {
			throw new IllegalStateException();
		}
		MAXID = generateCustomMap(allDistricts.values());
		initMap();
		evaluateAllGoodness(goodnessEval);
		initHelpers(constraintEval);
	}
	
	private int generateCustomMap(Iterable<CongressionalDistrict> districts) {
		int key = 0;
		this.districts = HashBiMap.create();
		for (CongressionalDistrict district : districts) {
			this.districts.put(key++, district);
		}
		return (key - 1);
	}
	
	private void validateEvaluators(GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval) {
		
	}
	
	private void initMap() {
		mapPrecincts();
		mapBorderPrecincts();
	}
	
	/*
	 * Lazy load precincts for each congressional district. Each precinct object
	 * will be the only copy for the algorithm run. Make sure the same objects are
	 * used in bidirectional mappings
	 */
	private void mapPrecincts() {
		GeoJSONReader reader = new GeoJSONReader();
		Map<Integer, Precinct> allPrecincts = new HashMap<Integer, Precinct>();
		Map<Integer, Precinct> currPrecincts;
		for (CongressionalDistrict district : districts.values()) {
			mapGeometry(district, reader);
			currPrecincts = district.getPrecincts(); // lazy loaded
			if (currPrecincts == null) {
				throw new IllegalStateException();
			}
			allPrecincts.putAll(currPrecincts);
			for (Precinct precinct : currPrecincts.values()) {
				precinct.setConDistrict(district); // make sure the object is the same
				mapGeometry(precinct, reader);
			}
		}
		mapNeighborPrecincts(allPrecincts);
		state.setPrecincts(allPrecincts); //allow for retrieving maps and data later
	}
	
	private void mapGeometry(Region region, GeoJSONReader reader) {
		if (region.getGeoJsonGeometry() == null) {
			throw new IllegalStateException();
		}
		try {
			Geometry currGeom = reader.read(region.getGeoJsonGeometry());
			region.setGeometry(currGeom);
		} catch (Exception e) {
			throw new IllegalStateException();
		}
	}
	
	/*
	 * Lazy load neighbor precincts for each precinct. Re-map neighbors use the
	 * current pool of precinct objects.
	 */
	private void mapNeighborPrecincts(Map<Integer, Precinct> allPrecincts) {
		Map<Integer, Region> currNeighbors;
		Map<Integer, Region> remappedNeighbors;
		Precinct rmNeighbor;
		for (Precinct precinct : allPrecincts.values()) {
			currNeighbors = precinct.getNeighborRegions(); // lazy loaded
			if (currNeighbors == null) {
				continue;
			}
			remappedNeighbors = new HashMap<Integer, Region>();
			for (Region neighbor : currNeighbors.values()) {
				if (!(neighbor instanceof Precinct)) {
					throw new IllegalStateException();
				}
				rmNeighbor = allPrecincts.get(neighbor.getId());
				if (rmNeighbor == null) {
					throw new IllegalStateException();
				}
				remappedNeighbors.put(rmNeighbor.getId(), rmNeighbor);
			}
			precinct.setNeighborRegions(remappedNeighbors);
		}
	}
	
	private void mapBorderPrecincts() {
		Map<Integer, Precinct> precincts;
		Map<Integer, Precinct> borderPrecincts;
		for (CongressionalDistrict district : districts.values()) {
			precincts = district.getPrecincts();
			borderPrecincts = new HashMap<Integer, Precinct>();
			for (Precinct precinct : precincts.values()) {
				if (isBorderPrecinct(precinct)) {
					borderPrecincts.put(precinct.getId(), precinct);
				}
			}
			district.setBorderPrecincts(borderPrecincts);
		}
	}
	
	private boolean isBorderPrecinct(Precinct precinct) {
		CongressionalDistrict currDistrict = precinct.getConDistrict();
		Map<Integer, Region> neighbors = precinct.getNeighborRegions();
		for (Region neighbor : neighbors.values()) {
			if (((Precinct) neighbor).getConDistrict() != currDistrict) {
				return true;
			}
		}
		return false;
	}
	
	private void initHelpers(ConstraintEvaluator evaluator) {
		ignoredDistricts = new HashMap<Integer, CongressionalDistrict>();
		lowestGoodnessDistrict = new PriorityQueue<CongressionalDistrict>(districts.size(), new GoodnessComparator());

		Set<Map.Entry<Integer, CongressionalDistrict>> districtsSet = districts.entrySet();
		boolean constraintsMet;
		for (Map.Entry<Integer, CongressionalDistrict> district : districtsSet) {
			constraintsMet = evaluator.meetsConstraints(district.getValue());
			if (!constraintsMet) {
				ignoredDistricts.put(district.getKey(), district.getValue());
			} else {
				lowestGoodnessDistrict.add(district.getValue());
			}
		}
		if (districts.size() == ignoredDistricts.size()) {
			throw new IllegalStateException();
		}
		
		borderPrecinctQueues = new HashMap<Integer,LinkedList<Integer>>();
		randGenerator = new Random(0);
	}
	
	private CongressionalDistrict getIgnoredDistrict(int districtID) {
		return null;
	}

	public double evaluateGoodness(int districtID, GoodnessEvaluator evaluator) {
		CongressionalDistrict district = districts.get(districtID);
		if (district == null || evaluator == null) {
			throw new IllegalArgumentException();
		}
		double goodness = updateGoodness(district, evaluator);
		updateGoodnessQueue(district);
		return goodness;
	}
	
	private double updateGoodness(CongressionalDistrict district, GoodnessEvaluator evaluator) {
		double goodness = evaluator.calculateGoodness(district);
		district.setGoodnessValue(goodness);
		return goodness;
	}
	
	private void evaluateAllGoodness(GoodnessEvaluator goodnessEval) {
		Set<CongressionalDistrict> districtsSet = districts.values();
		for (CongressionalDistrict district : districtsSet) {
			updateGoodness(district, goodnessEval);
		}
	}
	
	private void updateGoodnessQueue(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		if (!lowestGoodnessDistrict.remove(district)) {
			throw new IllegalStateException();
		}
		lowestGoodnessDistrict.add(district);
	}
	
	public double getGoodness(int districtID) {
		CongressionalDistrict district = districts.get(districtID);
		if (district == null) {
			throw new IllegalArgumentException();
		}
		return district.getGoodnessValue();
	}
	
	public double getTotalGoodness() {
		double totalGoodness = 0;
		Set<Integer> districtsKeySet = districts.keySet();
		for (int key : districtsKeySet) {
			totalGoodness += getGoodness(key);
		}
		double normalizedGoodness = totalGoodness / districts.size();
		return normalizedGoodness;
	}
	
	public int getRandomDistrict() {
		int districtID = -1;
		CongressionalDistrict conDistrict;
		CongressionalDistrict ignoredDistrict;
		while (true) {
			int randInt = randGenerator.nextInt(MAXID + 1);
			conDistrict = districts.get(randInt);
			if (conDistrict == null) {
				throw new IllegalStateException();
			}
			ignoredDistrict = ignoredDistricts.get(randInt);
			if (ignoredDistrict == null) {
				districtID = randInt;
				break;
			}	
		}
		System.err.println(conDistrict.getName());
		return districtID;
	}
	
	public int getLowestGoodnessDistrict() {
		CongressionalDistrict conDistrict = lowestGoodnessDistrict.peek();
		if (conDistrict == null) {
			return -1;
		}
		int districtID = districts.inverse().get(conDistrict);
		return districtID;
	}
	
	public boolean resetPrecinctQueue(int districtID) {
		CongressionalDistrict currDistrict = districts.get(districtID);
		if (currDistrict == null) {
			return false;
		}
		int[] keys = currDistrict.getBorderPrecinctKeys();
		if (keys == null) {
			return false;
		}
		LinkedList<Integer> queue = new LinkedList<Integer>(Arrays.asList(ArrayUtils.toObject(keys)));
		borderPrecinctQueues.put(districtID, queue);
		return true;
	}
	
	public int getNextCandidatePrecinct(int districtID, ConstraintEvaluator evaluator) {
		CongressionalDistrict currDistrict = districts.get(districtID);
		if (currDistrict == null || evaluator == null) {
			throw new IllegalArgumentException();
		}
		LinkedList<Integer> queue = borderPrecinctQueues.get(districtID);
		if (queue == null) { 
			throw new IllegalStateException();
		}
		Precinct currBorderPrecinct;
		Integer currPrecinctID;
		boolean constraintsMet;
		while (!queue.isEmpty()) {
			currPrecinctID = queue.removeFirst();
			currBorderPrecinct = currDistrict.getPrecinct(currPrecinctID);
			if (currBorderPrecinct == null) {
				throw new IllegalStateException();
			}
			constraintsMet = evaluator.meetsConstraints(currBorderPrecinct);
			if(constraintsMet) {
				return currPrecinctID;
			}
		}
		return -1;
	}
	
	public int movePrecinct(int districtIDFrom, int districtIDTo, int precinctID) {
		CongressionalDistrict currDistrict = districts.get(districtIDFrom);
		CongressionalDistrict neighborDistrict = (districtIDTo < 0) ? null : districts.get(districtIDTo);
		if (currDistrict == null || (neighborDistrict == null && districtIDTo >= 0)) {
			throw new IllegalArgumentException();
		}
		Precinct currPrecinct;
		currPrecinct = currDistrict.removePrecinct(precinctID);
		if (currPrecinct == null) {
			throw new IllegalStateException();
		}
		if (neighborDistrict == null) {
			/* Should return the same neighboring precinct/congressional district determined through the ConstraintsEvaluator if the 
			 * precinct object has not changed since this was last called */
			Precinct neighborPrecinct = currPrecinct.getFromNeighborConDistrict();
			if (neighborPrecinct == null) {
				throw new IllegalStateException();
			}
			neighborDistrict = neighborPrecinct.getConDistrict();
		}
		if (!currPrecinct.hasNeighborDistrict(neighborDistrict)) {
			throw new IllegalStateException();
		}
		currPrecinct = neighborDistrict.addPrecinct(currPrecinct);
		if (currPrecinct == null) {
			throw new IllegalStateException();
		}

		Integer neighborID = districts.inverse().get(neighborDistrict);
		if (neighborID == null) {
			throw new IllegalStateException();
		}
		return neighborID;
	}
	
	public State getGeneratedState() {
		return state;
	}

	public int getDistrictID(CongressionalDistrict district) {
		return -1;
	}
	
	private Iterator<CongressionalDistrict> getDistrictIterator() {
		return districts.values().iterator();
	}
}
