package cdg.dao;

import java.util.HashMap;
import java.util.Map;

import cdg.dto.CongressionalDistrictDTO;
import cdg.dto.DistrictDTO;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.ArrayUtils;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.TopologyException;

import javax.persistence.CascadeType;
import javax.persistence.MapKey;

@Entity
@Table (name = "Districts")
public class CongressionalDistrict extends Region {
	@ManyToOne(fetch = FetchType.LAZY)
	private State state;
	@OneToMany(mappedBy="conDistrict", cascade= {CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval=true)
	@MapKey(name = "id")
	private Map<Integer,Precinct> precincts;
	@Transient
	private Map<Integer,Precinct> borderPrecincts;
	@Transient
	private double goodnessValue;
	
	public CongressionalDistrict()
	{
		super();
		borderPrecincts = new HashMap<>();
	}
	public CongressionalDistrict(String name, byte[] geoJsonGeometry, ElectionResult presidentialVoteTotals, State state, double goodnessValue) {
		super(name, geoJsonGeometry, presidentialVoteTotals);
		this.state = state;
		this.borderPrecincts = new HashMap<>();
		this.goodnessValue = goodnessValue;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Map<Integer, Precinct> getPrecincts() {
		return precincts;
	}
	
	public Precinct getPrecinct(int precinctID) {
		if (getPrecincts() == null) {
			return null;
		}
		Precinct p = precincts.get(precinctID);
		return p;
	}

	public void setPrecincts(Map<Integer, Precinct> precincts) {
		this.precincts = precincts;
	}

	public Map<Integer, Precinct> getBorderPrecincts() {
		return borderPrecincts;
	}
	
	public int[] getBorderPrecinctKeys() {
		if (getBorderPrecincts() == null) {
			return null;
		}
		int[] keys = ArrayUtils.toPrimitive(borderPrecincts.keySet().toArray(new Integer[0]));
		return keys;
	}

	public void setBorderPrecincts(Map<Integer, Precinct> borderPrecincts) {
		this.borderPrecincts = borderPrecincts;
	}

	public double getGoodnessValue() {
		return goodnessValue;
	}

	public void setGoodnessValue(double goodnessValue) {
		this.goodnessValue = goodnessValue;
	}	
	
	/**
	 * Adds neighbor precinct to this district.  Updates the JTS geometry for the district and adds to the border.
	 * @param precinct precinct to add
	 * @postcondition only the precinct's congressional district mapping is changed
	 * @return added precinct
	 */
	public Precinct addPrecinct(Precinct precinct) {
		if (getPrecincts() == null || borderPrecincts == null) {
			throw new IllegalStateException();
		}
		Precinct currPrecinct = precincts.get(precinct.getId());
		if (currPrecinct != null) {
			return null;
		}
		
		try {
			if (isNeighborPrecinct(precinct)) {
				addBorderPrecinct(precinct);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
		precinct.setConDistrict(this);
		precincts.put(precinct.getId(), precinct);
		return precinct;
	}
	
	private boolean isNeighborPrecinct(Precinct precinct) {
		if (precinct == null) {
			throw new IllegalArgumentException();
		}
		if (precinct.getConDistrict() == null || precinct.getNeighborRegions() == null) {
			throw new IllegalStateException();
		}
		int currID = this.getId();
		if (precinct.getConDistrict().getId() == currID) {
			return false;
		}
		Precinct neighborPrec;
		CongressionalDistrict neighborDistrict;
		for (Region neighbor : precinct.getNeighborRegions().values()) {
			neighborPrec = (Precinct) neighbor;
			neighborDistrict = neighborPrec.getConDistrict();
			if (neighborDistrict == null) {
				throw new IllegalStateException();
			}
			if (neighborDistrict.getId() == currID) {
				return true;
			}
		}
		return false;
	}
	
	private void addBorderPrecinct(Precinct precinct) {
		if (precinct == null) {
			throw new IllegalArgumentException();
		}
		borderPrecincts.put(precinct.getId(), precinct);
		addToGeometry(precinct);
	}
	
	private void addToGeometry(Precinct precinct) {
		if (precinct == null) {
			throw new IllegalArgumentException();
		}
		Geometry currGeom = this.getGeometry();
		Geometry preGeom = precinct.getGeometry();
		if (currGeom == null || preGeom == null) {
			throw new IllegalStateException();
		}
		try {
			currGeom = currGeom.union(preGeom);
		} catch (TopologyException te) {
			throw new IllegalStateException();
		}
		this.setGeometry(currGeom);
			
		//check if adding precinct geometry removes any border precincts (possible from the precinct's neighbors)
		if (precinct.getNeighborRegions() == null) {
			throw new IllegalStateException();
		}
		int currID = this.getId();
		Precinct neighborPrec;
		CongressionalDistrict neighborDistrict;
		for (Region neighbor : precinct.getNeighborRegions().values()) {
			neighborPrec = (Precinct) neighbor;
			neighborDistrict = neighborPrec.getConDistrict();
			if (neighborDistrict == null) {
				throw new IllegalStateException();
			}
			//if neighbor's district is the current district and if none of the neighbor's neighbors belong to another district, remove from border precincts
			if ((neighborDistrict.getId() == currID) && !onBorder(neighborPrec, precinct)) {
				if (borderPrecincts.remove(neighborPrec.getId()) == null) {
					throw new IllegalStateException();
				}
			}
		}
	}
	
	/* precinct was a border precinct on the district (check if it is still on the border) */
	private boolean onBorder(Precinct precinct, Precinct addedPrec) {
		int currID = this.getId();
		Precinct neighborPrec;
		CongressionalDistrict neighborDistrict;
		for (Region neighbor : precinct.getNeighborRegions().values()) {
			neighborPrec = (Precinct) neighbor;
			neighborDistrict = neighborPrec.getConDistrict();
			if (neighborDistrict == null) {
				throw new IllegalStateException();
			}
			//if there is a neighbor that is not the added precinct and belongs to a different district, the added precinct is on the border
			if ((neighborPrec.getId() != addedPrec.getId()) && (neighborDistrict.getId() != currID)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes border precinct from this district.  Updates the JTS geometry for the district.
	 * @param precinctID border precinct ID to remove
	 * @postcondition the precinct object for precinctID remains unchanged
	 * @return removed precinct corresponding to precinctID
	 */
	public Precinct removePrecinct(int precinctID) {
		if (getPrecincts() == null || borderPrecincts == null) {
			throw new IllegalStateException();
		}
		Precinct currPrecinct = precincts.get(precinctID);
		if (currPrecinct == null) {
			return null;
		}
		if (isBorderPrecinct(precinctID)) {
			try {
				removeBorderPrecinct(precinctID);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException();
			}
		} else {
			return null;
		}
		currPrecinct = precincts.remove(precinctID);
		return currPrecinct;
	}
	
	public boolean isBorderPrecinct(int precinctID) {
		boolean isBorder = borderPrecincts.get(precinctID) == null ? false : true;
		return isBorder;
	}
	
	private void removeBorderPrecinct(int precinctID) {
		Precinct borderPrecinct = borderPrecincts.get(precinctID);
		if (borderPrecinct == null) {
			throw new IllegalArgumentException();
		}
		borderPrecincts.remove(precinctID);
		removeFromGeometry(borderPrecinct);
	}
	
	private void removeFromGeometry(Precinct precinct) {
		if (precinct == null) {
			throw new IllegalArgumentException();
		}
		Geometry currGeom = this.getGeometry();
		Geometry preGeom = precinct.getGeometry();
		if (currGeom == null || preGeom == null) {
			throw new IllegalStateException();
		}
		try {
			currGeom = currGeom.difference(preGeom);
		} catch (TopologyException te) {
			throw new IllegalStateException();
		}
		this.setGeometry(currGeom);
			
		//check if removing precinct geometry adds new border precincts (possible from the precinct's neighbors)
		if (precinct.getNeighborRegions() == null) {
			throw new IllegalStateException();
		}
		int currID = this.getId();
		Precinct neighborPrec;
		CongressionalDistrict neighborDistrict;
		for (Region neighbor : precinct.getNeighborRegions().values()) {
			neighborPrec = (Precinct) neighbor;
			neighborDistrict = neighborPrec.getConDistrict();
			if (neighborDistrict == null) {
				throw new IllegalStateException();
			}
			//if neighbor's district is the current district then it is a border precinct on the current district
			if (neighborDistrict.getId() == currID) {
				borderPrecincts.put(neighborPrec.getId(), neighborPrec);
			}
		}
	}

	@Override
	public DistrictDTO getDataDTO() {
		CongressionalDistrictDTO data = new CongressionalDistrictDTO();
		data.setID(this.getPublicID());
		data.setName(this.getName());
		if (precincts != null) {
			data.setNumPrecincts(precincts.size());
		}
		data.setGoodness(this.goodnessValue);
		return data;
	}

}
