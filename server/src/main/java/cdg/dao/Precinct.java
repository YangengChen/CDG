package cdg.dao;

import cdg.dto.DistrictDTO;
import cdg.dto.PrecinctDTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table( name = "Precincts")
public class Precinct extends Region {
	@ManyToOne(fetch = FetchType.LAZY)
	private CongressionalDistrict conDistrict;
	@ManyToOne(fetch = FetchType.LAZY)
	private State state;
	private String county;
	
	public Precinct()
	{
		super();
	}
	public Precinct(String name, byte[] geoJsonGeometry, ElectionResult presidentialVoteTotals, CongressionalDistrict conDistrict, State state) {
		super(name, geoJsonGeometry, presidentialVoteTotals);
		this.conDistrict = conDistrict;
		this.state = state;
	}
	
	public CongressionalDistrict getConDistrict() {
		return conDistrict;
	}

	public void setConDistrict(CongressionalDistrict conDistrict) {
		this.conDistrict = conDistrict;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	
	/*
	 * Return first neighborboring precinct found
	 */
	public Precinct getFromNeighborConDistrict() {
		if (this.getNeighborRegions() == null) {
			return null;
		}
		CongressionalDistrict currDistrict = this.getConDistrict();
		if (currDistrict == null) {
			throw new IllegalStateException();
		}
		
		int currID = currDistrict.getId();
		Precinct neighborPrec;
		CongressionalDistrict neighborDistrict;
		for (Region neighbor : this.getNeighborRegions().values()) {
			neighborPrec = (Precinct) neighbor;
			neighborDistrict = neighborPrec.getConDistrict();
			if (neighborDistrict == null) {
				throw new IllegalStateException();
			}
			if (neighborDistrict.getId() != currID) {
				return neighborPrec;
			}
		}
		return null;
	}
	
	/*
	 * Return all neighborboring precincts in certain congressional district
	 */
	public List<Precinct> getNeighborsFromConDistrict(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		List<Precinct> neighbors = new ArrayList<>();
		if (this.getNeighborRegions() == null) {
			return neighbors;
		}
		
		int districtID = district.getId();
		Precinct neighborPrec;
		CongressionalDistrict neighborDistrict;
		for (Region neighbor : this.getNeighborRegions().values()) {
			neighborPrec = (Precinct) neighbor;
			neighborDistrict = neighborPrec.getConDistrict();
			if (neighborDistrict == null) {
				throw new IllegalStateException();
			}
			if (neighborDistrict.getId() == districtID) {
				neighbors.add(neighborPrec);
			}
		}
		return neighbors;
	}
	
	public boolean hasNeighborDistrict(CongressionalDistrict district) {
		if (district == null) {
			System.out.println("District is null");
			throw new IllegalArgumentException();
		}
		CongressionalDistrict currDistrict = this.getConDistrict();
		if (currDistrict == null) {
			System.out.println("current is null");
			throw new IllegalStateException();
		}
		if (currDistrict.getId() == district.getId()) {
			System.out.println("the districts are the same");
			return false;
		}
		if (this.getNeighborRegions() == null) {
			System.out.println("neighbor regions null");
			return false;
		}
		
		int distID = district.getId();
		Precinct neighborPrec;
		CongressionalDistrict neighborDistrict;
		for (Region neighbor : this.getNeighborRegions().values()) {
			neighborPrec = (Precinct) neighbor;
			neighborDistrict = neighborPrec.getConDistrict();
			if (neighborDistrict == null) {
				throw new IllegalStateException();
			}
			if (neighborDistrict.getId() == distID) {
				return true;
			}
		}
		System.out.println("through with no errors, returning false");
		return false;
	}
	
	public boolean isLonePrecinct() {
		if (this.getNeighborRegions() == null) {
			return true;
		}
		if (this.getConDistrict() == null) {
			throw new IllegalStateException();
		}
		
		int distID = this.getConDistrict().getId();
		Precinct neighborPrec;
		CongressionalDistrict neighborDistrict;
		for (Region neighbor : this.getNeighborRegions().values()) {
			neighborPrec = (Precinct) neighbor;
			neighborDistrict = neighborPrec.getConDistrict();
			if (neighborDistrict == null) {
				throw new IllegalStateException();
			}
			if (neighborDistrict.getId() == distID) {
				return false;
			}
		}
		return true;
	}

	@Override
	public DistrictDTO getDataDTO() {
		PrecinctDTO data = new PrecinctDTO();
		data.setID(this.getPublicID());
		data.setName(this.getName());
		data.setPopulation(this.getPopulation());
		if (conDistrict != null) {
			data.setDistrictID(conDistrict.getPublicID());
		}
		data.setPresidentialElection(this.getPresidentialVoteTotals());
		return data;
	}
}
