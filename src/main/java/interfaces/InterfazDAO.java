package interfaces;

import java.util.List;

public interface InterfazDAO<T> {

    void insertar(T objeto);
    void actualizar(T objeto);
    void eliminar (int id);
    T obtenerPorId(int id);
    List<T> obtenerTodos();

}
