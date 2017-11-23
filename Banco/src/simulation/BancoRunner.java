package simulation;

import java.util.concurrent.TimeUnit;
import desmoj.core.simulator.Experiment;
import desmoj.core.simulator.TimeInstant;
/**
 *
 * @author A01203635
 */
public class BancoRunner {

    public static void main(String[] args) {
        BancoSimulationModel model = new BancoSimulationModel(null, "Modelo de Simulacion de Banco", true, false);
        Experiment experiment = new Experiment("Experimento", null);
        
        model.connectToExperiment(experiment);
        
        // Turn on the simulation trace from the start to the end
        experiment.traceOn(new TimeInstant(0));
        // Set when to stop the simulation
        experiment.stop(new TimeInstant(6, TimeUnit.HOURS));
        
        experiment.start();
		
        // Create the report files
        experiment.report();

        experiment.finish();
        
    }
    
}
