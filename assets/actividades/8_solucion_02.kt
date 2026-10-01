/*
Enunciado: Crea Imprimible con imprimir() y una clase abstracta Empleado con
nombre, calcularSueldo() e imprimir(). Implementa EmpleadoFijo (salario mensual)
y EmpleadoPorHoras (horas por tarifa), validando que importes, horas y tarifas
no sean negativos. En main, prueba un salario de 1500.0 y 10 horas a 15.0; los
sueldos esperados son 1500.0 y 150.0. Trata ambos objetos como Empleado e
Imprimible.
*/

package soluciones.empleados

interface Imprimible {
    fun imprimir()
}

abstract class Empleado(val nombre: String) : Imprimible {
    abstract fun calcularSueldo(): Double

    override fun imprimir() {
        println("$nombre: ${calcularSueldo()} euros")
    }
}

class EmpleadoFijo(
    nombre: String,
    private val salarioMensual: Double
) : Empleado(nombre) {
    init {
        require(salarioMensual >= 0.0) { "El salario no puede ser negativo" }
    }

    override fun calcularSueldo(): Double = salarioMensual
}

class EmpleadoPorHoras(
    nombre: String,
    private val horas: Double,
    private val tarifaPorHora: Double
) : Empleado(nombre) {
    init {
        require(horas >= 0.0) { "Las horas no pueden ser negativas" }
        require(tarifaPorHora >= 0.0) { "La tarifa no puede ser negativa" }
    }

    override fun calcularSueldo(): Double = horas * tarifaPorHora
}

fun imprimirEmpleado(empleado: Empleado) {
    val imprimible: Imprimible = empleado
    imprimible.imprimir()
}

fun main() {
    val empleadoFijo: Empleado = EmpleadoFijo("Ana", 1500.0)
    val empleadoPorHoras: Empleado = EmpleadoPorHoras("Luis", 10.0, 15.0)

    check(empleadoFijo.calcularSueldo() == 1500.0)
    check(empleadoPorHoras.calcularSueldo() == 150.0)

    imprimirEmpleado(empleadoFijo)
    imprimirEmpleado(empleadoPorHoras)
}
