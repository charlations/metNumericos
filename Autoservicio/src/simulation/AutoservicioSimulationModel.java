package simulation;

import simulation.entities.Limpiador;
import simulation.entities.Vehiculo;
import simulation.events.ArrivalEvent;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.Queue;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.statistic.Histogram;
import desmoj.core.statistic.Tally;
/**
 *
 * @author A01203635
 */
public class AutoservicioSimulationModel extends Model {
    private static final int LIMPIADOR = 1;
    private static final int LUGARES = 4;
    public int vehiculos;
    
    public Queue<Vehiculo> filaEspera;
    public Queue<Limpiador> idleLimpiador;
    
    private ContDistExponential serviceTime;
    private ContDistExponential arrivalTime;
    
    public Tally L; //numClientesBanco
    public Tally W; //tInvertidoBanco
    public Tally Lq; //numClientesFilaEsp
    public Tally Wq; //tInvertidoFilaEsp
    public Histogram clientHistogram; // P
    
    public double getVehicleArrivalTime(){
        double value = arrivalTime.sample().doubleValue();
        return value <= 0 ? (value * -1) : value;
    }
    public double getVehicleServiceTime(){
        double value = serviceTime.sample().doubleValue();
        return value <= 0 ? (value * -1) : value;
    }
    public int getTamanoLugares(){
        return LUGARES;
    }
    
    public AutoservicioSimulationModel(Model owner, String name,
                    boolean showInReport, boolean showInTrace) {
        super(owner, name, showInReport, showInTrace);
        vehiculos = 0;
    }

    @Override
    public String description() {
        return "Simulador de Autoservicio";
    }

    @Override
    public void doInitialSchedules() {
        ArrivalEvent arrivalEvent = new ArrivalEvent(this, "Llegada inicial", true);
        arrivalEvent.schedule(new TimeInstant(0));
    }

    @Override
    public void init() {
        vehiculos = 0;
        //init queues
        //último parámetro dice false en el github y en el ejemplo
        filaEspera = new Queue<Vehiculo>(this, "Fila de Espera", true, false); 
        idleLimpiador = new Queue<Limpiador>(this, "Limpiador Disponible", true, false);
        
        // Init random number generators
        //último parámetro dice false en el github y en el ejemplo
        serviceTime = new ContDistExponential(this, "Tiempo de Servicio", 60/15, true, false);
        arrivalTime = new ContDistExponential(this, "Tiempo de Llegada", 6/4, true, false);
        for(int i = 0; i < LIMPIADOR; i++) {
            idleLimpiador.insert(new Limpiador(this, "Limpiador", false));
        }
        
        //Estadísticas
        L = new Tally(this, "Vehiculos en el sistema", true, true);
        Lq = new Tally(this, "Vehiculos en la fila de espera", true, true);
        W = new Tally(this, "Tiempo invertido en el sistema", true, true);
        Wq = new Tally(this, "Tiempo invertido en la fila de espera", true, true);
        clientHistogram = new Histogram(this, "Histograma de P(s)", 1, 3, 2, true, true);
    }
}
