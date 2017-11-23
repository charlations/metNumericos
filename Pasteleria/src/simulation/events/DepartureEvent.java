package simulation.events;

import simulation.PastryShopSimulationModel;
import simulation.entities.CustomerGroupEntity;
import simulation.entities.TableEntity;
import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class DepartureEvent extends EventOf2Entities<CustomerGroupEntity, TableEntity>{

	private PastryShopSimulationModel model;
	
	public DepartureEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (PastryShopSimulationModel) owner;
                model.L.update(model.grupos);
	}

	@Override
	public void eventRoutine(CustomerGroupEntity who1, TableEntity who2) {
                model.grupos--;
                model.clientHistogram.update(model.grupos);
		
		if (model.eatersQueue.isEmpty()) {
			model.idleTableQueue.insert(who2);
		}
		else {
			CustomerGroupEntity customerGroup = model.eatersQueue.removeFirst();
                        model.Lq.update(model.buyersQueue.size());
                        model.Wq.update(presentTime().getTimeAsDouble() - customerGroup.getArrivalTime().getTimeAsDouble());
			DepartureEvent event = new DepartureEvent(model, "Departure event", true);
			event.schedule(customerGroup, who2, new TimeSpan(model.getEatTime()));
		}
		
                model.W.update(presentTime().getTimeAsDouble() - who1.getArrivalTime().getTimeAsDouble());
		model.timeSpentByCustomer.update(presentTime().getTimeAsDouble() - who1.getArrivalTime().getTimeAsDouble());
		
	}

}
