/*
Enunciado: Define EstadoPedido (CREADO, ENVIADO, ENTREGADO, CANCELADO) y Pedido
con código, importe y estado inicial CREADO. Usa init para rechazar códigos
vacíos e importes negativos. El estado se puede consultar, pero su setter debe
ser privado. Implementa enviar(), entregar() y cancelar() con estas transiciones:
CREADO -> ENVIADO o CANCELADO; ENVIADO -> ENTREGADO o CANCELADO. Añade un método
con when que describa el estado. En main, crea, envía y entrega un pedido; muestra
los estados y comprueba qué ocurre al intentar entregar uno que no se ha enviado.
*/

package soluciones.pedidos

enum class EstadoPedido {
    CREADO,
    ENVIADO,
    ENTREGADO,
    CANCELADO
}

class Pedido(
    val codigo: String,
    val importe: Double
) {
    var estado: EstadoPedido = EstadoPedido.CREADO
        private set

    init {
        require(codigo.isNotBlank()) { "El código no puede estar vacío" }
        require(importe >= 0.0) { "El importe no puede ser negativo" }
    }

    fun enviar() {
        check(estado == EstadoPedido.CREADO) {
            "Solo se puede enviar un pedido recién creado"
        }
        estado = EstadoPedido.ENVIADO
    }

    fun entregar() {
        check(estado == EstadoPedido.ENVIADO) {
            "Solo se puede entregar un pedido que ya se ha enviado"
        }
        estado = EstadoPedido.ENTREGADO
    }

    fun cancelar() {
        check(estado == EstadoPedido.CREADO || estado == EstadoPedido.ENVIADO) {
            "Solo se puede cancelar un pedido creado o enviado"
        }
        estado = EstadoPedido.CANCELADO
    }

    fun descripcionEstado(): String = when (estado) {
        EstadoPedido.CREADO -> "El pedido se ha creado"
        EstadoPedido.ENVIADO -> "El pedido está en camino"
        EstadoPedido.ENTREGADO -> "El pedido se ha entregado"
        EstadoPedido.CANCELADO -> "El pedido se ha cancelado"
    }
}

fun main() {
    val pedido = Pedido("P-001", 25.0)
    println(pedido.descripcionEstado())

    pedido.enviar()
    println(pedido.descripcionEstado())

    pedido.entregar()
    println(pedido.descripcionEstado())

    val pedidoSinEnviar = Pedido("P-002", 10.0)
    try {
        pedidoSinEnviar.entregar()
    } catch (error: IllegalStateException) {
        println("Transición rechazada: ${error.message}")
    }
}
