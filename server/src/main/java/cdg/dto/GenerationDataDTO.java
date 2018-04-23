package cdg.dto;

import cdg.domain.generation.GenerationConfiguration;

public class GenerationDataDTO extends MapDataDTO {
	private int generationID;
	private GenerationConfiguration configuration;

	public int getGenerationID() {
		return generationID;
	}

	public void setGenerationID(int generationID) {
		this.generationID = generationID;
	}

	public GenerationConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(GenerationConfiguration configuration) {
		this.configuration = configuration;
	}
}
