package model;

public enum TallasProducto {
    S, M, L, XL, XXL,CUARENTA(40),CUARENTA_UNO(41),CUARENTA_DOS(42), CUARENTA_TRES(43),CUARENTA_CUATRO(44),CUARENTA_CINCO(45),TALLA_UNICA;

    private Integer numeroTalla;
    TallasProducto(Integer numeroTalla) {
        this.numeroTalla = numeroTalla;
    }

    TallasProducto() {
        this.numeroTalla = null;
    }

    public Integer getNumeroTalla() {
        return numeroTalla;
    }
    public static TallasProducto fromString(String text) {
        for (TallasProducto talla : TallasProducto.values()) {
            if (talla.name().equalsIgnoreCase(text)) {
                return talla;
            }
        }
        return null;
    }
}
