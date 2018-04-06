package cdg.dao;

import java.util.HashMap;
import java.util.Map;

import cdg.domain.region.Party;

public class ElectionResult {
	private Map<Party,Integer> voteTotals;
	
	public ElectionResult() 
	{
		voteTotals = new HashMap<>();
	}
	
	public void setTotal(Party party, int voteTotal)
	{
		voteTotals.put(party, voteTotal);
	}
	public Integer getTotal(Party party)
	{
		return voteTotals.get(party);
	}
}
