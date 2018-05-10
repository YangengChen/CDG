package cdg.domain.generation;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygonal;

import cdg.dao.CongressionalDistrict;
import cdg.dao.State;

public class CdgGoodnessEvaluator extends GoodnessEvaluator {

	public CdgGoodnessEvaluator(double maxGoodness) {
		super(maxGoodness);
	}
	
	public static GoodnessEvaluator toGoodnessEvaluator(double maxGoodness, GenerationConfiguration configuration) {
		if (configuration == null) {
			throw new IllegalArgumentException();
		}
		GoodnessEvaluator evaluator = new CdgGoodnessEvaluator(maxGoodness);
		if ((configuration.getCompactnessWeight() + configuration.getContiguityWeight() + configuration.getEqualPopWeight() +
				configuration.getPartisanFairWeight()) != 1) {
			throw new IllegalArgumentException();
		}
		evaluator.setGoodnessMeasure(GoodnessMeasure.COMPACTNESS, configuration.getCompactnessWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.CONTIGUITY, configuration.getContiguityWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.EQUALPOPULATION, configuration.getEqualPopWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.PARTISANFAIRNESS, configuration.getPartisanFairWeight());
		return evaluator;
	}
	
	public double calculateGoodness(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		double compactnessValue = evaluateCompactness(district);
		double contiguityValue = evaluateContiguity(district);
		double populationEqualityValue = evaluatePopulationEquality(district);
		double goodness = runObjectiveFunction(compactnessValue, contiguityValue, populationEqualityValue, 0, 0);
		return goodness;
	}
	
	/**
	 * Schwarzenburg compactness
	 * @param district Congressional district
	 * @return compactness value normalized between 0 and MAXGOODNESS
	 */
	public double evaluateCompactness(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		double compactnessValue;
		Geometry districtGeom = district.getGeometry();
		if (!(districtGeom instanceof Polygonal)) {
			throw new IllegalArgumentException();
		}
		double area = districtGeom.getArea();
		double perimeter = districtGeom.getLength();
		if (area == 0 || perimeter == 0) {
			throw new IllegalArgumentException();
		}
		double equalARadius = Math.sqrt(area/Math.PI);
		double circumferance = 2 * Math.PI * equalARadius;
		double schwarzenburgScore = 1 / (perimeter/circumferance);
		compactnessValue = MAXGOODNESS * schwarzenburgScore;
		return compactnessValue;
	}
	
	/**
	 * 
	 * @param district Congressional district
	 * @return contiguity value normalized between 0 and MAXGOODNESS
	 */
	public double evaluateContiguity(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		double contiguityValue;
		Geometry districtGeom = district.getGeometry();
		if (districtGeom instanceof Polygonal) {
			contiguityValue = MAXGOODNESS/districtGeom.getNumGeometries();
		} else {
			throw new IllegalArgumentException();
		}
 		return contiguityValue;
	}
	
	public double evaluatePopulationEquality(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		double populationEqualityValue;
		State state = district.getState();
		if (state == null || state.numConDistricts() == 0) {
			throw new IllegalArgumentException();
		}
		long popAvg = state.getPopulation() / state.numConDistricts();
		long population = district.getPopulation();
		double percentage = (double)population / (double)popAvg;
		if (Math.abs(1 - percentage) > 1) {
			populationEqualityValue = 0;
		} else {
			populationEqualityValue = MAXGOODNESS - MAXGOODNESS * Math.abs(1 - percentage);
		}
		return populationEqualityValue;
	}
	
	public double evaluatePartisanFairness(CongressionalDistrict district)
	{
		return 0;
	}
	
	public double evaluateRacialFairness(CongressionalDistrict district)
	{
		return 0;
	}
	
	private double runObjectiveFunction(double compactness, double contiguity, double populationEqual, double partisanFair, double racialFair) 
	{
		double totalGoodness = 0;
		double weight = 0;
		int totalMeasures = 0;
		
		weight = this.getGoodnessMeasure(GoodnessMeasure.COMPACTNESS);
		if (weight > 0) {
			totalGoodness += (weight * compactness);
			totalMeasures++;
		}
		
		weight = this.getGoodnessMeasure(GoodnessMeasure.CONTIGUITY);
		if (weight > 0) {
			totalGoodness += (weight * contiguity);
			totalMeasures++;
		}
		
		weight = this.getGoodnessMeasure(GoodnessMeasure.EQUALPOPULATION);
		if (weight > 0) {
			totalGoodness += (weight * populationEqual);
			totalMeasures++;
		}
		
		weight = this.getGoodnessMeasure(GoodnessMeasure.PARTISANFAIRNESS);
		if (weight > 0) {
			totalGoodness += (weight * partisanFair);
			totalMeasures++;
		}
		
		weight = this.getGoodnessMeasure(GoodnessMeasure.RACIALFAIRNESS);
		if (weight > 0) {
			totalGoodness += (weight * racialFair);
			totalMeasures++;
		}

		double goodness = totalGoodness * (1/(double)totalMeasures);
		
		return goodness;
	}
}
