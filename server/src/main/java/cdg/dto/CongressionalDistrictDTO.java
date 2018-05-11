package cdg.dto;

public class CongressionalDistrictDTO extends DistrictDTO {
	private int numPrecincts;
	private String representative;
	private double goodness;
	private double wastedVoteRatio;

	public int getNumPrecincts() {
		return numPrecincts;
	}

	public void setNumPrecincts(int numPrecincts) {
		this.numPrecincts = numPrecincts;
	}

	public double getGoodness() {
		return goodness;
	}

	public void setGoodness(double goodness) {
		this.goodness = goodness;
	}

	public double getWastedVoteRatio() {
		return wastedVoteRatio;
	}

	public void setWastedVoteRatio(double wastedVoteRatio) {
		this.wastedVoteRatio = wastedVoteRatio;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}
}
