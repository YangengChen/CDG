package cdg.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cdg.dao.CongressionalDistrict;
import cdg.dao.GeoFiles;
import cdg.dao.NameOnly;
import cdg.dao.NameOnlyFake;
import cdg.dao.Precinct;
import cdg.dao.State;
import cdg.domain.generation.GeometryService;

@Repository
public class FakeData implements StateRepository {
	
	private Map<Integer,State> fakeStates;
	
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
		GeometryService.updateGeometry(state);
		fakeStates.put(1000, state);
		

		fakeStates.put(2, new State("washington",null,null));
		fakeStates.put(3, new State("wisconsin",null,null));
	}
	
	
	@Override
	public <T> T findByPublicId(int publicId, Class<T> type) {
		if (type.equals(State.class))
		{
			if (fakeStates == null)
				setFakeStates();
			
			return (T) fakeStates.get(publicId);
		}
		else
			return null;
	}
	
	public Iterable<State> findAll()
	{
		if (fakeStates == null)
			setFakeStates();
		
		return fakeStates.values();
	}
	
	public Collection<NameOnly> findAllProjectedBy()
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

	@Override
	public List<State> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
