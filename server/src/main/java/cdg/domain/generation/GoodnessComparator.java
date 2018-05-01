package cdg.domain.generation;

import java.util.Comparator;

import cdg.dao.CongressionalDistrict;

public class GoodnessComparator implements Comparator<CongressionalDistrict> {
	@Override
	public int compare(CongressionalDistrict o1, CongressionalDistrict o2) {
		double goodness1 = o1.getGoodnessValue();
		double goodness2 = o2.getGoodnessValue();
		if (goodness1 < goodness2) {
			return -1;
		} else if (goodness1 > goodness2) {
			return 1;
		} else {
			return 0;
		}
	}
}
