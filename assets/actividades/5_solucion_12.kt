/*
Enunciado: Analiza las puntuaciones de ocho rondas (0 a 30). Muestra suma,
media, máximos y mínimos con sus rondas, cuántas rondas alcanzan 20 puntos y
el mensaje de rendimiento según si la media es al menos 18.
Entrada: 12, 20, 8, 25, 16, 30, 10, 19.
*/
package soluciones.seccion5.ejercicio12

fun main() {
    val puntuaciones = intArrayOf(12, 20, 8, 25, 16, 30, 10, 19)
    val total = puntuaciones.sum()
    val media = puntuaciones.average()
    val maximo = puntuaciones.maxOrNull() ?: error("No hay puntuaciones")
    val minimo = puntuaciones.minOrNull() ?: error("No hay puntuaciones")
    val rondaMaxima = puntuaciones.indexOf(maximo) + 1
    val rondaMinima = puntuaciones.indexOf(minimo) + 1
    val rondasDeVeinteOMas = puntuaciones.count { it >= 20 }

    println("Puntos totales: $total")
    println("Media: $media")
    println("Puntuación máxima: $maximo (ronda $rondaMaxima)")
    println("Puntuación mínima: $minimo (ronda $rondaMinima)")
    println("Rondas con 20 puntos o más: $rondasDeVeinteOMas")
    println(if (media >= 18.0) "Buen rendimiento" else "Rendimiento por mejorar")
}
