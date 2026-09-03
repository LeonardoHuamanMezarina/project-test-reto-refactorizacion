package pe.edu.vallegrande;

public class PedidoService {
    private static final double DESCUENTO_CLIENTE_FRECUENTE = 0.90;
    private static final double DESCUENTO_POR_CANTIDAD = 0.95;
    private static final int CANTIDAD_MINIMA_DESCUENTO = 10;

    public double calcularTotal(double precio, int cantidad, boolean clienteFrecuente) {
        if (cantidad <= 0) {
            return 0;
        }
        double total = precio * cantidad;
        if (clienteFrecuente) {
            total *= DESCUENTO_CLIENTE_FRECUENTE;
        }
        if (cantidad >= CANTIDAD_MINIMA_DESCUENTO) {
            total *= DESCUENTO_POR_CANTIDAD;
        }
        return total;
    }

    public String obtenerEstado(double total) {
        if (total <= 0) {
            return "ERROR";
        }
        if (total < 100) {
            return "PEQUEÑO";
        }
        if (total < 500) {
            return "MEDIANO";
        }
        return "GRANDE";
    }

    public boolean validarPedido(String producto, int cantidad) {
        if (producto == null || producto.isEmpty() || cantidad <= 0) {
            return false;
        }
        return true;
    }
}