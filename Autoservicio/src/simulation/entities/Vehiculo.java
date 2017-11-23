package simulation.entities;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;

/**
 *
 * @author A01203635
 */
public class Vehiculo extends Entity{
    private TimeInstant arrivalTime;
    public Vehiculo(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        arrivalTime = presentTime();
    }
    
    public TimeInstant getArrivalTime(){
        return arrivalTime;
    }
}
