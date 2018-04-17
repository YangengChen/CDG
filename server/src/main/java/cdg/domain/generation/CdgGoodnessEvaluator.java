package cdg.domain.generation;

import cdg.dao.CongressionalDistrict;

public class CdgGoodnessEvaluator extends GoodnessEvaluator {

	public CdgGoodnessEvaluator(double maxGoodness) {
		super(maxGoodness);
	}
	
	public static GoodnessEvaluator toGoodnessEvaluator(double maxGoodness, GenerationConfiguration configuration) 
	{
		if (configuration == null) {
			throw new IllegalArgumentException();
		}
		GoodnessEvaluator evaluator = new CdgGoodnessEvaluator(maxGoodness);
		evaluator.setGoodnessMeasure(GoodnessMeasure.COMPACTNESS, configuration.getCompactnessWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.CONTIGUITY, configuration.getContiguityWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.EQUALPOPULATION, configuration.getEqualPopWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.PARTISANFAIRNESS, configuration.getPartisanFairWeight());
		evaluator.setGoodnessMeasure(GoodnessMeasure.RACIALFAIRNESS, configuration.getRacialFairWeight());
		return evaluator;
	}
	
	public double calculateGoodness(CongressionalDistrict district)
	{
		return 0;
	}
	
	public double evaluateCompactness(CongressionalDistrict district)
	{
		return 0;
	}
	
	public double evaluateContiguity(CongressionalDistrict district)
	{
		return 0;
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
		return 0;
	}
}
