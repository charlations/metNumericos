package simulation.events;

import simulation.BancoSimulationModel;
import simulation.entities.Cashier;
import simulation.entities.Customer;
import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author A01203635
 */
public class DepartureEvent extends EventOf2Entities<Customer, Cashier> {
    private BancoSimulationModel model;
    
    public DepartureEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        model = (BancoSimulationModel) owner;
        model.L.update(model.clientes);
    }
    
    @Override
    public void eventRoutine(Customer cli, Cashier serv) {
        model.clientes--;
        model.clientHistogram.update(model.clientes);
        //model.L.update(model.clientes);
        
        if (model.filaEspera.isEmpty()) {
            model.idleServer.insert(serv);
        } else {
            Customer cliente = model.filaEspera.removeFirst();
            model.Lq.update(model.filaEspera.size());
            model.Wq.update(presentTime().getTimeAsDouble() - cliente.getArrivalTime().getTimeAsDouble());
            DepartureEvent event = new DepartureEvent(model, "Tiempo de servicio", true);
            event.schedule(cliente, serv, new TimeSpan(model.getCustomerServiceTime()));
            //OJO: le agregué el MINUTES
        }
        
        model.W.update(presentTime().getTimeAsDouble() - cli.getArrivalTime().getTimeAsDouble());
    }
    
    
    
}
