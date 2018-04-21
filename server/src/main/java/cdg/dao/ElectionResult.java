package cdg.dao;

import java.util.HashMap;
import java.util.Map;

import cdg.domain.region.Party;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table( name = "ElectionResults")
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
