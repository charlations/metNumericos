package simulation.events;

import simulation.BancoSimulationModel;
import simulation.entities.Cashier;
import simulation.entities.Customer;
import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author A01203635
 */
public class ArrivalEvent extends ExternalEvent {
    private BancoSimulationModel model;
    public ArrivalEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        model = (BancoSimulationModel) owner;
    }
    //TODO: modify model atribute of #clients and update histogram
    @Override
    public void eventRoutine(){
        model.clientes++;
        model.clientHistogram.update(model.clientes);
        model.L.update(model.clientes);
        
        Customer cliente = new Customer(model, "Cliente", true);
        
        if(model.idleServer.isEmpty()) {
            model.filaEspera.insert(cliente);
            //model.Lq.update(1.0);
            model.Lq.update(model.filaEspera.size());
        } else {
            Cashier cajero = model.idleServer.removeFirst();
            //model.L.update(1.0);
            //model.L.update(model.clientes);
            DepartureEvent event = new DepartureEvent(model, "Acabo tiempo de servicio", true);
            event.schedule(cliente, cajero, new TimeSpan(model.getCustomerServiceTime()));
            //por qué no así?
            //event.schedule(cliente, cajero, new TimeSpan(model.getCustomerServiceTime(), TimeUnit.MINUTES));
        }
        
        schedule(new TimeSpan(model.getCustomerArrivalTime())); 
        //igual aquí
    }
}
