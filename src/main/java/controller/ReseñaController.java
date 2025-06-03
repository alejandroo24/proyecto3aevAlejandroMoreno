package controller;

import DataBase.ConnectionBD;

import java.util.ArrayList;

public class ReseñaController {
    private ArrayList<Reseña> reseñasProductos;
    private static ReseñasDAO reseñasDAO = new ReseñasDAO(ConnectionBD.getConnection());

    public ReseñaController() {
        this.reseñasProductos = new ArrayList<>();
    }


    public ArrayList<Reseña> getReseñasProductos() {
        return reseñasProductos;
    }
    public void setReseñasProductos(ArrayList<Reseña> reseñasProductos) {
        this.reseñasProductos = reseñasProductos;
    }

    @Override
    public String toString() {
        return "Reseñas de productos" +
                "reseñasProductos=" + reseñasProductos +
                '}';
    }

    public void addReseña(Reseña reseña) {
        this.reseñasProductos.add(reseña);
        reseñasDAO.insertar(reseña);
    }

    public boolean eliminarReseña(Reseña reseña) {
        if (reseñasProductos.contains(reseña)) {
            reseñasProductos.remove(reseña);
            reseñasDAO.eliminar(reseña.getId());
            return true;
        }
        return false;
    }

    public boolean modificarReseña(Reseña reseña) {
        if (reseñasProductos.contains(reseña)) {
            int index = reseñasProductos.indexOf(reseña);
            reseñasProductos.set(index, reseña);
            reseñasDAO.actualizar(reseña);
            return true;
        }
        return false;
    }

    public boolean buscarReseña(Reseña reseña) {
        return reseñasProductos.contains(reseña);
    }

    public boolean buscarReseñaPorId(int id) {
        reseñasDAO.obtenerPorId(id);
        return false;
    }
}
