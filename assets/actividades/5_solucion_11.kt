/*
Enunciado: Procesa las calificaciones finales de seis alumnos (0 a 10). Indica
para cada uno aprobado (nota >= 5), suspenso o no presentado. Calcula la media
solo con las notas disponibles, e indica quién obtuvo la más alta y la más baja.
Si todas son null, informa de que no se puede calcular la media.
Datos: Ana 8.0, Luis null, Marta 4.5, Iker 10.0, Nora 6.0, Pablo 0.0.
*/
package soluciones.seccion5.ejercicio11

import java.util.Locale

data class Alumno(val nombre: String, val nota: Double?)

fun main() {
    val alumnos = arrayOf(
        Alumno("Ana", 8.0),
        Alumno("Luis", null),
        Alumno("Marta", 4.5),
        Alumno("Iker", 10.0),
        Alumno("Nora", 6.0),
        Alumno("Pablo", 0.0)
    )

    var suma = 0.0
    var cantidadNotas = 0
    var notaMasAlta = Double.NEGATIVE_INFINITY
    var alumnoConNotaMasAlta = ""
    var notaMasBaja = Double.POSITIVE_INFINITY
    var alumnoConNotaMasBaja = ""

    for (alumno in alumnos) {
        val nota = alumno.nota
        if (nota == null) {
            println("${alumno.nombre}: no presentado")
            continue
        }

        val resultado = if (nota >= 5.0) "aprobado" else "suspenso"
        println("${alumno.nombre}: $resultado")

        suma += nota
        cantidadNotas++
        if (nota > notaMasAlta) {
            notaMasAlta = nota
            alumnoConNotaMasAlta = alumno.nombre
        }
        if (nota < notaMasBaja) {
            notaMasBaja = nota
            alumnoConNotaMasBaja = alumno.nombre
        }
    }

    if (cantidadNotas == 0) {
        println("No hay notas disponibles; no se puede calcular la media.")
    } else {
        val media = suma / cantidadNotas
        println("Media: ${"%.2f".format(Locale.US, media)}")
        println("Nota más alta: $alumnoConNotaMasAlta ($notaMasAlta)")
        println("Nota más baja: $alumnoConNotaMasBaja ($notaMasBaja)")
    }
}
