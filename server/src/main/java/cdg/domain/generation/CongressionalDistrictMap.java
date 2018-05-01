package cdg.domain.generation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

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
		if (allDistricts == null) {
			throw new IllegalStateException();
		}
		MAXID = generateCustomMap(allDistricts.values());
		initMap();
		evaluateAllGoodness(goodnessEval);
		initHelpers(constraintEval);
	}

	private int generateCustomMap(Iterable<CongressionalDistrict> districts) {
		int key = 1;
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
				throw new IllegalStateException();
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
	}

	private CongressionalDistrict getIgnoredDistrict(int districtID) {
		return null;
	}

	public double evaluateGoodness(int districtID, GoodnessEvaluator evaluator) {
		return -1;
	}

	public double getGoodness(int districtID) {
		CongressionalDistrict district = districts.get(districtID);
		if (district == null) {
			return -1;
		}
		return district.getGoodnessValue();
	}

	public double getTotalGoodness() {
		return -1;
	}

	public int getRandomDistrict() 
	{
		int districtID = -1;
		CongressionalDistrict conDistrict;
		CongressionalDistrict ignoredDistrict;
		while (true) {
			int randInt = randGenerator.nextInt(MAXID);
			conDistrict = districts.get(randInt);
			ignoredDistrict = ignoredDistricts.get(randInt);
			if (conDistrict != null && ignoredDistrict == null) {
				ignoredDistricts.put(randInt, conDistrict);
				districtID = randInt;
				break;
			}
				
		}
		return districtID;
	}

	public int getLowestGoodnessDistrict() {
		CongressionalDistrict conDistrict = lowestGoodnessDistrict.poll();
		if (conDistrict == null)
			return -1;
		int districtID = districts.inverse().get(conDistrict);
		ignoredDistricts.put(districtID, conDistrict);
		return districtID;
	}

	public void resetPrecinctQueue(int districtID) {
		CongressionalDistrict currDistrict = districts.get(districtID);
		currDistrict.resetBorderIterator();
	}

	public int getNextCandidatePrecinct(int districtID, ConstraintEvaluator evaluator) {
		CongressionalDistrict currDistrict = districts.get(districtID);
		Iterator<Precinct> borderPrecinctIterator = currDistrict.getBorderPrecincts().getValues().iterator();
		Precinct currBorderPrecinct;
		boolean constraintsMet;
		int borderPrecinctID = -1;
		while(borderPrecinctIterator.hasNext()) {
			currBorderPrecinct = borderPrecinctIterator.next();
			constraintsMet = evaluator.meetsConstraint(currBorderPrecinct);
			if(constraintsMet) {
				borderPrecinctID = currBorderPrecinct.getId();
				break;
			}
		}
		return borderPrecinctID;
	}

	public int movePrecinct(int districtIDFrom, int districtIDTo, int precinctID) {
		return -1;
	}

	private void evaluateAllGoodness(GoodnessEvaluator goodnessEval) {
		Set<Integer> districtsKeySet = districts.keySet();
		for (int key : districtsKeySet) {
			evaluateGoodness(key, goodnessEval);
		}
	}

	private void updateGoodnesssQueue(CongressionalDistrict district) {

	}

	public int getDistrictID(CongressionalDistrict district) {
		return -1;
	}

	private Iterator<CongressionalDistrict> getDistrictIterator() {
		return districts.values().iterator();
	}
}
