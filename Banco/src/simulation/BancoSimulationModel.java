package simulation;

import simulation.entities.Cashier;
import simulation.entities.Customer;
import simulation.events.ArrivalEvent;
import simulation.events.DepartureEvent;

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
public class BancoSimulationModel extends Model {
    private static final int SERVERS = 2;
    public int clientes;
    
    public Queue<Customer> filaEspera;
    public Queue<Cashier> idleServer;
    
    private ContDistExponential serviceTime;
    private ContDistExponential arrivalTime;
    
    public Tally L; //numClientesBanco
    public Tally W; //tInvertidoBanco
    public Tally Lq; //numClientesFilaEsp
    public Tally Wq; //tInvertidoFilaEsp
    public Histogram clientHistogram; // P
    
    public double getCustomerArrivalTime(){
        double value = arrivalTime.sample().doubleValue();
        return value <= 0 ? (value * -1) : value;
        //return value <= 0 ? 0 : value;
    }
    public double getCustomerServiceTime(){
        double value = serviceTime.sample().doubleValue();
        return value <= 0 ? (value * -1) : value;
        //return value <= 0 ? 0 : value;
    }
    
    public BancoSimulationModel(Model owner, String name,
                                boolean showInReport, boolean showInTrace) {
        super(owner, name, showInReport, showInTrace);
        clientes = 0;
    }
    @Override
    public String description() {
        return "Bank simulation";
    }

    @Override
    public void doInitialSchedules() {
        ArrivalEvent event = new ArrivalEvent(this, "Arrival event", true);
        event.schedule(new TimeInstant(0));
        /* TimeInstant represents an absolute point in the timeline while 
        TimeSpan is relative to the current time */
    }
    @Override
    public void init() {
        clientes = 0; //no sé dónde ponerlo...
        
        filaEspera = new Queue<Customer>(this, "Fila de Espera", true, true);
        idleServer = new Queue<Cashier>(this, "Cajeros Inactivos", true, true);
        
        serviceTime = new ContDistExponential(this, "Tiempo de Servicio", 1.2, true, true);
        arrivalTime = new ContDistExponential(this, "Tiempo de Llegada", 0.75, true, true); //<-- 60/80
        
        for (int i = 0; i < SERVERS; i++){
            idleServer.insert(new Cashier(this, "Cajero", true));
        }
        
        L = new Tally(this, "Numero de clientes esperados en el banco", true, true);
        W = new Tally(this, "Tiempo invertido en el banco", true, true);
        Lq = new Tally(this, "Numero de clientes esperados en la fila de espera", true, true);
        Wq = new Tally(this, "Tiempo invertido en la fila de espera", true, true);
        clientHistogram = new Histogram(this, "Histograma de P(s)", 1, 10, 4, true, true);
    }
    
    
}
