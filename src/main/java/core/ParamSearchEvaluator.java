package core;

import java.util.ArrayList;
import java.util.Collection;

import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

import jep.Jep;
import jep.JepConfig;
import jep.JepException;

public class ParamSearchEvaluator implements Evaluator<Collection<Integer>> {
	JepConfig cfg;

	@Inject
	public ParamSearchEvaluator(@Constant(namespace = ParamSearchEvaluator.class, value = "evalpath") String path) {

		cfg = new JepConfig();
		cfg.addSharedModules("numpy");
		cfg.addSharedModules("sklearn");
		cfg.addIncludePaths(path);
		cfg.addIncludePaths(".");
	}

	Objective acc = new Objective("acc", Sign.MAX);

	@Override
	public Objectives evaluate(Collection<Integer> phenotype) {
		double accuracy = 0;
		String paramPrefix = "arg";
		// Integer[] argArray = phenotype.toArray(new Integer[phenotype.size()]);
		Jep jep;
		try {
			jep = new Jep(cfg);
			jep.eval("from optPy import evaluator");

			// any of the following work, these are just pseudo-examples

			// using eval(String) to invoke methods
			int i = 0;
			String paramString = "";
			Collection<String> argList = new ArrayList<String>();
			for (int arg : phenotype) {
				String tmpParam = paramPrefix + i;
				argList.add(tmpParam);
				jep.set(tmpParam, arg);
				paramString += tmpParam + ",";
				i++;
			}
			if (paramString.length() > 1) {
				paramString = paramString.substring(0, paramString.length() - 1);
			}

			String pyFunctionCall = String.format("x = evaluator(%s)", paramString);
			jep.eval(pyFunctionCall);
			accuracy = (double) jep.getValue("x");
			System.out.println(accuracy);
			for (String arg : argList) {
				String delString = String.format("del %s", arg);
				jep.eval(delString);

			}

			jep.eval("del x");
			jep.close();
			// using runScript
			// jep.runScript("path/To/Script");
		} catch (JepException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Objectives objectives = new Objectives();
		objectives.add(acc, accuracy);
		return objectives;
	}

}
