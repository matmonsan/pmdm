/*
Enunciado: Modifica un Int dentro de una función y demuestra que el original no cambia.
*/

package soluciones.seccion6.ejercicio05

fun incrementarSinCambiarOriginal(numero: Int) {
    var copiaLocal = numero
    copiaLocal++
    println("Dentro de la función: $copiaLocal")
}

fun main() {
    val original = 5
    incrementarSinCambiarOriginal(original)
    println("Fuera de la función: $original") // Sigue valiendo 5
}
