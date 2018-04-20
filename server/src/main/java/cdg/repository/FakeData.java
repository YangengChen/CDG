package cdg.repository;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import cdg.dao.NameOnly;
import cdg.dao.NameOnlyFake;
import cdg.dao.State;
import cdg.services.ImportService;

@Repository
public class FakeData implements StateRepository {
	
	private Map<String,State> fakeStates;
	
	private void setFakeStates()
	{
		fakeStates = new HashMap<>();
		addFakeState("arizona_precincts.geojson");
		addFakeState("indiana_precincts.geojson");
		addFakeState("minnesota_precincts.geojson");
		addFakeState("mississippi_precincts.geojson");
	}
	
	private void addFakeState(String pathName) {
		String geoJson = null;
		
		try {
			Resource resource = new ClassPathResource(pathName);
			StringWriter writer = new StringWriter();
			IOUtils.copy(resource.getInputStream(), writer, StandardCharsets.UTF_8);
			geoJson = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		State state = ImportService.createState(geoJson);
		if (state == null) {
			return;
		}
		fakeStates.put(state.getPublicID(), state);
	}
	
	
	@Override
	public <T> T findByPublicId(String publicId, Class<T> type) {
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
		
		//filter out states with no maps
		List<State> results = new ArrayList<State>();
		for (State state : fakeStates.values())
		{
			if (state.getStateMapGeoJson() != null) {
				results.add(state);
			}
		}
		
		return results;
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
