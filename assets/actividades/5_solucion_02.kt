/*
Enunciado: Accede y modifica elementos de un array de cadenas.
*/
package soluciones.seccion5.ejercicio02

fun main() {
    val nombres = arrayOf("Ana", "Luis", "Marta")

    println("Segundo nombre: ${nombres[1]}")
    nombres[1] = "Carlos"
    println("Array modificado: ${nombres.joinToString()}")
}
