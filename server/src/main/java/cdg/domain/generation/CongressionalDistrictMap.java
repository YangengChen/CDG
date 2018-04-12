package cdg.domain.generation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import cdg.dao.CongressionalDistrict;
import cdg.dao.State;
import cdg.repository.FakeData;

public class CongressionalDistrictMap {
	private State state;
	private BiMap<Integer,CongressionalDistrict> districts;
	private PriorityQueue<CongressionalDistrict> lowestGoodnessDistrict;
	private Map<Integer,LinkedList<Integer>> borderPrecinctQueues;
	private Random randGenerator;
	private final int MAXID;
	//@Autowired
	//StateRepository stateRepo;
	
	@Autowired
	FakeData stateRepo;
	
	public CongressionalDistrictMap(int stateID, GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval) {
		if (goodnessEval == null || constraintEval == null) {
			throw new IllegalArgumentException();
		}
		state = stateRepo.findByPublicId(stateID, State.class);
		if (state == null) {
			throw new IllegalArgumentException();
		}
		
		MAXID = generateCustomMap(state.getConDistricts().values());
		initMap();
		evaluateAllGoodness(goodnessEval);
		initHelpers(constraintEval);
	}
	
	private int generateCustomMap(Iterable<CongressionalDistrict> districts) {
		int key = 1;
		BiMap<Integer,CongressionalDistrict> districtMap = HashBiMap.create();
		for (CongressionalDistrict district : districts) {
			districtMap.put(key++,district);
		}
		return (key - 1);
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
		return -1;
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
		
	}
	
	private void updateGoodnesssQueue(CongressionalDistrict district)
	{
		
	}
	
	public CongressionalDistrict getDistrict(int distID)
	{
		return null;
	}
	
	public int getDistrictID(CongressionalDistrict district)
	{
		return -1;
	}
	
	private Iterator<CongressionalDistrict> getDistrictIterator()
	{
		return null;
	}
}
