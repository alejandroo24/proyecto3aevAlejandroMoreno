package interfaces;

import DataBase.ConnectionBD;

import java.util.List;

public interface InterfazDAO<T> {

    void insertar(T objeto);
    void actualizar(T objeto);
    void eliminar (T objeto);
    T obtenerPorId(int id);
    List<T> obtenerTodos();

}
