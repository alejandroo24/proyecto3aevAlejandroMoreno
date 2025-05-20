package DAO;

import interfaces.InterfazDAO;
import model.TrabajadorPedidoCompletado;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorPedidoCompletadoDAO implements InterfazDAO<TrabajadorPedidoCompletado> {

    private static Connection con;

    public TrabajadorPedidoCompletadoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(TrabajadorPedidoCompletado objeto) {
        String sql = "INSERT INTO trabajador_pedido_completado (id_trabajador, id_pedido) VALUES (?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getTrabajadorId());
            stmt.setInt(2, objeto.getPedidoId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(TrabajadorPedidoCompletado objeto) {
        String sql = "UPDATE trabajador_pedido_completado SET id_trabajador = ?, id_pedido = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getTrabajadorId());
            stmt.setInt(2, objeto.getPedidoId());
            stmt.setInt(3, objeto.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql = "DELETE FROM trabajador_pedido_completado WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TrabajadorPedidoCompletado obtenerPorId(int id) {
        String sql = "SELECT * FROM trabajador_pedido_completado WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                TrabajadorPedidoCompletado trabajadorPedidoCompletado = new TrabajadorPedidoCompletado();
                trabajadorPedidoCompletado.setId(rs.getInt("id"));
                trabajadorPedidoCompletado.setTrabajadorId(rs.getInt("id_trabajador"));
                trabajadorPedidoCompletado.setPedidoId(rs.getInt("id_pedido"));
                return trabajadorPedidoCompletado;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TrabajadorPedidoCompletado> obtenerTodos() {
        String sql = "SELECT * FROM trabajador_pedido_completado";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<TrabajadorPedidoCompletado> lista = new ArrayList<>();
            while (rs.next()) {
                TrabajadorPedidoCompletado trabajadorPedidoCompletado = new TrabajadorPedidoCompletado();
                trabajadorPedidoCompletado.setId(rs.getInt("id"));
                trabajadorPedidoCompletado.setTrabajadorId(rs.getInt("id_trabajador"));
                trabajadorPedidoCompletado.setPedidoId(rs.getInt("id_pedido"));
                lista.add(trabajadorPedidoCompletado);
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
