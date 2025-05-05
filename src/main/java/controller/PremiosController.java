package controller;

import model.Premio;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PremiosController {

    private static PremiosController instance;
    private ArrayList<Premio> premios;
    private final Random random = new Random();
    private final List<String> descripciones = Arrays.asList(
            "Descuento del 20% en la próxima compra",
            "Envío gratuito",
            "Envio express gratuito",
            "Producto a elegir gratis",
            "Descuento del 50% en la próxima compra",
            "Tarjeta de regalo valorada en 10€",
            "Tarjeta de regalo valorada en 20€",
            "Tarjeta de regalo valorada en 30€",
            "Tarjeta de regalo valorada en 50€",
            "Producto sorpresa gratis",
            "Dobles puntos en la próxima compra"
    );

    private PremiosController() {
        // Constructor privado para evitar instanciación externa
    }

    public static PremiosController getInstance() {
        if (instance == null) {
            instance = new PremiosController();
        }
        return instance;
    }

    public static void setInstance(PremiosController instance) {
        PremiosController.instance = instance;
    }

    public PremiosController( ArrayList<Premio> premios) {
        this.premios = premios;
    }

    public ArrayList<Premio> getPremios() {
        return premios;
    }

    public void setPremios(ArrayList<Premio> premios) {
        this.premios = premios;
    }

    public ArrayList<Premio> getPremiosDisponibles() {
        return premios;
    }

    public void setPremiosDisponibles(ArrayList<Premio> premios) {
        this.premios = premios;
    }

    public ArrayList<Premio> obtenerPremiosAleatorios() {
        int totalPremios = descripciones.size();
        HashSet<Integer> numerosGenerados = new HashSet<>();
        ArrayList<Premio> premiosGenerados = new ArrayList<>();
        do {
            int numeroAleatorio = random.nextInt(totalPremios);

            if (!numerosGenerados.contains(numeroAleatorio)) {
                numerosGenerados.add(numeroAleatorio);
                premiosGenerados.add(new Premio(descripciones.get(numeroAleatorio), random.nextInt(251) + 300));
            }
        }while(numerosGenerados.size() < 4);

        return premiosGenerados;
    }

    public void actualizarPremios(){
        premios.clear();
        premios.addAll(obtenerPremiosAleatorios());
    }

    private void programarActualizacionPremios() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::actualizarPremios,0,7, TimeUnit.DAYS);
    }
}


