package cdg.dao;

import java.util.HashMap;
import java.util.Map;

import cdg.dto.CongressionalDistrictDTO;
import cdg.dto.DistrictDTO;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.persistence.CascadeType;
import javax.persistence.MapKey;

@Entity
@Table (name = "Districts")
public class CongressionalDistrict extends Region {
	@ManyToOne
	private State state;
	@OneToMany(mappedBy="conDistrict", cascade= {CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval=true)
	@MapKey(name = "id")
	private Map<Integer,Precinct> precincts;
	@Transient
	private Map<Integer,Precinct> borderPrecincts;
	@Transient
	private double goodnessValue;
	
	public CongressionalDistrict()
	{
		super();
		borderPrecincts = new HashMap<>();
	}
	public CongressionalDistrict(String name, byte[] geoJsonGeometry, ElectionResult presidentialVoteTotals, State state, double goodnessValue) {
		super(name, geoJsonGeometry, presidentialVoteTotals);
		this.state = state;
		this.borderPrecincts = new HashMap<>();
		this.goodnessValue = goodnessValue;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Map<Integer, Precinct> getPrecincts() {
		return precincts;
	}

	public void setPrecincts(Map<Integer, Precinct> precincts) {
		this.precincts = precincts;
	}

	public Map<Integer, Precinct> getBorderPrecincts() {
		return borderPrecincts;
	}

	public void setBorderPrecincts(Map<Integer, Precinct> borderPrecincts) {
		this.borderPrecincts = borderPrecincts;
	}

	public double getGoodnessValue() {
		return goodnessValue;
	}

	public void setGoodnessValue(double goodnessValue) {
		this.goodnessValue = goodnessValue;
	}	

	@Override
	public DistrictDTO getDataDTO() {
		CongressionalDistrictDTO data = new CongressionalDistrictDTO();
		data.setID(this.getPublicID());
		data.setName(this.getName());
		if (precincts != null) {
			data.setNumPrecincts(precincts.size());
		}
		data.setGoodness(this.goodnessValue);
		return data;
	}

}
