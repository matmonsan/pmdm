/*
Enunciado: Crea procesarMensaje, que reciba una cadena y una lambda de tipo
(String) -> String, aplique la lambda y devuelva el resultado. Prueba con
"  hola, kotlin  ": una lambda debe quitar espacios y convertir a mayúsculas;
otra debe quitar espacios y añadir !. Los resultados son "HOLA, KOTLIN" y
"hola, kotlin!". No uses listas ni arrays.
*/

package soluciones.seccion7.ejercicio01

fun procesarMensaje(
    texto: String,
    operacion: (String) -> String
): String = operacion(texto)

fun main() {
    val texto = "  hola, kotlin  "

    val mayusculas = procesarMensaje(texto) { mensaje ->
        mensaje.trim().uppercase()
    }
    val conExclamacion = procesarMensaje(texto) { mensaje ->
        mensaje.trim() + "!"
    }

    println(mayusculas) // HOLA, KOTLIN
    println(conExclamacion) // hola, kotlin!

    check(mayusculas == "HOLA, KOTLIN")
    check(conExclamacion == "hola, kotlin!")
}
