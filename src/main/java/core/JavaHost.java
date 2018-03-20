package core;

import org.opt4j.core.Individual;
import org.opt4j.core.common.completer.IndividualCompleterModule;
import org.opt4j.core.common.completer.IndividualCompleterModule.Type;
import org.opt4j.core.config.Task;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.start.Opt4JTask;
import org.opt4j.optimizers.ea.EvolutionaryAlgorithmModule;
import org.opt4j.viewer.ViewerModule;


import py4j.GatewayServer;

public class JavaHost {

	private int number;
	private int[] lower;
	private int[] upper;
	private String pythonPath;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int[] getLower() {
		return lower;
	}

	public void setLower(int[] lower) {
		this.lower = lower;
	}

	public int[] getUpper() {
		return upper;
	}

	public void setUpper(int[] upper) {
		this.upper = upper;
	}

	public void run() {
		EvolutionaryAlgorithmModule ea = new EvolutionaryAlgorithmModule();
		ea.setGenerations(500);
		ea.setAlpha(1);
		IndividualCompleterModule comp = new IndividualCompleterModule(); //here comes the new module (the order does not matter)
		comp.setType(Type.PARALLEL);
		ParamSearch params = new ParamSearch(this.lower, this.upper, this.pythonPath);

		ViewerModule viewer = new ViewerModule();
		viewer.setCloseOnStop(false);
		Task task = new Opt4JTask(false);
		task.init(ea, comp, params, viewer);
		try {
			task.execute();
			Archive archive = ((Opt4JTask) task).getInstance(Archive.class);
			for (Individual individual : archive) {
				System.out.println(individual.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			((Opt4JTask) task).close();
		}
	}

	public void config2(String type, Integer[] lower, Integer[] upper) {
		System.out.println(type);
	}

	public void setPythonPath(String path) {
		this.pythonPath = path;
		System.out.println("Python path set to " + path);
	}

	public int config(String type, Integer[] lower, Integer[] upper) {
		System.out.println("Config Genotype Params");
		assert lower.length == upper.length;
		int[] lower_ = new int[lower.length];
		int[] upper_ = new int[upper.length];
		for (int i = 0; i < upper_.length; i++) {
			lower_[i] = lower[i];
			upper_[i] = upper[i];
		}
		switch (type) {
		case "int":
			this.number = lower.length;
			this.lower = lower_;
			this.upper = upper_;
			break;
		case "float":
			break;
		default:
			System.err.println("Currently only 'int' and 'float' is supported.");
		}
		System.out.println("Done");
		return 0;
	}

	public static void main(String[] args) {
		JavaHost app = new JavaHost();
		System.out.println("start JavaHost");
		// int[] low = {1,1};
		// int[] up = {12,12};
		// app.setLower(low);
		// app.setUpper(up);
		// app.setPythonPath("C:\\Users\\andreas\\Desktop\\andreas\\opt4jpython\\optexec\\src\\main\\python\\");
		// app.run();
		GatewayServer server = new GatewayServer(app);
		server.start();
	}

}
