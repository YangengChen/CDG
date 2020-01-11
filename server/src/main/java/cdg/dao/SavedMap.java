package cdg.dao;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SavedMaps")
public class SavedMap {	
	@Id
	private String id;
	private double schwarzbergWeight;
	private double hullRatioWeight;
	private double reockWeight;
	private double contiguityWeight;
	private double equalPopWeight;
	private double partisanFairWeight;
	private String name;
	@ManyToOne
	private State state;
	@OneToMany(cascade= {CascadeType.ALL}, orphanRemoval=true)
	@JoinTable
	(
		name="SavedMapMappings",
		joinColumns={ @JoinColumn(name="map_id", referencedColumnName="id", nullable = false) },
		inverseJoinColumns={ @JoinColumn(name="mapping_id", referencedColumnName="id", nullable = false) }
	)
	private Set<SavedMapping> districts;
	
	public SavedMap() {}
	
	public SavedMap(String uid) {
		id = uid;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public Set<SavedMapping> getDistricts() {
		return districts;
	}
	public void setDistricts(Set<SavedMapping> districts) {
		this.districts = districts;
	}

	public double getSchwarzbergWeight() {
		return schwarzbergWeight;
	}

	public void setSchwarzbergWeight(double schwarzbergWeight) {
		this.schwarzbergWeight = schwarzbergWeight;
	}

	public double getHullRatioWeight() {
		return hullRatioWeight;
	}

	public void setHullRatioWeight(double hullRatioWeight) {
		this.hullRatioWeight = hullRatioWeight;
	}

	public double getReockWeight() {
		return reockWeight;
	}

	public void setReockWeight(double reockWeight) {
		this.reockWeight = reockWeight;
	}

	public double getContiguityWeight() {
		return contiguityWeight;
	}

	public void setContiguityWeight(double contiguityWeight) {
		this.contiguityWeight = contiguityWeight;
	}

	public double getEqualPopWeight() {
		return equalPopWeight;
	}

	public void setEqualPopWeight(double equalPopWeight) {
		this.equalPopWeight = equalPopWeight;
	}

	public double getPartisanFairWeight() {
		return partisanFairWeight;
	}

	public void setPartisanFairWeight(double partisanFairWeight) {
		this.partisanFairWeight = partisanFairWeight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
