package simulation.entities;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;
/**
 *
 * @author A01203635
 */
public class Customer extends Entity {
    private TimeInstant arrivalTime;
    //private TimeInstant serviceTime;
	
    public Customer(Model owner, String name, boolean showInTrace) {
            super(owner, name, showInTrace);
            arrivalTime = presentTime();
    }

    public TimeInstant getArrivalTime() {
            return arrivalTime;
    }
    /*public TimeInstant getServiceTime() {
            return serviceTime;
    }/*
    public void setServiceTime(TimeInstant servTime) {
        serviceTime = servTime;
    }*/
}
