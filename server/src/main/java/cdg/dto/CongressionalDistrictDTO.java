package cdg.dto;

public class CongressionalDistrictDTO extends DistrictDTO {
	private int numPrecincts;
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
}
