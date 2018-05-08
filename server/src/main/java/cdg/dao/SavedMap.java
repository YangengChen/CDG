package cdg.dao;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SavedMaps")
public class SavedMap {	
	@Id
	private String id;
	@ManyToOne(fetch = FetchType.LAZY, cascade= {})
	private State state;
	@OneToMany(fetch = FetchType.LAZY, cascade= {CascadeType.ALL}, orphanRemoval=true)
	@JoinColumn(name="id", referencedColumnName="id")
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
