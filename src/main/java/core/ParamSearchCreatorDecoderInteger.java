package core;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.opt4j.core.genotype.Bounds;
import org.opt4j.core.genotype.IntegerBounds;
import org.opt4j.core.genotype.IntegerGenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

public class ParamSearchCreatorDecoderInteger
		implements Creator<IntegerGenotype>, Decoder<IntegerGenotype, Collection<Integer>> {

	private int number;
	private Bounds<Integer> bounds;

	@Inject
	public ParamSearchCreatorDecoderInteger(
			@Constant(namespace = ParamSearchCreatorDecoderInteger.class, value = "numberz") int number,
			IntegerBounds bounds) {
		this.number = number;
		this.bounds = bounds;
	}

	@Override
	public IntegerGenotype create() {
		IntegerGenotype genotype = new IntegerGenotype(this.bounds);
		Random random = new Random();
		genotype.init(random, this.number);
		return genotype;
	}

	@Override
	public Collection<Integer> decode(IntegerGenotype genotype) {
		List<Integer> valueList = new ArrayList<Integer>();
		for (Integer value : genotype) {
			valueList.add(value);
		}
		return valueList;
	}

}
