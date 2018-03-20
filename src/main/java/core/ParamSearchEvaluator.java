package core;
import java.util.Collection;

import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

import org.opt4j.core.problem.Evaluator;

import com.google.inject.Inject;

import jep.Jep;
import jep.JepConfig;
import jep.JepException;

public class ParamSearchEvaluator implements Evaluator<Collection<Integer>> {
	JepConfig cfg;
	
	
	public ParamSearchEvaluator() {

		cfg = new JepConfig();
        cfg.addSharedModules("numpy");
        cfg.addSharedModules("sklearn");
        cfg.addIncludePaths("C:\\Users\\andreas\\Desktop\\andreas\\opt4jpython\\Opt4Py\\src");
        cfg.addIncludePaths(".");
	}
	
	
	Objective acc = new Objective("acc", Sign.MAX);

	@Override
	public Objectives evaluate(Collection<Integer> phenotype) {
		double accuracy = 0;
		Integer[] argArray = phenotype.toArray(new Integer[phenotype.size()]);
		Jep jep;
		try {
			jep = new Jep(cfg);
			jep.eval("from opteval import evaluator");

			// any of the following work, these are just pseudo-examples

			// using eval(String) to invoke methods
			jep.set("arg1", argArray[0]);
			jep.eval("x = evaluator(arg1)");
			accuracy = (double) jep.getValue("x");
			System.out.println(accuracy);
			jep.eval("del arg1");
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
