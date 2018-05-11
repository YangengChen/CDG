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
import org.springframework.beans.factory.annotation.Autowired;

import cdg.dao.NameOnly;
import cdg.dao.NameOnlyFake;
import cdg.dao.State;
import cdg.services.ImportService;

@Repository
public class FakeData {
	
	@Autowired
	ImportService importService;
	
	private Map<String,State> fakeStates;
	
	public void  initialize() {
		if (fakeStates == null) {
			setFakeStates();
		}
	}
	
	private void setFakeStates()
	{
		fakeStates = new HashMap<>();
		addFakeState("arizona_precincts.geojson");
		addFakeState("indiana_precincts.geojson");
		addFakeState("minnesota_precincts.geojson");
		addFakeState("mississippi_precincts.geojson");
		addFakeState("washington_precincts.geojson");
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
		State state = importService.createState(geoJson);
		if (state == null) {
			return;
		}
		fakeStates.put(state.getPublicID(), state);
	}
	
	public <T> T findByPublicId(String publicId, Class<T> type) {
		initialize();
		
		if (type.equals(State.class))
		{
			return (T) fakeStates.get(publicId);
		}
		else
			return null;
	}
	
	public Iterable<State> findAll()
	{
		initialize();
		
		return fakeStates.values();
	}
	
	public Collection<NameOnly> findAllProjectedBy()
	{
		initialize();
		
		List<NameOnly> result = new ArrayList<NameOnly>();
		NameOnlyFake name;
		for (State state : fakeStates.values())
		{
			name = new NameOnlyFake();
			name.setPublicID(state.getPublicID());
			name.setName(state.getName());
			result.add(name);
		}
		return result;
	}

	public List<State> findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
