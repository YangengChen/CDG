package cdg.dto;

import cdg.dao.ElectionResult;

public class DistrictDTO {
	private String ID;
	private String name;
	private long population;
	private ElectionResult presidentialElection;
	
	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public long getPopulation() {
		return population;
	}

	public void setPopulation(long population) {
		this.population = population;
	}

	public ElectionResult getPresidentialElection() {
		return presidentialElection;
	}

	public void setPresidentialElection(ElectionResult presidentialElection) {
		this.presidentialElection = presidentialElection;
	}
}
