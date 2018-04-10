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
		GeometryService.updateGeometry(state);
		fakeStates.put(1000, state);
		
		state = new State("washington",null,null);
		state.setPublicID(2000);
		fakeStates.put(2000, state);
		state = new State("wisconsin",null,null);
		state.setPublicID(3000);
		fakeStates.put(3000, state);
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
			name.setPublicId(state.getPublicID());
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
