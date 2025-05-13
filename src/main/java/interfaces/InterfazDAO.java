package interfaces;

import java.util.List;

public interface  InterfazDAO<T> {

    void insertar(T objeto);
    void insertar(T objeto, T objeto2);
    void actualizar(T objeto);
    void eliminar (int id);
    T obtenerPorId(int id);
    List<T> obtenerTodos();

}
