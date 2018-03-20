package core;
import org.opt4j.core.genotype.Bounds;
import org.opt4j.core.genotype.IntegerBounds;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;
import com.google.inject.name.Names;

import jep.JepConfig;

public class ParamSearch extends ProblemModule {

	private int[] lower;
	private int[] upper;


	public ParamSearch(int[] lower, 
					  int[] upper) {
		this.lower = lower;
		this.upper = upper;
	}

	@Override
	protected void config() {
		
		Class<? extends Creator<?>> creator = ParamSearchCreatorDecoderInteger.class;
		Class<? extends Decoder<?, ?>> decoder = ParamSearchCreatorDecoderInteger.class;
		Class<? extends Evaluator<?>> evaluator = ParamSearchEvaluator.class;
		IntegerBounds bounds = new IntegerBounds(lower,upper);
		bindConstant("numberz",ParamSearchCreatorDecoderInteger.class).to(lower.length);

		bind(IntegerBounds.class).toInstance(bounds);
		bindProblem(creator, decoder, evaluator);

	}

}
