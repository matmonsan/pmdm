/*
Enunciado: Ordena un array en orden ascendente y descendente.
*/
package soluciones.seccion5.ejercicio08

fun main() {
    val numeros = intArrayOf(12, 3, 25, 8, 1)
    val ascendente = numeros.sortedArray()
    val descendente = numeros.sortedArrayDescending()

    println("Original: ${numeros.joinToString()}")
    println("Ascendente: ${ascendente.joinToString()}")
    println("Descendente: ${descendente.joinToString()}")
}
