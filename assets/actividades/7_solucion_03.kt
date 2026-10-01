/*
Enunciado: Define comprobarNumero, que reciba un entero y una lambda de tipo
(Int) -> Boolean. Pásale una lambda que compruebe si el número es primo: los
menores que 2 no son primos y, para los demás, comprueba si tienen divisores
entre 2 y el número anterior. Prueba 7 (true), 12 (false) y 1 (false),
procesando un número por llamada.
*/

package soluciones.seccion7.ejercicio03

fun comprobarNumero(numero: Int, condicion: (Int) -> Boolean): Boolean = condicion(numero)

fun main() {
    val esPrimo: (Int) -> Boolean = { numero ->
        if (numero < 2) {
            false
        } else {
            var resultado = true
            for (divisor in 2 until numero) {
                if (numero % divisor == 0) {
                    resultado = false
                    break
                }
            }
            resultado
        }
    }

    val sieteEsPrimo = comprobarNumero(7, esPrimo)
    val doceEsPrimo = comprobarNumero(12, esPrimo)
    val unoEsPrimo = comprobarNumero(1, esPrimo)

    println("7 es primo: $sieteEsPrimo") // true
    println("12 es primo: $doceEsPrimo") // false
    println("1 es primo: $unoEsPrimo") // false

    check(sieteEsPrimo)
    check(!doceEsPrimo)
    check(!unoEsPrimo)
}
