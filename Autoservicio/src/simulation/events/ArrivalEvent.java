package simulation.events;

import simulation.AutoservicioSimulationModel;
import simulation.entities.Vehiculo;
import simulation.entities.Limpiador;

import desmoj.core.simulator.ExternalEvent;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.TimeSpan;
/**
 *
 * @author A01203635
 */
public class ArrivalEvent extends ExternalEvent{
    private AutoservicioSimulationModel model;
    
    public ArrivalEvent(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        model = (AutoservicioSimulationModel) owner;
    }

    @Override
    public void eventRoutine() {
        int k = model.getTamanoLugares();
        
        if( model.filaEspera.size() < k ) {
            model.vehiculos++;
            model.clientHistogram.update(model.vehiculos);
            model.L.update(model.vehiculos);
            
            Vehiculo cliente = new Vehiculo(model, "Vehiculo cliente", true);
            
            if(model.idleLimpiador.isEmpty()) {
                model.filaEspera.insert(cliente);
                model.Lq.update(model.filaEspera.size());
            } else {
                Limpiador servidor = model.idleLimpiador.removeFirst();
                DepartureEvent event = new DepartureEvent(model, "Sale vehiculo", true);
                event.schedule(cliente, servidor, new TimeSpan(model.getVehicleServiceTime()));
            }
            schedule(new TimeSpan(model.getVehicleArrivalTime()));
        }
        //schedule(new TimeSpan(model.getVehicleArrivalTime()));
    }
}
