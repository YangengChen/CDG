package cdg.domain.generation;

import java.util.EnumMap;

import cdg.dao.CongressionalDistrict;

public abstract class GoodnessEvaluator {
	protected final double MAXGOODNESS;
	private EnumMap<GoodnessMeasure,Double> goodnessMeasures;
	
	public GoodnessEvaluator(double maxGoodness) {
		if (maxGoodness <= 0) {
			throw new IllegalArgumentException();
		}
		MAXGOODNESS = maxGoodness;
		goodnessMeasures = new EnumMap<GoodnessMeasure,Double>(GoodnessMeasure.class);
	}
	
	public double getGoodnessMeasure(GoodnessMeasure measure) {
		if (measure == null) {
			throw new IllegalArgumentException();
		}
		Double weight = goodnessMeasures.get(measure);
		if (weight == null) {
			return 0;
		}
 		return weight;
	}
	
	public void setGoodnessMeasure(GoodnessMeasure measure, double weight)
	{
		if (measure == null || (weight < 0.0 || weight > 1.0)) {
			throw new IllegalArgumentException();
		}
		goodnessMeasures.put(measure, weight);
	}
	
	public void removeGoodnessMeasure(GoodnessMeasure measure)
	{
		if (measure == null) {
			throw new IllegalArgumentException();
		}
		goodnessMeasures.remove(measure);
	}
	
	public abstract double calculateGoodness(CongressionalDistrict district);
}
