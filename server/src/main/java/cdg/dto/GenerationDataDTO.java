package cdg.dto;

import cdg.domain.generation.GenerationConfiguration;

public class GenerationDataDTO extends MapDataDTO {
	private int generationID;
	private GenerationConfiguration configuration;
	
	/**
	 * @return the generationID
	 */
	public int getGenerationID() {
		return generationID;
	}
	/**
	 * @param generationID the generationID to set
	 */
	public void setGenerationID(int generationID) {
		this.generationID = generationID;
	}
	/**
	 * @return the configuration
	 */
	public GenerationConfiguration getConfiguration() {
		return configuration;
	}
	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(GenerationConfiguration configuration) {
		this.configuration = configuration;
	}
}
