package cdg.domain.generation;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.BiMap;

import cdg.dao.CongressionalDistrict;
import cdg.dao.State;
import cdg.repository.StateRepository;

public class CongressionalDistrictMap {
	private State state;
	private BiMap<Integer,CongressionalDistrict> districts;
	private PriorityQueue<CongressionalDistrict> lowestGoodnessDistrict;
	private Map<Integer,LinkedList<Integer>> borderPrecinctQueues;
	private Random randGenerator;
	private final int MAXID;
	@Autowired
	StateRepository stateRepo;
	
	public CongressionalDistrictMap(int stateID, GoodnessEvaluator goodnessEval, ConstraintEvaluator constraintEval)
	{
		MAXID = 10;
	}
	
	private void initMap()
	{
		
	}
	
	private void initHelpers(ConstraintEvaluator evaluator)
	{
		
	}
	
	private CongressionalDistrict getIgnoredDistrict(int districtID)
	{
		return null;
	}
	
	private BiMap<Integer,CongressionalDistrict> generateCustomMap(List<CongressionalDistrict> districts)
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
	
	private void mapPrecincts()
	{
		
	}
	
	private void mapBorderPrecincts()
	{
		
	}
	
	private void mapNeighborPrecincts()
	{
		
	}
	
	private void evaluatorAllGoodness(GoodnessEvaluator goodnessEval)
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
