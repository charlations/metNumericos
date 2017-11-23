package simulation.events;

import simulation.AutoservicioSimulationModel;

import simulation.entities.Vehiculo;
import simulation.entities.Limpiador;

import desmoj.core.simulator.EventOf2Entities;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;
/**
 *
 * @author A01203635
 */
public class DepartureEvent extends EventOf2Entities<Vehiculo, Limpiador> {
    private AutoservicioSimulationModel model;
    
    public DepartureEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        model = (AutoservicioSimulationModel) owner;
        model.L.update(model.vehiculos);
    }

    @Override
    public void eventRoutine(Vehiculo cli, Limpiador serv) {
        model.vehiculos--;
        model.clientHistogram.update(model.vehiculos);
        if(model.filaEspera.isEmpty()) {
            model.idleLimpiador.insert(serv);
        }
        else {
            Vehiculo cliente = model.filaEspera.removeFirst();
            model.Lq.update(model.filaEspera.size());
            model.Wq.update(presentTime().getTimeAsDouble() - cliente.getArrivalTime().getTimeAsDouble());
            DepartureEvent event = new DepartureEvent(model, "Sale vehiculo", true);
            event.schedule(cliente, serv, new TimeSpan(model.getVehicleServiceTime()));
        }
        model.W.update(presentTime().getTimeAsDouble() - cli.getArrivalTime().getTimeAsDouble());
    }    
}
