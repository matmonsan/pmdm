/*
Enunciado: Gestiona 12 plazas de sala, inicialmente libres. Permite reservar,
cancelar, consultar ocupación y recaudación, y finalizar. Rechaza plazas fuera
 del rango 1..12, reservas duplicadas y cancelaciones de plazas libres. Precios:
1..4 cuestan 8 euros, 5..8 cuestan 10 y 9..12 cuestan 12. Al cancelar, resta
el precio de la recaudación.
*/
package soluciones.seccion5.ejercicio13

private const val TOTAL_PLAZAS = 12

private fun precioDePlaza(plaza: Int): Int = when (plaza) {
    in 1..4 -> 8
    in 5..8 -> 10
    in 9..12 -> 12
    else -> error("Número de plaza fuera de rango")
}

private fun leerNumeroDePlaza(): Int? {
    print("Número de plaza: ")
    return readLine()?.trim()?.toIntOrNull()
}

fun main() {
    val ocupadas = BooleanArray(TOTAL_PLAZAS)
    var recaudacion = 0

    while (true) {
        println("""
            |RESERVAS DE SALA
            |1. Reservar una plaza
            |2. Cancelar una reserva
            |3. Consultar ocupación
            |4. Consultar recaudación
            |0. Finalizar
        """.trimMargin())
        print("Opción: ")

        when (readLine()?.trim()?.toIntOrNull()) {
            1 -> {
                val entrada = leerNumeroDePlaza()
                val plaza = entrada
                when {
                    plaza == null || plaza !in 1..TOTAL_PLAZAS ->
                        println("Número de plaza no válido: ${entrada ?: ""}")
                    ocupadas[plaza - 1] ->
                        println("No se pudo reservar: la plaza $plaza está ocupada")
                    else -> {
                        ocupadas[plaza - 1] = true
                        recaudacion += precioDePlaza(plaza)
                        println("Reserva confirmada: plaza $plaza")
                    }
                }
            }
            2 -> {
                val entrada = leerNumeroDePlaza()
                val plaza = entrada
                when {
                    plaza == null || plaza !in 1..TOTAL_PLAZAS ->
                        println("Número de plaza no válido: ${entrada ?: ""}")
                    !ocupadas[plaza - 1] ->
                        println("No se pudo cancelar: la plaza $plaza está libre")
                    else -> {
                        ocupadas[plaza - 1] = false
                        recaudacion -= precioDePlaza(plaza)
                        println("Reserva cancelada: plaza $plaza")
                    }
                }
            }
            3 -> {
                val plazasOcupadas = ocupadas.count { it }
                println("Plazas ocupadas: $plazasOcupadas")
                println("Plazas libres: ${TOTAL_PLAZAS - plazasOcupadas}")
            }
            4 -> println("Recaudación actual: $recaudacion euros")
            0 -> return
            null -> println("Opción no válida")
            else -> println("Opción no válida")
        }
    }
}
