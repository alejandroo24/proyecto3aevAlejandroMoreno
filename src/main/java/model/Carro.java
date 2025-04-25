package model;

import dataAccess.HashMapAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.HashMap;

@XmlRootElement(name = "carro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Carro {
    @XmlJavaTypeAdapter(HashMapAdapter.class)
    private HashMap<Producto, Integer> productosCarro;

    public Carro() {
        this.productosCarro = new HashMap<>();
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
