package cdg.dao;

import cdg.dto.DistrictDTO;
import cdg.dto.PrecinctDTO;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.ManyToOne;

@Entity
@Table( name = "Precincts")
public class Precinct extends Region {
	@ManyToOne
	private CongressionalDistrict conDistrict;
	@ManyToOne
	private State state;
	
	public Precinct()
	{
		super();
	}
	public Precinct(String name, String geoJsonGeometry, ElectionResult presidentialVoteTotals, CongressionalDistrict conDistrict, State state) {
		super(name, geoJsonGeometry, presidentialVoteTotals);
		this.conDistrict = conDistrict;
		this.state = state;
	}
	
	/**
	 * @return the conDistrict
	 */
	public CongressionalDistrict getConDistrict() {
		return conDistrict;
	}
	/**
	 * @param conDistrict the conDistrict to set
	 */
	public void setConDistrict(CongressionalDistrict conDistrict) {
		this.conDistrict = conDistrict;
	}
	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public DistrictDTO getDataDTO() {
		PrecinctDTO data = new PrecinctDTO();
		data.setID(this.getPublicID());
		data.setName(this.getName());
		if (conDistrict != null) {
			data.setDistrictID(conDistrict.getPublicID());
		}
		return data;
	}
}
