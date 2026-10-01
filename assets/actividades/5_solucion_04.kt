/*
Enunciado: Recorre un array con un for tradicional y después recórrelo con step.
*/
package soluciones.seccion5.ejercicio04

fun main() {
    val numeros = intArrayOf(10, 20, 30, 40, 50, 60)

    println("Todos los elementos:")
    for (indice in numeros.indices) {
        println("Índice $indice: ${numeros[indice]}")
    }

    println("Un elemento sí y otro no:")
    for (indice in numeros.indices step 2) {
        println("Índice $indice: ${numeros[indice]}")
    }
}
