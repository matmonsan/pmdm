/*
Enunciado: Usa map y mapIndexed sobre un array y observa que ambos devuelven listas.
*/
package soluciones.seccion5.ejercicio10

fun main() {
    val edades = intArrayOf(18, 20, 22)

    val edadesEnMeses: List<Int> = edades.map { edad -> edad * 12 }
    val edadesConIndice: List<String> = edades.mapIndexed { indice, edad ->
        "Elemento ${indice + 1}: $edad años"
    }

    println("Edades en meses: $edadesEnMeses")
    println("Edades con posición: $edadesConIndice")
}
