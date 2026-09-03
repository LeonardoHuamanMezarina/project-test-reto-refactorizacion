package pe.edu.vallegrande;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    @Test
    void debeCalcularTotal() {

        PedidoService service = new PedidoService();

        double resultado = service.calcularTotal(100, 2, false);

        assertEquals(200, resultado);
    }

    @Test
    void debeAplicarDescuentoClienteFrecuente() {

        PedidoService service = new PedidoService();

        double resultado = service.calcularTotal(100, 2, true);

        assertEquals(180, resultado);
    }

    @Test
    void debeObtenerEstadoMediano() {

        PedidoService service = new PedidoService();

        String resultado = service.obtenerEstado(200);

        assertEquals("MEDIANO", resultado);
    }

    @Test
    void debeValidarPedidoCorrecto() {

        PedidoService service = new PedidoService();

        boolean resultado = service.validarPedido("Laptop", 2);

        assertTrue(resultado);
    }
}