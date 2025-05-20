package DAO;

import interfaces.InterfazDAO;
import model.Cliente;
import model.Reseña;
import DAO.ProductosDAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ReseñasDAO implements InterfazDAO<Reseña> {

    private static Connection con;

    public ReseñasDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Reseña reseña) {
        String sql = "INSERT INTO reseñas (producto_id, cliente_id, calificacion, comentario,fecha) VALUES (?, ?, ?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, reseña.getProducto().getId());
            stmt.setInt(2, reseña.getCliente().getId());
            stmt.setInt(3, reseña.getCalificacion());
            stmt.setString(4, reseña.getComentario());
            stmt.setDate(5, java.sql.Date.valueOf(reseña.getFecha()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actualizar(Reseña reseña) {
        String sql = "UPDATE reseñas SET producto_id = ?, cliente_id = ?, calificacion = ?, comentario = ? fecha = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, reseña.getProducto().getId());
            stmt.setInt(2, reseña.getCliente().getId());
            stmt.setInt(3, reseña.getCalificacion());
            stmt.setString(4, reseña.getComentario());
            stmt.setInt(5, reseña.getId());
            stmt.setDate(6, java.sql.Date.valueOf(reseña.getFecha()));
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM reseñas WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reseña obtenerPorId(int id) {
        String sql = "SELECT * FROM reseñas WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Reseña reseña = new Reseña();
                reseña.setId(rs.getInt("id"));
                reseña.setProducto(new ProductosDAO(con).obtenerPorId(rs.getInt("producto_id")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reseña> obtenerTodos() {
        String sql = "SELECT * FROM reseñas";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                Reseña reseña = new Reseña();
                reseña.setId(rs.getInt("id"));
                reseña.setProducto(new ProductosDAO(con).obtenerPorId(rs.getInt("producto_id")));
                reseña.setCliente(new ClienteDAO(con).obtenerPorId(rs.getInt("cliente_id")));
                reseña.setCalificacion(rs.getInt("calificacion"));
                reseña.setComentario(rs.getString("comentario"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Reseña> obtenerPorCliente(Cliente cliente) {
        String sql = "SELECT * FROM reseñas WHERE cliente_id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId());
            var rs = stmt.executeQuery();
            List<Reseña> reseñas = new ArrayList<>();
            while (rs.next()) {
                Reseña reseña = new Reseña();
                reseña.setId(rs.getInt("id"));
                reseña.setProducto(new ProductosDAO(con).obtenerPorId(rs.getInt("producto_id")));
                reseña.setCliente(cliente);
                reseña.setCalificacion(rs.getInt("calificacion"));
                reseña.setComentario(rs.getString("comentario"));
                reseña.setFecha(rs.getDate("fecha").toLocalDate());
                reseñas.add(reseña);
            }
            return reseñas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
