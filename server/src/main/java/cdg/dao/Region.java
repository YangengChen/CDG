package cdg.dao;

import java.util.HashMap;
import java.util.Map;

import cdg.dto.CongressionalDistrictDTO;
import cdg.dto.DistrictDTO;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


@Entity
@Table(name = "Regions")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "type")
public class Region {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int id;
	private String publicID;
	private String name;
	@Transient
	private String geoJsonGeometry;
	@Transient
	private ElectionResult presidentialVoteTotals;
	@Transient
	private Map<Integer,Region> neighborRegions;
	
	public Region()
	{
		neighborRegions = new HashMap<>();
	}
	public Region(String name, String geoJsonGeometry, ElectionResult presidentialVoteTotals) {
		this();
		this.name = name;
		this.geoJsonGeometry = geoJsonGeometry;
		this.presidentialVoteTotals = presidentialVoteTotals;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the geoJsonGeometry
	 */
	public String getGeoJsonGeometry() {
		return geoJsonGeometry;
	}

	/**
	 * @param geoJsonGeometry the geoJsonGeometry to set
	 */
	public void setGeoJsonGeometry(String geoJsonGeometry) {
		this.geoJsonGeometry = geoJsonGeometry;
	}

	/**
	 * @return the neighborRegions
	 */
	public Map<Integer, Region> getNeighborRegions() {
		return neighborRegions;
	}

	/**
	 * @param neighborRegions the neighborRegions to set
	 */
	public void setNeighborRegions(Map<Integer, Region> neighborRegions) {
		this.neighborRegions = neighborRegions;
	}
	/**
	 * @return the presidentialVoteTotals
	 */
	public ElectionResult getPresidentialVoteTotals() {
		return presidentialVoteTotals;
	}
	/**
	 * @param presidentialVoteTotals the presidentialVoteTotals to set
	 */
	public void setPresidentialVoteTotals(ElectionResult presidentialVoteTotals) {
		this.presidentialVoteTotals = presidentialVoteTotals;
	}
	/**
	 * @return the publicID
	 */
	public String getPublicID() {
		return publicID;
	}
	/**
	 * @param publicID the publicID to set
	 */
	public void setPublicID(String publicID) {
		this.publicID = publicID;
	}

	public DistrictDTO getDataDTO()
	{
		DistrictDTO data = new DistrictDTO();
		data.setID(this.getPublicID());
		data.setName(this.getName());
		return data;
	}
}
