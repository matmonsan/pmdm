/*
Enunciado: Crea un array aleatorio, devuélvelo y muéstralo desde otra función.
*/

package soluciones.seccion6.ejercicio06

import kotlin.random.Random

fun crearArrayAleatorio(cantidad: Int): IntArray {
    require(cantidad >= 0) { "La cantidad no puede ser negativa" }
    return IntArray(cantidad) { Random.nextInt(0, 100) }
}

fun mostrarArray(numeros: IntArray) {
    println(numeros.joinToString(", "))
}

fun main() {
    val numeros = crearArrayAleatorio(5)
    mostrarArray(numeros)
}
