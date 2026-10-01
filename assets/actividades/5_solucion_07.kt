/*
Enunciado: Calcula la suma, la media, el máximo y el mínimo de un array.
*/
package soluciones.seccion5.ejercicio07

fun main() {
    val numeros = intArrayOf(4, 8, 12, 16)

    println("Suma: ${numeros.sum()}")
    println("Media: ${numeros.average()}")
    println("Máximo: ${numeros.maxOrNull()}")
    println("Mínimo: ${numeros.minOrNull()}")
}
