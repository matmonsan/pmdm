/*
Enunciado: Recorre un array con forEach y forEachIndexed.
*/
package soluciones.seccion5.ejercicio03

fun main() {
    val frutas = arrayOf("manzana", "pera", "plátano")

    println("Recorrido con forEach:")
    frutas.forEach { fruta -> println(fruta) }

    println("Recorrido con forEachIndexed:")
    frutas.forEachIndexed { indice, fruta ->
        println("Posición $indice: $fruta")
    }
}
