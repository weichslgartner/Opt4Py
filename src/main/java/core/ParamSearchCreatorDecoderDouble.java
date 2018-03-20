package core;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;


public class ParamSearchCreatorDecoderDouble implements Creator<DoubleGenotype>, Decoder<DoubleGenotype, Collection<Double>> {

	public ParamSearchCreatorDecoderDouble(int number) {
		
	}
	
	@Override
	public DoubleGenotype create() {
		DoubleGenotype genotype = new DoubleGenotype();
		Random random = new Random();
		genotype.init(random, 1);
		return genotype;
	}
	
	@Override
	public Collection<Double> decode(DoubleGenotype genotype) {
		List<Double> valueList = new ArrayList<Double>();
		for (Double value : genotype) {
			valueList.add(value);
		}
		return valueList;
	}

}
