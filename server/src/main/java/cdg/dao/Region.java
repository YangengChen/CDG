package cdg.dao;

import java.util.HashMap;
import java.util.Map;

import com.vividsolutions.jts.geom.Geometry;

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
	private Geometry geometry;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGeoJsonGeometry() {
		return geoJsonGeometry;
	}

	public void setGeoJsonGeometry(String geoJsonGeometry) {
		this.geoJsonGeometry = geoJsonGeometry;
	}

	public Geometry getGeometry() {
		return geometry;
	}
	
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public Map<Integer, Region> getNeighborRegions() {
		return neighborRegions;
	}

	public void setNeighborRegions(Map<Integer, Region> neighborRegions) {
		this.neighborRegions = neighborRegions;
	}

	public ElectionResult getPresidentialVoteTotals() {
		return presidentialVoteTotals;
	}

	public void setPresidentialVoteTotals(ElectionResult presidentialVoteTotals) {
		this.presidentialVoteTotals = presidentialVoteTotals;
	}

	public String getPublicID() {
		return publicID;
	}

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
