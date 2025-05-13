package controller;

import DataBase.ConnectionBD;
import model.Premio;
import utils.Utilidades;
import DAO.PremiosDAO;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PremiosController {

    private final String rutaArchivo = "premios.txt";
    private static PremiosController instance;
    private final Random random = new Random();
    private static PremiosDAO premiosDAO = new PremiosDAO(ConnectionBD.getConnection());



    private ArrayList<Premio> premios;
    private HashMap<String,Integer>codigosValidos;
    private HashSet<String>codigosUsados;
    private final List<String> descripciones = Arrays.asList(
            "Descuento del 20% en la próxima compra",
            "Descuento del 50% en la próxima compra",
            "Envío gratuito",
            "Producto sorpresa gratis",
            "Tarjeta de regalo valorada en 10€",
            "Tarjeta de regalo valorada en 20€",
            "Tarjeta de regalo valorada en 50€",
            "Duplicar puntos acumulados"
    );



    private PremiosController() {
        // Constructor privado para evitar instanciación externa
    }

    public static PremiosController getInstance() {
        if (instance == null) {
            instance = new PremiosController();

            instance.setPremios((ArrayList<Premio>) premiosDAO.obtenerTodos());
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

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public HashMap<String, Integer> getCodigosValidos() {
        return codigosValidos;
    }

    public void setCodigosValidos(HashMap<String, Integer> codigosValidos) {
        this.codigosValidos = codigosValidos;
    }

    public HashSet<String> getCodigosUsados() {
        return codigosUsados;
    }

    public void setCodigosUsados(HashSet<String> codigosUsados) {
        this.codigosUsados = codigosUsados;
    }

    public ArrayList<Premio> obtenerPremiosAleatorios() {
        int totalPremios = descripciones.size();
        HashSet<Integer> numerosGenerados = new HashSet<>();
        ArrayList<Premio> premiosGenerados = new ArrayList<>();
        do {
            int numeroAleatorio = random.nextInt(totalPremios);

            if (!numerosGenerados.contains(numeroAleatorio)) {
                numerosGenerados.add(numeroAleatorio);
                premiosGenerados.add(new Premio(descripciones.get(numeroAleatorio), random.nextInt(251) + 350));
            }
        }while(numerosGenerados.size() < 2);

        return premiosGenerados;
    }

    public void actualizarPremios(){
        premios.clear();
        premios.addAll(obtenerPremiosAleatorios());
        for (Premio premio : premios) {
            if (!premiosDAO.obtenerTodos().contains(premio)){
                premiosDAO.insertar(premio);
            }
        }
    }

    private void programarActualizacionPremios() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::actualizarPremios,0,7, TimeUnit.DAYS);
    }

    public void generarCodigo(int valorCodigo){
        String codigoGenerado = null;
        do {
             codigoGenerado = Utilidades.CrearCodigoAleatorio(9);
        }while (codigosUsados.contains(codigoGenerado));
        codigosValidos.put(codigoGenerado,valorCodigo);
    }

    public void canjearCodigo(String codigo){
        if (codigosValidos.containsKey(codigo)){
            codigosUsados.add(codigo);
            codigosValidos.remove(codigo);
        }else{
            Utilidades.muestraMensaje("El código no es válido o ya ha sido usado");
        }
    }


}


