package core;
import org.opt4j.core.genotype.IntegerBounds;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.ProblemModule;

public class ParamSearch extends ProblemModule {

	private int[] lower;
	private int[] upper;
	private String pyPath;


	public ParamSearch(int[] lower, 
					  int[] upper, 
					  String path) {
		this.lower = lower;
		this.upper = upper;
		this.pyPath = path;
	}

	@Override
	protected void config() {
		
		Class<? extends Creator<?>> creator = ParamSearchCreatorDecoderInteger.class;
		Class<? extends Decoder<?, ?>> decoder = ParamSearchCreatorDecoderInteger.class;
		Class<? extends Evaluator<?>> evaluator = ParamSearchEvaluator.class;
		IntegerBounds bounds = new IntegerBounds(lower,upper);
		bindConstant("numberz",ParamSearchCreatorDecoderInteger.class).to(lower.length);
		bindConstant("evalpath",ParamSearchEvaluator.class).to(this.pyPath);
		bind(IntegerBounds.class).toInstance(bounds);
		bindProblem(creator, decoder, evaluator);

	}

}
