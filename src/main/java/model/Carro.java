package model;

import java.util.HashMap;

public class Carro {
    private HashMap<Producto, Integer> productosCarro;

    public Carro() {
        this.productosCarro = null;
    }

    public HashMap<Producto, Integer> getProductosCarro() {
        return productosCarro;
    }

    public void setProductosCarro(HashMap<Producto, Integer> productosCarro) {
        this.productosCarro = productosCarro;
    }

    @Override
    public String toString() {
        return "Carro" +
                "productos en el Carro" + productosCarro;
    }


}
