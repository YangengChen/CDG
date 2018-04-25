package cdg.dao;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.vividsolutions.jts.geom.Geometry;

import cdg.dto.DistrictDTO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyGroup;

import javax.persistence.JoinColumn;


@Entity
@Table(name = "Regions")
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "type")
public class Region {
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column(name="id", updatable = false, nullable = false)
	private int id;
	@Column(updatable = false, nullable = false)
	private String publicID;
	private String name;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@LazyGroup("geo")
	private byte[] geoJson;
	@Transient
	private Geometry geometry;
	@Transient
	private ElectionResult presidentialVoteTotals;
	@ManyToMany
    @JoinTable(
        name = "Neighbors", 
        joinColumns = { @JoinColumn(name = "neighborOneID", referencedColumnName = "id", nullable = false) }, 
        inverseJoinColumns = { @JoinColumn(name = "neighborTwoID", referencedColumnName = "id", nullable = false) }
    )
	private Map<Integer,Region> neighborRegions;
	
	public Region() {}
	
	public Region(String name, byte[] geoJson, ElectionResult presidentialVoteTotals) {
		this();
		this.name = name;
		this.geoJson = geoJson;
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

	public byte[] getGeoJson() {
		return geoJson;
	}

	public void setGeoJson(byte[] geoJsonGeometry) {
		this.geoJson = geoJsonGeometry;
	}
	
	public String getGeoJsonGeometry() {
		byte[] currGeom = getGeoJson();
		String jsonStr = convertGeoToString(currGeom);
		return jsonStr;
	}

	public void setGeoJsonGeometry(String geoJsonGeometry) {
		if (geoJsonGeometry == null) {
			setGeoJson(null);
			return;
		}
		byte[] currGeom = convertStringToGeo(geoJsonGeometry);
		setGeoJson(currGeom);
	}
	
	public static String convertGeoToString(byte[] currGeom) {
		if (currGeom == null) {
			return null;
		}
		String geom = new String(currGeom, StandardCharsets.UTF_8);
		return geom;
	}

	public static byte[] convertStringToGeo(String currGeom) {
		if (currGeom == null) {
			return null;
		}
		byte[] geom = currGeom.getBytes(StandardCharsets.UTF_8);
		return geom;
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
