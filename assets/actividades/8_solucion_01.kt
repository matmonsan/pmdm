/*
Enunciado: Crea Libro con ISBN, título y precio. El ISBN no debe cambiar tras
crear el objeto, pero el título y el precio sí. Usa init para rechazar títulos
vacíos y precios negativos. Añade aplicarDescuento(porcentaje), que acepte
valores entre 0 y 100, y un método para mostrar la ficha. En main, crea el libro
9781234567890, "Kotlin básico", 30.0, aplica un 10 % de descuento y muestra la
ficha; el precio final debe ser 27.0.
*/

package soluciones.libro

class Libro(
    val isbn: String,
    var titulo: String,
    var precio: Double
) {
    init {
        require(isbn.isNotBlank()) { "El ISBN no puede estar vacío" }
        require(titulo.isNotBlank()) { "El título no puede estar vacío" }
        require(precio >= 0.0) { "El precio no puede ser negativo" }
    }

    fun aplicarDescuento(porcentaje: Double) {
        require(porcentaje in 0.0..100.0) {
            "El descuento debe estar entre 0 y 100"
        }
        precio *= 1.0 - porcentaje / 100.0
    }

    fun mostrarFicha() {
        println("ISBN: $isbn")
        println("Título: $titulo")
        println("Precio: $precio euros")
    }
}

fun main() {
    val libro = Libro("9781234567890", "Kotlin básico", 30.0)
    libro.aplicarDescuento(10.0)
    libro.mostrarFicha()

    check(libro.precio == 27.0) { "El precio final debería ser 27.0" }
}
