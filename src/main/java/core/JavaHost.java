package core;
import java.util.Arrays;
import java.util.Random;

import org.opt4j.core.Individual;
import org.opt4j.core.common.completer.IndividualCompleterModule;
import org.opt4j.core.config.Task;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.start.Opt4JTask;
import org.opt4j.optimizers.ea.EvolutionaryAlgorithmModule;
import org.opt4j.viewer.ViewerModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import py4j.GatewayServer;

public class JavaHost {





	int number;
	int [] lower;
	int [] upper;
	private String setPythonPath;
	
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
		IndividualCompleterModule ic = new IndividualCompleterModule();
		ic.setThreads(4);
		ParamSearch params = new ParamSearch(this.lower, this.upper);

		ViewerModule viewer = new ViewerModule();
		viewer.setCloseOnStop(false);
		Task task = new Opt4JTask(false);
		task.init(ea, ic, params, viewer);
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
		this.setPythonPath = path;
		System.out.println("Python path set to" + path);
	}
	
	
	public int config(String type, Integer[] lower, Integer[] upper) {
		System.out.println("Config Genotype Params");
		System.out.println(upper.length);
		System.out.println(lower.length);
		assert lower.length == upper.length;
		int []lower_ = new int[lower.length];
		int []upper_ = new int[upper.length];
		for (int i = 0; i < upper_.length; i++) {
			lower_[i] = lower[i];
			upper_[i] = upper[i];
		}
		System.out.println(lower_[1]);
		System.out.println(upper_);
		switch(type) {
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
//		int[] low = {1,1};
//		int[] up = {12,12}; 
//		app.setLower(low);
//		app.setUpper(up);
//		app.run();
		//app.config("int", new int [] {1,1}, new int[] {12,12});
		//app.run();
		// app is now the gateway.entry_point
		GatewayServer server = new GatewayServer(app);
		server.start();
	}

}
