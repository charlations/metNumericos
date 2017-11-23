package simulation.events;

import simulation.PastryShopSimulationModel;
import simulation.entities.CustomerGroupEntity;
import simulation.entities.TableEntity;
import simulation.entities.WaitressEntity;
import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class BuyEndedEvent extends EventOf2Entities<CustomerGroupEntity, WaitressEntity>{

	private PastryShopSimulationModel model;
	
	public BuyEndedEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (PastryShopSimulationModel) owner;
	}

	@Override
	public void eventRoutine(CustomerGroupEntity who1, WaitressEntity who2) {
		
		if (model.buyersQueue.isEmpty()) {
			model.idleWaitressQueue.insert(who2);
		}
		else {
			CustomerGroupEntity customerGroup = model.buyersQueue.removeFirst();
			BuyEndedEvent event = new BuyEndedEvent(model, "Buy ended event", true);
			event.schedule(customerGroup, who2, new TimeSpan(model.getBuyTime()));
		}
		
		if (model.idleTableQueue.isEmpty()) {
			model.eatersQueue.insert(who1);
		}
		else {
			TableEntity table = model.idleTableQueue.removeFirst();
			DepartureEvent event = new DepartureEvent(model, "Departure event", true);
			event.schedule(who1, table, new TimeSpan(model.getEatTime()));
		}
		
	}

}
