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
	
	private Map<Integer,State> fakeStates;
	
	private void setFakeStates()
	{
		fakeStates = new HashMap<>();
		State state;
		//minnesota - good boundary data
		addFakeState("minnesota", 1000, "minnesota_precincts.geojson");
		
		//addFakeState("washington", 2000, "washington_precincts.geojson");
		state = new State("washington", null, null);
		state.setPublicID(2000);
		String geoJson = null;
		try {
			Resource resource = new ClassPathResource("washington_precincts.geojson");
			StringWriter writer = new StringWriter();
			IOUtils.copy(resource.getInputStream(), writer, StandardCharsets.UTF_8);
			geoJson = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		state.setPrecinctMapGeoJson(geoJson);
		fakeStates.put(state.getPublicID(), state);
		
		//addFakeState("wisconsin", 3000, "wisconsin_precincts.geojson");
		state = new State("wisconsin", null, null);
		state.setPublicID(3000);
		geoJson = null;
		try {
			Resource resource = new ClassPathResource("wisconsin_precincts.geojson");
			StringWriter writer = new StringWriter();
			IOUtils.copy(resource.getInputStream(), writer, StandardCharsets.UTF_8);
			geoJson = writer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		state.setPrecinctMapGeoJson(geoJson);
		fakeStates.put(state.getPublicID(), state);
	}
	
	private void addFakeState(String name, int publicID, String pathName) {
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
		State state = ImportService.createState(name, geoJson);
		if (state == null) {
			return;
		}
		state.setPublicID(publicID);
		fakeStates.put(publicID, state);
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
