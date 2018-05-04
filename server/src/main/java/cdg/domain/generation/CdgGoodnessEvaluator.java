package cdg.domain.generation;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygonal;

import cdg.dao.CongressionalDistrict;

public class CdgGoodnessEvaluator extends GoodnessEvaluator {

	public CdgGoodnessEvaluator(double maxGoodness) {
		super(maxGoodness);
	}
	
	public static GoodnessEvaluator toGoodnessEvaluator(double maxGoodness, GenerationConfiguration configuration) {
		if (configuration == null) {
			throw new IllegalArgumentException();
		}
		GoodnessEvaluator evaluator = new CdgGoodnessEvaluator(maxGoodness);
		if ((configuration.getCompactnessWeight() + configuration.getContiguityWeight() +
				configuration.getPartisanFairWeight() + configuration.getRacialFairWeight()) != 1) {
			throw new IllegalArgumentException();
		}
		evaluator.setGoodnessMeasure(GoodnessMeasure.COMPACTNESS, configuration.getCompactnessWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.CONTIGUITY, configuration.getContiguityWeight());
		//evaluator.setGoodnessMeasure(GoodnessMeasure.EQUALPOPULATION, configuration.getEqualPopWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.PARTISANFAIRNESS, configuration.getPartisanFairWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.RACIALFAIRNESS, configuration.getRacialFairWeight());
		return evaluator;
	}
	
	public double calculateGoodness(CongressionalDistrict district) {
		if (district == null) {
			throw new IllegalArgumentException();
		}
		double compactnessValue = evaluateCompactness(district);
		double contiguityValue = evaluateContiguity(district);
		double goodness = runObjectiveFunction(compactnessValue, contiguityValue, 0, 0);
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
	
	public double evaluatePartisanFairness(CongressionalDistrict district)
	{
		return 0;
	}
	
	public double evaluateRacialFairness(CongressionalDistrict district)
	{
		return 0;
	}
	
	private double runObjectiveFunction(double compactness, double contiguity, double partisanFair, double racialFair) 
	{
		double totalGoodness = 0;
		double weight = 0;
		
		weight = this.getGoodnessMeasure(GoodnessMeasure.COMPACTNESS);
		totalGoodness += (weight * compactness);
		
		weight = this.getGoodnessMeasure(GoodnessMeasure.CONTIGUITY);
		totalGoodness += (weight * contiguity);
		
		weight = this.getGoodnessMeasure(GoodnessMeasure.PARTISANFAIRNESS);
		totalGoodness += (weight * partisanFair);
		
		weight = this.getGoodnessMeasure(GoodnessMeasure.RACIALFAIRNESS);
		totalGoodness += (weight * racialFair);

		double goodness = totalGoodness * 0.25;
		
		return goodness;
	}
}
