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
	@ManyToOne
	private State state;
	@OneToMany(cascade= {CascadeType.ALL}, orphanRemoval=true)
	//@JoinColumn(referencedColumnName="id")
	//@JoinColumn(name="districts_id")
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
}
