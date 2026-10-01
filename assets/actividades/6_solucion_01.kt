/*
Enunciado: Define una función de saludo completa y otra con cuerpo de expresión.
*/

package soluciones.seccion6.ejercicio01

fun saludarCompleto(nombre: String) {
    println("Hola, $nombre!")
}

fun saludarExpresion(nombre: String) = println("Hola, $nombre!")

fun main() {
    saludarCompleto("Ana")
    saludarExpresion("Luis")
}
