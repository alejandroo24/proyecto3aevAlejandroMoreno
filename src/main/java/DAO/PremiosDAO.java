package DAO;

import interfaces.InterfazDAO;
import model.Premio;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PremiosDAO implements InterfazDAO<Premio> {

    private static Connection con;

    public PremiosDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Premio premio) {
        String sql = "INSERT INTO premios (descripcion, puntosNecesarios) VALUES (?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, premio.getDescripcion());
            stmt.setInt(2, premio.getPuntosNecesarios());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Premio objeto) {
        String sql = "UPDATE premios SET descripcion = ?, puntosNecesarios = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, objeto.getDescripcion());
            stmt.setInt(2, objeto.getPuntosNecesarios());
            stmt.setInt(3, objeto.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql = " DELETE FROM premios WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Premio obtenerPorId(int id) {
        String sql = "SELECT * FROM premios WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Premio premio = new Premio();
                premio.setId(rs.getInt("id"));
                premio.setDescripcion(rs.getString("descripcion"));
                premio.setPuntosNecesarios(rs.getInt("puntosNecesarios"));
                return premio;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Premio> obtenerTodos() {
     String sql = "SELECT * FROM premios";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<Premio> premios = new ArrayList<>();
            while (rs.next()) {
                Premio premio = new Premio();
                premio.setId(rs.getInt("id"));
                premio.setDescripcion(rs.getString("descripcion"));
                premio.setPuntosNecesarios(rs.getInt("puntosNecesarios"));
                premios.add(premio);
            }
            return premios;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
