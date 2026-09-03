# Reto de Refactorización - PedidoService

Proyecto de refactorización y aseguramiento de calidad de software aplicando principios de **Clean Code**, pruebas unitarias con **JUnit 5** y análisis de cobertura de código con **JaCoCo**.

---

## Descripción del Proyecto

El objetivo de este proyecto es refactorizar la lógica de negocio en la clase `PedidoService` para mejorar su legibilidad, mantenibilidad y estructura, asegurando que el comportamiento externo se mantenga intacto y todas las pruebas unitarias existentes sigan pasando satisfactoriamente.

---

## Tecnologías y Herramientas

* **Java:** 17 (LTS)
* **Gestor de dependencias y compilación:** Apache Maven
* **Framework de Pruebas Unitarias:** JUnit 5 (Jupiter 5.10.2)
* **Herramienta de Cobertura de Código:** JaCoCo Maven Plugin (0.8.12)

---

## Cambios y Refactorizaciones Aplicadas (Clean Code)

A continuación se detallan las mejoras implementadas en la clase [`PedidoService`](src/main/java/pe/edu/vallegrande/PedidoService.java):

### 1. Eliminación de "Números Mágicos" por Constantes Descriptivas
* **Problema:** En el código original se utilizaban valores literales como `0.90`, `0.95` y `10` esparcidos en los métodos, dificultando su comprensión y mantenimiento.
* **Solución:** Se extrajeron constantes `private static final`:
  ```java
  private static final double DESCUENTO_CLIENTE_FRECUENTE = 0.90;
  private static final double DESCUENTO_POR_CANTIDAD = 0.95;
  private static final int CANTIDAD_MINIMA_DESCUENTO = 10;
  ```

### 2. Método `calcularTotal`
* **Nombres Significativos:** Se renombró la variable ambigua `x` por `total`, reflejando claramente su propósito en el dominio del negocio.
* **Cláusula de Guarda (*Guard Clause*):** Se añadió una validación temprana para cantidades menores o iguales a cero (`cantidad <= 0`), evitando cálculos innecesarios.
* **Asignación Aritmética Concisa:** Se simplificó la sintaxis a operaciones compuestas (`total *= DESCUENTO_...`).

### 3. Método `obtenerEstado`
* **Retornos Tempranos (*Early Returns*):** Se eliminó la cadena redundante de `else if` y `else`. Dado que cada condición ejecuta un `return`, el código sale inmediatamente, mejorando la legibilidad lineal y reduciendo el anidamiento.

### 4. Método `validarPedido`
* **Unificación de Validaciones:** Se agruparon tres bloques `if` independientes en una sola condición lógica utilizando el operador OR (`||`):
  ```java
  if (producto == null || producto.isEmpty() || cantidad <= 0) {
      return false;
  }
  return true;
  ```
* **Mejor Práctica de Strings:** Se reemplazó `.equals("")` por el método idiomático `.isEmpty()`.

---

## Comparativa: Antes vs. Después

### Código Original (Antes)
```java
public class PedidoService {

    public double calcularTotal(double precio, int cantidad, boolean clienteFrecuente) {
        double x = 0;
        if (cantidad > 0) {
            x = precio * cantidad;
        }
        if (clienteFrecuente) {
            x = x * 0.90;
        }
        if (cantidad >= 10) {
            x = x * 0.95;
        }
        return x;
    }

    public String obtenerEstado(double total) {
        if (total <= 0) {
            return "ERROR";
        } else if (total < 100) {
            return "PEQUEÑO";
        } else if (total < 500) {
            return "MEDIANO";
        } else {
            return "GRANDE";
        }
    }

    public boolean validarPedido(String producto, int cantidad) {
        if (producto == null) {
            return false;
        }
        if (producto.equals("")) {
            return false;
        }
        if (cantidad <= 0) {
            return false;
        }
        return true;
    }
}
```

### Código Refactorizado (Después)
```java
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
```

---

## Pruebas Unitarias y Cobertura (JaCoCo)

El proyecto incluye pruebas unitarias automáticas con JUnit 5 en [`PedidoServiceTest`](src/test/java/pe/edu/vallegrande/PedidoServiceTest.java) que garantizan:
1. Cálculo correcto del monto total con y sin descuentos.
2. Obtención de estados según los rangos establecidos.
3. Validación de consistencia de los datos del pedido.

### ¿Qué es y qué hace JaCoCo?
**JaCoCo** (*Java Code Coverage*) está configurado como un plugin de Maven en `pom.xml`. Durante la ejecución de los tests, supervisa qué líneas, métodos e instrucciones lógicas son ejecutadas por la suite de pruebas y genera un reporte en formato HTML.

---

## Guía de Ejecución

### 1. Requisitos Previos
* Java JDK 17 o superior instalado y configurado en el `PATH`.
* Apache Maven 3.8+ instalado.

### 2. Ejecutar Pruebas y Generar Reporte de Cobertura
Abre una terminal en la raíz del proyecto y ejecuta:

```bash
mvn clean test
```

### 3. Visualizar el Reporte de JaCoCo
Una vez finalizado el comando, abre el siguiente archivo en tu navegador web preferido:
```text
target/site/jacoco/index.html
```

---

## Estructura del Proyecto

```text
reto-refactorizacion/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── pe/
    │           └── edu/
    │               └── vallegrande/
    │                   └── PedidoService.java       # Código de producción refactorizado
    └── test/
        └── java/
            └── pe/
                └── edu/
                    └── vallegrande/
                        └── PedidoServiceTest.java   # Pruebas unitarias con JUnit 5
```
