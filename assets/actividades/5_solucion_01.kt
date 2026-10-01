/*
Enunciado: Declara e inicializa un array de enteros y otro de cadenas.
*/
package soluciones.seccion5.ejercicio01

fun main() {
    val edades = intArrayOf(18, 20, 25)
    val nombres = arrayOf("Ana", "Luis", "Marta")

    println(edades.joinToString())
    println(nombres.joinToString())
}
