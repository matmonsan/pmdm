/*
Enunciado: Convierte un array en una lista inmutable, una lista mutable y un conjunto.
*/
package soluciones.seccion5.ejercicio09

fun main() {
    val colores = arrayOf("rojo", "azul", "rojo", "verde")

    val listaInmutable: List<String> = colores.toList()
    val listaMutable: MutableList<String> = colores.toMutableList()
    val conjunto: Set<String> = colores.toSet()

    listaMutable.add("amarillo")

    println("Lista inmutable: $listaInmutable")
    println("Lista mutable: $listaMutable")
    println("Conjunto sin repetidos: $conjunto")
}
