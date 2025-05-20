package DAO;

import DataBase.ConnectionBD;
import interfaces.InterfazDAO;
import model.Codigo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CodigoDAO implements InterfazDAO<Codigo> {

    private static Connection con;

    public CodigoDAO(Connection con) {
        this.con = con;
    }

    @Override
    public void insertar(Codigo objeto) {
        String sql = "INSERT INTO codigos (codigo, valor) VALUES (?, ?)";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, objeto.getCodigo());
            stmt.setFloat(2, objeto.getValor());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Codigo objeto) {
    String sql = "UPDATE codigos SET valor = ? WHERE codigo = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, objeto.getValor());
            stmt.setString(2, objeto.getCodigo());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
    String sql = "DELETE FROM codigos WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Codigo obtenerPorId(int id) {
        String sql = "SELECT * FROM codigos WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                Codigo codigo = new Codigo();
                codigo.setId(rs.getInt("id"));
                codigo.setCodigo(rs.getString("codigo"));
                codigo.setValor(rs.getInt("valor"));
                return codigo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Codigo> obtenerTodos() {
        String sql = "SELECT * FROM codigos";
        try (var stmt = con.prepareStatement(sql)) {
            var rs = stmt.executeQuery();
            List<Codigo> listaCodigos = new ArrayList<>();
            while (rs.next()) {
                Codigo codigo = new Codigo();
                codigo.setId(rs.getInt("id"));
                codigo.setCodigo(rs.getString("codigo"));
                codigo.setValor(rs.getInt("valor"));
                listaCodigos.add(codigo);
            }
            return listaCodigos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void codigoUsado(Codigo codigo){
        String sql = "UPDATE codigos SET usado = ? WHERE id = ?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setBoolean(1, true);
            stmt.setInt(2, codigo.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
