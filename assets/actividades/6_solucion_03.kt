/*
Enunciado: Crea una función con un parámetro String predeterminado.
*/

package soluciones.seccion6.ejercicio03

fun saludar(nombre: String = "mundo") {
    println("Hola, $nombre!")
}

fun main() {
    saludar()
    saludar("Marta")
}
