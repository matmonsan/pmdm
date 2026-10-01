/*
Enunciado: Suma un array y un rango de enteros.
*/

package soluciones.seccion6.ejercicio04

fun sumarArray(numeros: IntArray): Int = numeros.sum()

fun sumarRango(numeros: IntRange): Int = numeros.sum()

fun main() {
    val numeros = intArrayOf(1, 2, 3, 4)
    val rango = 1..5

    println("Suma del array: ${sumarArray(numeros)}") // 10
    println("Suma del rango: ${sumarRango(rango)}") // 15
}
