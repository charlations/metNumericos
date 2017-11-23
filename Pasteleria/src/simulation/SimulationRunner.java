package simulation;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;

public class SimulationRunner {

	public static void main(String[] args) {
		
		PastryShopSimulationModel model = new PastryShopSimulationModel(null, "PastryShopSimulationModel", true, false);
		
		Experiment experiment = new Experiment("Experiment", TimeUnit.SECONDS, TimeUnit.SECONDS, null);
		
		model.connectToExperiment(experiment);
		
		// Turn on the simulation trace from the start to the end
		experiment.traceOn(new TimeInstant(0));
		
		// Set when to stop the simulation
		experiment.stop(new TimeInstant(8, TimeUnit.HOURS));
//		experiment.stop(new TimeInstant(20, TimeUnit.SECONDS));
		
		experiment.start();
		
		// Create the report files
		experiment.report();
		
		experiment.finish();
		
	}
}
