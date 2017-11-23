package simulation.entities;

import desmoj.core.simulator.Entity;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeInstant;

public class CustomerGroupEntity extends Entity{

	int size;
	private TimeInstant arrivalTime;
	
	public CustomerGroupEntity(int size, Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		this.size = size;
		arrivalTime = presentTime();
	}

	public TimeInstant getArrivalTime() {
		return arrivalTime;
	}

}
