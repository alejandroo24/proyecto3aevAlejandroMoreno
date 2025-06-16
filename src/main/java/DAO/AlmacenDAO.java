package DAO;

import interfaces.InterfazDAO;
import model.Almacen;
import model.Trabajador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AlmacenDAO implements InterfazDAO<Almacen> {

    private final Connection con;

    public AlmacenDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Almacen almacen) {
        String sql = "INSERT INTO almacen (id, nombre, direccion, telefono) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, almacen.getId());
            stmt.setString(2, almacen.getNombre());
            stmt.setString(3, almacen.getLocalizacion());
            stmt.setString(4, almacen.getTelefono());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Almacen almacen) {
    String sql = "UPDATE almacen SET nombre = ?, direccion = ?, telefono = ? WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, almacen.getNombre());
            stmt.setString(2, almacen.getLocalizacion());
            stmt.setString(3, almacen.getTelefono());
            stmt.setInt(4, almacen.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(Almacen almacen) {
    String sql = "DELETE FROM almacen WHERE id = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, almacen.getId());
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
                almacen.setTelefono(rs.getString("telefono"));
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
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Almacen almacen = new Almacen();
                almacen.setId(rs.getInt("id"));
                almacen.setNombre(rs.getString("nombre"));
                almacen.setLocalizacion(rs.getString("direccion"));
                almacen.setTelefono(rs.getString("telefono"));
                almacenes.add(almacen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return almacenes;
    }

    /**
     * Obtiene el almacén con menos trabajadores asignados.
     * @return Almacén con menos trabajadores, o null si no hay almacenes.
     */
    public Almacen obtenerAlmacenPorTrabajadores(){
        HashMap<Almacen,Integer> almacenContador = new HashMap<>();
        for (Almacen almacen : obtenerTodos()) {
            almacenContador.put(almacen, 0);
        }
        for (Trabajador trabajador : new TrabajadorDAO(con).obtenerTodos()) {
            for (Almacen almacen : almacenContador.keySet()) {
                if (trabajador.getAlmacen() != null && trabajador.getAlmacen().getId() == almacen.getId()) {
                    almacenContador.put(almacen, almacenContador.get(almacen) + 1);
                }
            }
        }
        Almacen almacenConMenosTrabajadores = null;
        if (!almacenContador.isEmpty()){
            for (Map.Entry<Almacen, Integer> entry : almacenContador.entrySet()) {
                if (almacenConMenosTrabajadores == null || entry.getValue() < almacenContador.get(almacenConMenosTrabajadores)) {
                    almacenConMenosTrabajadores = entry.getKey();
                }
            }
        }
        return almacenConMenosTrabajadores;
    }
}
