/*
Enunciado: Usa filter, indexOf y contains sobre un array de enteros.
*/
package soluciones.seccion5.ejercicio06

fun main() {
    val numeros = intArrayOf(3, 8, 12, 5, 20, 7)

    val mayoresQueSiete = numeros.filter { numero -> numero > 7 }
    val indiceDelDoce = numeros.indexOf(12)
    val contieneCinco = numeros.contains(5)

    println("Mayores que 7: $mayoresQueSiete")
    println("Índice del 12: $indiceDelDoce")
    println("¿Contiene el 5?: $contieneCinco")
}
