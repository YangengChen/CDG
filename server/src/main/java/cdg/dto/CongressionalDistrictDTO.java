package cdg.dto;

public class CongressionalDistrictDTO extends DistrictDTO {
	private int numPrecincts;
	private double goodness;
	/**
	 * @return the numPrecincts
	 */
	public int getNumPrecincts() {
		return numPrecincts;
	}
	/**
	 * @param numPrecincts the numPrecincts to set
	 */
	public void setNumPrecincts(int numPrecincts) {
		this.numPrecincts = numPrecincts;
	}
	/**
	 * @return the goodness
	 */
	public double getGoodness() {
		return goodness;
	}
	/**
	 * @param goodness the goodness to set
	 */
	public void setGoodness(double goodness) {
		this.goodness = goodness;
	}
}
