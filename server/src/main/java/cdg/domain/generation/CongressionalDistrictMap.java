package cdg.domain.generation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import cdg.dao.CongressionalDistrict;
import cdg.dao.State;

public class CongressionalDistrictMap {
	private State state;
	private BiMap<Integer,CongressionalDistrict> districts;
	private Map<Integer,CongressionalDistrict> ignoredDistricts;
	private PriorityQueue<CongressionalDistrict> lowestGoodnessDistrict;
	private Map<Integer,LinkedList<Integer>> borderPrecinctQueues;
	private Random randGenerator;
	private final int MAXID;
	
	//@Autowired
	//StateRepository stateRepo;
	
	public CongressionalDistrictMap(State state, GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval) {
		if (goodnessEval == null || constraintEval == null || state == null) {
			throw new IllegalArgumentException();
		}
		this.state = state;
		validateEvaluators();
		MAXID = generateCustomMap(state.getConDistricts().values());
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
	
	private void validateEvaluators() {
		
	}
	
	private void initMap() {
		mapPrecincts();
		mapBorderPrecincts();
		mapNeighborPrecincts();
	}
	
	private void mapPrecincts()
	{

	}
	
	private void mapBorderPrecincts()
	{
		
	}
	
	private void mapNeighborPrecincts()
	{
		
	}
	
	private void initHelpers(ConstraintEvaluator evaluator)
	{
		ignoredDistricts = new HashMap<Integer,CongressionalDistrict>();
		lowestGoodnessDistrict = new PriorityQueue<CongressionalDistrict>(districts.size(), new GoodnessComparator());
		
		Set<Map.Entry<Integer,CongressionalDistrict>> districtsSet = districts.entrySet();
		boolean constraintsMet;
		for (Map.Entry<Integer,CongressionalDistrict> district : districtsSet) {
			constraintsMet = evaluator.meetsConstraints(district.getValue());
			if (!constraintsMet) {
				ignoredDistricts.put(district.getKey(), district.getValue());
			} else {
				lowestGoodnessDistrict.add(district.getValue());
			}
		}
	}
	
	private CongressionalDistrict getIgnoredDistrict(int districtID)
	{
		return null;
	}
	
	
	public double evaluateGoodness(int districtID, GoodnessEvaluator evaluator)
	{
		return -1;
	}
	
	public double getGoodness(int districtId)
	{
		CongressionalDistrict district = districts.get(districtId);
		if (district == null) {
			return -1;
		}
		return district.getGoodnessValue();
	}
	
	public double getTotalGoodness()
	{
		return -1;
	}
	
	public int getRandomDistrict() 
	{
		return -1;
	}
	
	public int getLowestGoodnessDistrict() 
	{
		return -1;
	}
	
	public void resetPrecinctQueue(int districtID)
	{
		
	}
	
	public int getNextCandidatePrecinct(int districtId, ConstraintEvaluator evaluator)
	{
		return -1;
	}
	
	public int movePrecinct(int districtIDFrom, int districtIDTo, int precinctID)
	{
		return -1;
	}
	
	private void evaluateAllGoodness(GoodnessEvaluator goodnessEval)
	{
		Set<Integer> districtsKeySet = districts.keySet();
		for (int key : districtsKeySet) {
			evaluateGoodness(key, goodnessEval);
		}
	}
	
	private void updateGoodnesssQueue(CongressionalDistrict district)
	{
		
	}
	
	public int getDistrictID(CongressionalDistrict district)
	{
		return -1;
	}
	
	private Iterator<CongressionalDistrict> getDistrictIterator()
	{
		return districts.values().iterator();
	}
}
