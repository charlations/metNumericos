package simulation.events;

import simulation.PastryShopSimulationModel;
import simulation.entities.CustomerGroupEntity;
import simulation.entities.WaitressEntity;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;

public class ArrivalEvent extends ExternalEvent{

	private PastryShopSimulationModel model;

	public ArrivalEvent(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model = (PastryShopSimulationModel) owner;
	}

	@Override
	public void eventRoutine() {
                model.grupos++;
                model.clientHistogram.update(model.grupos);
                model.L.update(model.grupos);

		int size = model.getCustomerGroupSize();
		model.customerGroupSizeHistogram.update(size);
		
		CustomerGroupEntity customerGroup = new CustomerGroupEntity(size, model, "Customer Group", false);
		
		if (model.idleWaitressQueue.isEmpty()) {
			model.buyersQueue.insert(customerGroup);
                        model.Lq.update(model.buyersQueue.size());
		}
		else {
			WaitressEntity waitress = model.idleWaitressQueue.removeFirst();
			BuyEndedEvent event = new BuyEndedEvent(model, "Buy ended event", true);
			event.schedule(customerGroup, waitress, new TimeSpan(model.getBuyTime()));
		}
		
		schedule(new TimeSpan(model.getCustomerArrivalTime()));
		
	}

}
