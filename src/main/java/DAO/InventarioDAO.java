package DAO;

import interfaces.InterfazDAO;
import model.Almacen;
import model.Inventario;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO implements InterfazDAO<Inventario>{

    private static Connection con;

    public InventarioDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Inventario inventario) {
        String sql = "INSERT INTO inventario (almacen_id, producto_id, cantidad) VALUES (?, ?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, inventario.getIdAlmacen());
            stmt.setInt(2, inventario.getIdProducto());
            stmt.setInt(3, inventario.getCantidad());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Inventario inventario) {
    String sql = "UPDATE inventario SET almacen_id = ?, producto_id = ?, cantidad = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, inventario.getIdAlmacen());
            stmt.setInt(2, inventario.getIdProducto());
            stmt.setInt(3, inventario.getCantidad());
            stmt.setInt(4, inventario.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql = "DELETE FROM inventario WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Inventario obtenerPorId(int id) {
        String sql = "SELECT * FROM inventario WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Inventario inventario = new Inventario();
                inventario.setId(rs.getInt("id"));
                inventario.setIdAlmacen(rs.getInt("almacen_id"));
                inventario.setIdProducto(rs.getInt("producto_id"));
                inventario.setCantidad(rs.getInt("cantidad"));
                return inventario;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Inventario> obtenerTodos() {
        String sql = "SELECT * FROM inventario";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<Inventario> listaInventarios = new ArrayList<>();
            while (rs.next()) {
                Inventario inventario = new Inventario();
                inventario.setId(rs.getInt("id"));
                inventario.setIdAlmacen(rs.getInt("almacen_id"));
                inventario.setIdProducto(rs.getInt("producto_id"));
                inventario.setCantidad(rs.getInt("cantidad"));
                listaInventarios.add(inventario);
            }
            return listaInventarios;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
