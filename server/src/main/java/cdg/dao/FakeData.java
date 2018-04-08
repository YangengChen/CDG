package cdg.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class FakeData {
	//private static FakeData instance = null;
	
	private Map<Integer,State> fakeStates;
	
	/*private FakeData() {
		setFakeStates();
	}
	
	public static FakeData getInstance()
	{
		if (instance == null)
		{
			instance = new FakeData();		
		}
		return instance;
	}*/
	
	private void setFakeStates()
	{
		fakeStates = new HashMap<>();
		State state;
		Map<Integer,CongressionalDistrict> districts;
		Map<Integer,Precinct> precincts;
		CongressionalDistrict conDist;
		Precinct pre;
		
		state  = new State("minnesota", null, null);
		state.setPublicID(1000);
		districts = new HashMap<>();
		conDist = new CongressionalDistrict("Min 11", null, null, null, 100);
		conDist.setPublicID(11);
		districts.put(1, conDist);
		conDist = new CongressionalDistrict("Min 22", null, null, null, 200);
		conDist.setPublicID(22);
		districts.put(2, conDist);
		state.setConDistricts(districts);
		precincts = new HashMap<>();
		pre = new Precinct("Min 33", null, null, null, null);
		pre.setPublicID(33);
		precincts.put(3, pre);
		pre = new Precinct("Min 44", null, null, null, null);
		pre.setPublicID(44);
		precincts.put(4, pre);
		state.setPrecincts(precincts);
		fakeStates.put(1, state);
		

		fakeStates.put(2, new State("washington",null,null));
		fakeStates.put(3, new State("wisconsin",null,null));
	}
	
	public State findById(int id)
	{
		if (fakeStates == null)
			setFakeStates();
		
		return fakeStates.get(id);
	}
	
	public Iterable<State> findAll()
	{
		if (fakeStates == null)
			setFakeStates();
		
		return fakeStates.values();
	}
	
	public Iterable<NameOnly> findAllProjectedBy()
	{
		if (fakeStates == null)
			setFakeStates();
		
		List<NameOnly> result = new ArrayList<NameOnly>();
		NameOnlyFake name;
		for (State state : fakeStates.values())
		{
			name = new NameOnlyFake();
			name.setName(state.getName());
			result.add(name);
		}
		return result;
	}
}
