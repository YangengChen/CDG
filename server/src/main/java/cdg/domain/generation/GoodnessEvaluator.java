package cdg.domain.generation;

import java.util.EnumMap;

import cdg.dao.CongressionalDistrict;

public class GoodnessEvaluator {
	private EnumMap<GoodnessMeasure,Double> goodnessMeasures;
	
	//fake
	public static final double MAX_GOODNESS = 100;
	
	public GoodnessEvaluator()
	{
		goodnessMeasures = new EnumMap<GoodnessMeasure,Double>(GoodnessMeasure.class);
	}
	
	public void setGoodnessMeasure(GoodnessMeasure measure, double weight)
	{
		
	}
	
	public void removeGoodnessMeasure(GoodnessMeasure measure)
	{
		
	}
	
	public static GoodnessEvaluator toGoodnessEvaluator(GenerationConfiguration configuration) 
	{
		return new GoodnessEvaluator();
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
	
	public double runObjectiveFunction(double compactness, double contiguity, double partisanFair, double racialFair) 
	{
		return 0;
	}
}
