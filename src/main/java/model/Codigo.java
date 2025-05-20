package model;

public class Codigo {
    private int id;
    private String codigo;
    private int valor;
    private boolean usado;

    public Codigo(){

    }

    public Codigo(String codigo, int valor) {
        this.codigo = codigo;
        this.valor = valor;
        this.usado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
}
