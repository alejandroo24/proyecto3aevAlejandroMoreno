package DAO;

import interfaces.InterfazDAO;
import model.TrabajadorAlmacen;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TrabajadorAlmacenDAO implements InterfazDAO<TrabajadorAlmacen> {

    private static Connection con;

    public TrabajadorAlmacenDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(TrabajadorAlmacen objeto) {
        String sql = "INSERT INTO trabajadores_almacen (idTrabajador, idAlmacen) VALUES (?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getIdTrabajador());
            stmt.setInt(2, objeto.getIdAlmacen());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(TrabajadorAlmacen objeto) {
    String sql = "UPDATE trabajadores_almacen SET idTrabajador = ?, idAlmacen = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getIdTrabajador());
            stmt.setInt(2, objeto.getIdAlmacen());
            stmt.setInt(3, objeto.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql = "DELETE FROM trabajadores_almacen WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TrabajadorAlmacen obtenerPorId(int id) {
        String sql ="SELECT * FROM trabajadores_almacen WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                TrabajadorAlmacen trabajadorAlmacen = new TrabajadorAlmacen();
                trabajadorAlmacen.setId(rs.getInt("id"));
                trabajadorAlmacen.setIdTrabajador(rs.getInt("idTrabajador"));
                trabajadorAlmacen.setIdAlmacen(rs.getInt("idAlmacen"));
                return trabajadorAlmacen;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TrabajadorAlmacen> obtenerTodos() {
        String sql = "SELECT * FROM trabajadores_almacen";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<TrabajadorAlmacen> lista = new ArrayList<>();
            while (rs.next()) {
                TrabajadorAlmacen trabajadorAlmacen = new TrabajadorAlmacen();
                trabajadorAlmacen.setId(rs.getInt("id"));
                trabajadorAlmacen.setIdTrabajador(rs.getInt("idTrabajador"));
                trabajadorAlmacen.setIdAlmacen(rs.getInt("idAlmacen"));
                lista.add(trabajadorAlmacen);
            }
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
