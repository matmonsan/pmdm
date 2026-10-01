/*
Enunciado: Define comprobarTexto, que reciba una cadena y una condición de tipo
(String) -> Boolean. Pásale una lambda que convierta el texto a minúsculas,
quite los espacios y compare la cadena con su reverso. Comprueba "Anita lava la
tina" (true) y "Kotlin" (false). Trabaja con una cadena por llamada, sin listas
ni arrays.
*/

package soluciones.seccion7.ejercicio02

fun comprobarTexto(
    texto: String,
    condicion: (String) -> Boolean
): Boolean = condicion(texto)

fun main() {
    val comprobarPalindromo: (String) -> Boolean = { texto ->
        val normalizado = texto.lowercase().replace(" ", "")
        normalizado == normalizado.reversed()
    }

    val frase = "Anita lava la tina"
    val palabra = "Kotlin"

    val fraseEsPalindromo = comprobarTexto(frase, comprobarPalindromo)
    val palabraEsPalindromo = comprobarTexto(palabra, comprobarPalindromo)

    println("$frase -> $fraseEsPalindromo") // true
    println("$palabra -> $palabraEsPalindromo") // false

    check(fraseEsPalindromo)
    check(!palabraEsPalindromo)
}
