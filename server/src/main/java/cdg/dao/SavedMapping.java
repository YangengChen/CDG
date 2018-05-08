package cdg.dao;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SavedMappings")
public class SavedMapping {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@ManyToOne(fetch = FetchType.LAZY, cascade= {})
	private CongressionalDistrict district;
	@OneToMany(fetch = FetchType.LAZY, cascade= {})
	@JoinColumn(name="id", referencedColumnName="id")
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
