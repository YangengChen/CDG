package cdg.dao;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SavedMappings")
public class SavedMapping {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private CongressionalDistrict district;
	@ManyToMany
	@JoinTable
	(
		name="MappingPrecincts",
		joinColumns={ @JoinColumn(name="mapping_id", referencedColumnName="id", nullable = false) },
		inverseJoinColumns={ @JoinColumn(name="precinct_id", referencedColumnName="id", nullable = false) }
	)
	private Set<Precinct> precincts;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public CongressionalDistrict getDistrict() {
		return district;
	}
	public void setDistrict(CongressionalDistrict district) {
		this.district = district;
	}
	public Set<Precinct> getPrecincts() {
		return precincts;
	}
	public void setPrecincts(Set<Precinct> precincts) {
		this.precincts = precincts;
	}
}
