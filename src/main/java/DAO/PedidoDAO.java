package DAO;

import interfaces.InterfazDAO;
import model.Pedido;

import java.sql.Connection;
import java.util.List;

public class PedidoDAO implements InterfazDAO<Pedido> {

    private static Connection con;

    public PedidoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Pedido pedido) {
        String sql = "INSERT INTO pedidos (cliente_id, estado, fecha_creacion, fecha_limite) VALUES (?, ?, ?, ?)";
    }

    @Override
    public void actualizar(Pedido pedido) {

    }

    @Override
    public void eliminar(int id) {

    }

    @Override
    public Pedido obtenerPorId(int id) {
        return null;
    }

    @Override
    public List<Pedido> obtenerTodos() {
        return List.of();
    }
}
