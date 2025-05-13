package DAO;

import interfaces.InterfazDAO;
import model.Almacen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class AlmacenDAO implements InterfazDAO<Almacen> {

    private final Connection con;

    public AlmacenDAO(Connection con) {
        this.con = con;
    }



    @Override
    public void insertar(Almacen almacen) {
        String sql = "INSERT INTO almacen (nombre, direccion) VALUES (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, almacen.getNombre());
            stmt.setString(2, almacen.getLocalizacion());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Almacen almacen) {
        String sql = "UPDATE almacen SET nombre = ?, direccion = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, almacen.getNombre());
            stmt.setString(2, almacen.getLocalizacion());
            stmt.setInt(3, almacen.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql = "DELETE FROM almacen WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Almacen obtenerPorId(int id) {
        String sql = "SELECT * FROM almacen WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Almacen almacen = new Almacen();
                almacen.setId(rs.getInt("id"));
                almacen.setNombre(rs.getString("nombre"));
                almacen.setLocalizacion(rs.getString("direccion"));
                return almacen;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Almacen> obtenerTodos() {
    String sql = "SELECT * FROM almacen";
        List<Almacen> almacenes = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Almacen almacen = new Almacen();
                almacen.setId(rs.getInt("id"));
                almacen.setNombre(rs.getString("nombre"));
                almacen.setLocalizacion(rs.getString("direccion"));
                almacenes.add(almacen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return almacenes;
    }
}
