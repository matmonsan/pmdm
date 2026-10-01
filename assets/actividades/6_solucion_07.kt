/*
Enunciado: Crea un programa que gestione las puntuaciones de una máquina recreativa
a partir de un array puntuaciones. Divide la lógica en funciones para obtener la
mejor puntuación, contar las partidas que superan un umbral, clasificar cada
puntuación como Leyenda, Experto, Aficionado o Novato, y encontrar la partida en
la que se obtuvo la mejor puntuación. Muestra el ranking completo y un resumen.

Entrada de ejemplo: intArrayOf(320, 850, 1200, 690, 410)
Salida esperada:
Partida 1: 320 puntos -> Novato
Partida 2: 850 puntos -> Experto
Partida 3: 1200 puntos -> Leyenda
Partida 4: 690 puntos -> Aficionado
Partida 5: 410 puntos -> Aficionado
Mejor puntuación: 1200 (partida 3)
Partidas récord (>700): 2
*/

package soluciones.seccion6.ejercicio07

fun obtenerMejorPuntuacion(puntuaciones: IntArray): Int =
    puntuaciones.maxOrNull() ?: error("No hay puntuaciones")

fun contarPartidasSobreUmbral(puntuaciones: IntArray, umbral: Int): Int =
    puntuaciones.count { it > umbral }

fun clasificarPuntuacion(puntuacion: Int): String = when {
    puntuacion >= 1000 -> "Leyenda"
    puntuacion >= 800 -> "Experto"
    puntuacion >= 400 -> "Aficionado"
    else -> "Novato"
}

fun obtenerNumeroPartidaMejorPuntuacion(puntuaciones: IntArray): Int {
    val mejorPuntuacion = obtenerMejorPuntuacion(puntuaciones)
    return puntuaciones.indexOf(mejorPuntuacion) + 1
}

fun mostrarRanking(puntuaciones: IntArray) {
    require(puntuaciones.isNotEmpty()) { "Debe haber al menos una partida" }

    puntuaciones.forEachIndexed { indice, puntuacion ->
        println("Partida ${indice + 1}: $puntuacion puntos -> ${clasificarPuntuacion(puntuacion)}")
    }

    val mejorPuntuacion = obtenerMejorPuntuacion(puntuaciones)
    val numeroPartida = obtenerNumeroPartidaMejorPuntuacion(puntuaciones)
    val partidasRecord = contarPartidasSobreUmbral(puntuaciones, 700)

    println("Mejor puntuación: $mejorPuntuacion (partida $numeroPartida)")
    println("Partidas récord (>700): $partidasRecord")
}

fun main() {
    val puntuaciones = intArrayOf(320, 850, 1200, 690, 410)
    mostrarRanking(puntuaciones)
}
