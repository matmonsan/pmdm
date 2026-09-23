---
layout: default
title: Repaso de Kotlin con ejemplos
---

# REPASO DE KOTLIN CON EJEMPLOS

**PMDM**  
**Santiago Rodenas Herráiz**

## Índice

- [Introducción](#introducción)
- [Recordando conceptos](#recordando-conceptos)
- [Variables primitivas en Kotlin](#variables-primitivas-en-kotlin)
- [Estructuras de control y repetición](#estructuras-de-control-y-repetición)
- [Arrays](#arrays)
- [Funciones](#funciones)
- [Funciones lambda](#funciones-lambda)
- [Clases](#clases)
- [Función de extensión en Kotlin](#función-de-extensión-en-kotlin)
- [Data class](#data-class)
- [Listas inmutables y mutables](#listas-inmutables-y-mutables)
- [Mapas](#mapas)
- [Callback](#callback)

## INTRODUCCIÓN

Con este documento, pretendo ofrecer una visión rápida del lenguaje Kotlin, en comparación con el Java que ya conocéis del curso pasado. A través de sencillos ejemplos y explicaciones, espero proporcionar una introducción efectiva al lenguaje. No pretendo cubrir un módulo completo de programación de primer año de DAM, pero he observado que en años anteriores era necesario dedicar una o dos semanas para familiarizarse con este lenguaje. Kotlin, poco a poco será el lenguaje que sustituya a Java en la programación de aplicaciones con Android.

### Variables

En esta sección, exploraremos cómo se declaran y utilizan las variables en Kotlin. Veremos la diferencia entre variables inmutables (`val`) y mutables (`var`), así como las convenciones para nombrarlas y los tipos de datos más comunes que se utilizan.

### Sentencias

Aquí abordaremos las sentencias de control de flujo en Kotlin, como `if`, `when`, y `for`. Estas estructuras nos permiten controlar la ejecución del código según diferentes condiciones y realizar iteraciones sobre colecciones.

### Arrays

En esta parte, aprenderemos cómo trabajar con arrays en Kotlin. Veremos cómo declararlos, inicializarlos y manipular sus elementos. También discutiremos cómo Kotlin ofrece una variedad de funciones útiles para trabajar con arrays.

### Funciones

Exploraremos cómo definir y utilizar funciones en Kotlin. Abordaremos la sintaxis básica para declarar funciones y cómo Kotlin maneja los valores de retorno y los parámetros.

### Lambda

Las expresiones lambda son una característica poderosa de Kotlin. En esta sección, aprenderemos cómo se definen y utilizan las lambdas, así como los casos en los que son especialmente útiles, como en funciones de orden superior. En el último punto, trataremos los callback.

### Clases

En esta sección, discutiremos cómo definir y utilizar clases en Kotlin. Veremos la sintaxis para crear clases, propiedades, métodos y constructores, y cómo se aplican conceptos de orientación a objetos como herencia y polimorfismo.

### Extensiones

Aquí aprenderemos sobre las funciones de extensión en Kotlin, que permiten añadir nuevas funcionalidades a clases existentes sin modificarlas. Veremos cómo definir funciones de extensión y cómo pueden mejorar la legibilidad y modularidad del código.

### Data Class

Las `data class` en Kotlin son una forma conveniente de manejar datos. En esta sección, veremos cómo definir una `data class`, así como los métodos automáticamente generados como `toString()`, `equals()`, `hashCode()`, y `copy()`.

### Listas

En esta parte, discutiremos el trabajo con listas en Kotlin, tanto inmutables (`List`) como mutables (`MutableList`). Veremos cómo declarar, inicializar y manipular listas, y cómo Kotlin ofrece funciones útiles para trabajar con ellas.

### Map

Exploraremos los mapas (`Map`), que son colecciones de pares clave-valor. Veremos cómo crear, inicializar y manipular mapas, y cómo utilizar funciones como `put`, `get`, `remove`, y `forEach`.

### Callback

Finalmente, abordaremos el concepto de callbacks, que son funciones que se pasan como parámetros y se ejecutan cuando una tarea asíncrona se completa. Veremos cómo definir y utilizar callbacks en Kotlin, y cómo esto se relaciona con operaciones asíncronas y la programación basada en eventos.

Actualmente, soy profesor de 2º DAM en el IES Virgen del Carmen de Jaén. Imparto la asignatura de PMDM (Programación multimedia y dispositivos móviles) y la asignatura de PSP (Programación servicios y procesos). Para cualquier consulta, puedes contactar en la siguiente dirección de correo electrónico:

- **Email:** srodher115@g.educaand.es
- [GitHub Repository](https://github.com/srodenas?tab=repositories)
- Curso 24/25
- [Documentación oficial de Kotlin](https://kotlinlang.org/docs/home.html)
- © 2024 Santiago Rodenas Herráiz.

## RECORDANDO CONCEPTOS

En clase, hablaremos de los siguientes conceptos:

1. **Java.** Lenguaje de programación POO desarrollado por Sun en 1995 y uno de los más utilizados tanto a nivel empresarial para web como para aplicaciones móviles. Es portable porque puede ser ejecutado en cualquier sistema.
2. **Kotlin.** Lenguaje sustituto de Android Studio, más moderno que Java y oficial por Google. Fue desarrollado por JetBrains en 2011. Soporta programación funcional y corrutinas. Tenemos integridad en desarrollo software con Kotlin/Ktor, tanto en el front como en el back.
3. **JRE.** Java Runtime Environment engloba la JVM, encargada de interpretar y ejecutar el bytecode generado después de la compilación realizada por el JDK. La máquina virtual debe instalarse según el sistema operativo y la arquitectura del procesador. La JVM interpreta el bytecode y genera instrucciones para la arquitectura concreta.
4. **JDK.** El kit de desarrollo software contiene lo necesario para desarrollar aplicaciones: compilador (`javac`) y librerías del lenguaje. En Kotlin, el compilador (`kotlinc`) no se encuentra dentro del JDK, pero debe generar bytecode compatible con él.
5. **SDK.** Conjunto de herramientas necesarias para desarrollar una aplicación: IDE, documentación, herramientas de compilación, librerías, emuladores y plugins. En Android se relaciona con el nivel de API. Una aplicación con API mínima 34 no podrá ejecutarse en Android 33.

La relación general es: el SDK utiliza Gradle para gestionar dependencias y construir el proyecto; Kotlin necesita el JDK; Kotlin compila a bytecode compatible con Java; la JVM interpreta ese bytecode y necesita el sistema operativo y la arquitectura hardware.

## VARIABLES PRIMITIVAS EN KOTLIN

### Declaración tipo entero

```kotlin
fun main() {
	val a = 1 // No se puede modificar
	var b = 2 // Identificamos el tipo por el valor
	val c = a + b

	var d: Int = 0 // Inicializamos al mismo tiempo
	var e: Int // No es necesario inicializarlo.

	// b = 2.9 // No puedo hacerlo: ya se declaró como entero.
	val f = 20.9
	b = f.toInt() // Casting
	print("El valor de f es $f y el de b es $b. También puedo poner la suma: ${b + f.toInt()}")
}
```

**Resultado:** `El valor de f es 20.9 y el de b es 20. También puedo poner la suma: 40`.

### Reales, booleanos y cadenas

```kotlin
fun main() {
	val myInt: Int = 1
	val myInt2 = 2
	val myInt3 = myInt * myInt2

	val myDouble: Double = 1.0
	val myDouble2 = 2.0
	val myDouble3: Double
	val myDouble4 = 3
	myDouble3 = myDouble + myDouble4 * myDouble2

	val myString = "Santi"
	val myString1: String = "soy " + myString

	val myBool = true
	var myBool1 = myBool && true
	myBool1 = myBool1 && false

	println("Estamos repasando de kotlin y $myString1")
	println("Tipos enteros: $myInt3")
	println("Tipos reales: $myDouble3")
	println("Tipos booleanos: $myBool1")
}
```

**Resultado:**

```text
Estamos repasando de kotlin y soy Santi
Tipos enteros: 2
Tipos reales: 7.0
Tipos booleanos: false
```

Un `Float` reserva 32 bits y un `Double`, 64 bits. En un `Float` es necesario añadir la letra `f`. Los `Double` se utilizan cuando queremos precisión y los `Float` cuando el ahorro de memoria sea crítico.

```kotlin
fun main() {
	val myDouble: Double = 3.141592653589793
	val myFloat: Float = 3.1415927f
	println("Valor de Double: $myDouble")
	println("Valor de Float: $myFloat")
}
```

Para convertir entre ambos tipos se utilizan `toFloat()` y `toDouble()`:

```kotlin
val myDouble: Double = 9.87654321
val myFloat: Float = myDouble.toFloat()
println("Double a Float: $myFloat")

val anotherFloat: Float = 3.14159f
val anotherDouble: Double = anotherFloat.toDouble()
println("Float a Double: $anotherDouble")
```

### Anulables

Los tipos anulables permiten que una variable tome el valor `null`. Para declararlos se escribe `?` después del tipo.

```kotlin
var nombre: String? = null
var edad: Int? = 25

if (nombre != null) {
	println("El nombre es: $nombre")
} else {
	println("El nombre es nulo")
}

val longitudNombre = nombre?.length ?: 0
println("La longitud del nombre es: $longitudNombre")
```

El operador `?.` accede a una propiedad sólo si el receptor no es `null`. El operador `?:` proporciona un valor alternativo. El operador `!!` afirma que el valor no es nulo, pero lanza `NullPointerException` si la afirmación es falsa.

```kotlin
val longitudNombre = nombre?.length
val longitudForzada = nombre!!.length // Puede lanzar NullPointerException

fun obtenerNombre(): String? = null

fun obtenerNombreConPredeterminado(): String =
	obtenerNombre() ?: "Nombre predeterminado"
```

`let` permite ejecutar un bloque sólo si el valor no es nulo:

```kotlin
fun procesarNombre(nombre: String?) {
	nombre?.let {
		println("El nombre es $it")
	} ?: run {
		println("El nombre es null")
	}
}

fun main() {
	procesarNombre("Santi")
	procesarNombre(null)
}
```

También puede encadenar operaciones sobre el mismo objeto:

```kotlin
fun main() {
	val yo = "Santiago Rodenas Herráiz"

	yo.let { it.uppercase() }
		.let { nombreMayus ->
			val partes = nombreMayus.split(" ")
			val nombre = partes[0]
			val apellido1 = partes[1]
			val apellido2 = partes[2]
			Triple(nombre, apellido2, apellido1)
		}
		.let {
			print("Mi nombre con el apellido cambiado es ${it.first}, ${it.second}, ${it.third}")
		}
}
```

El código siguiente no tiene sentido tal como está escrito: `nombre.let` recibe un `String?`, por lo que `it.length` no es seguro. La forma coherente sería `nombre?.let { ... } ?: run { ... }`; usar `it!!.length` puede provocar una excepción.

**Resumen:** usa `?` para declarar anulables, `if` para comprobarlos, `?:` para valores predeterminados, `?.` para acceso seguro y `!!` sólo cuando la no nulidad esté garantizada.

### ACTIVIDADES

1. Declara varias variables enteras (`val` y `var`), realiza operaciones básicas y muestra el resultado.
2. Declara variables `Double`, `Float`, `Boolean` y `String`; realiza operaciones y muestra los resultados.
3. Declara un `Double` y un `Float` y observa la diferencia de precisión.
4. Convierte un `Double` a `Float`.
5. Convierte un `Float` a `Double`.
6. Declara una variable sin inicializar, asígnale un valor y úsala.
7. Convierte un `Double` a `Int` y muestra ambos valores.
8. Concatena dos cadenas.
9. Usa un `Boolean` en una condición.
10. Declara variables `var` y `val`, modifica sólo las primeras y explica la diferencia.
11. Define una función que reciba `String?` y devuelva un valor mediante Elvis.
12. Recibe `Int?` y usa `let` para imprimir su doble o indicar que es `null`.
13. Usa `?: run` sólo cuando exista un objeto `?.let`.
14. Encadena operaciones con `let`.
15. Filtra una lista de `String?` con `filterNotNull()`.
16. Devuelve la longitud de un `String?` o cero usando `?.let`.
17. Recibe dos `Int?` y devuelve su suma o un valor predeterminado si alguno es nulo.

## ESTRUCTURAS DE CONTROL Y REPETICIÓN

### Condicionales compuestas

```kotlin
fun main() {
	val myInt = 9

	if (myInt < 0)
		println("Numero negativo, es $myInt")
	else if (myInt <= 10 && myInt != 5)
		println("Numero entre 0 y 10 y distinto de 5 es $myInt")
	else if (myInt == 5)
		println("Número igual a 5")
	else
		println("Número mayor que 10 es $myInt")
}
```

### Condicional múltiple `when`

En Java se utiliza `switch`; Kotlin ofrece un `when` más expresivo.

```kotlin
fun main() {
	val pais = "España"
	var moneda = ""

	when (pais) {
		"España", "Francia", "Alemania", "Italia" -> moneda = "Euro"
		"EEUU" -> moneda = "Dolar"
		"Venezuela" -> moneda = "Bolibar"
		else -> moneda = "N.I."
	}

	println("La moneda del pais $pais es $moneda")

	val sueldo = 1000
	when (sueldo) {
		in 700..900 -> println("Sueldo de 700 a 900")
		in 901..1200 -> println("Sueldo de 901 a 1200")
		in 1201..2000 -> println("Sueldo de menos de 2000")
		else -> println("Otro sueldo")
	}
}
```

### Bucles

```kotlin
fun main() {
	var x = 0
	while (x < 10) {
		print(" $x ")
		x += 2
	}

	println("\nAhora do-while")
	x = 0
	do {
		print(" $x ")
		x += 2
	} while (x < 10)
}
```

```kotlin
fun main() {
	var suma = 0
	for (i in 1..10) {
		print("Ingrese un valor:")
		val valor = readLine()!!.toInt()
		suma += valor
	}
	println("La suma de los valores ingresados es $suma")
	val promedio = suma / 10
	println("Su promedio es $promedio")
}
```

```kotlin
fun main() {
	var suma = 0
	for (i in 0..10 step 2) {
		println("Número par: $i")
		suma += i
	}
	println("La suma de los números pares es: $suma")

	for (i in 10 downTo 0 step 2) {
		println("Contamos... Estado actual: $i")
	}
	println("¡BUMMMMMMM!")
}
```

En un rango decremental debe usarse `downTo`: `10..0 step 2` no funciona como se espera porque el rango ascendente no puede recorrer esos límites.

### ACTIVIDADES

1. Pide un número e indica si es positivo, negativo o cero.
2. Pregunta la edad e indica si es mayor o menor de edad.
3. Recibe una calificación de 0 a 10 y muestra suspenso, aprobado, notable o sobresaliente.
4. Recibe un día y usa `when` para distinguir laborables y descanso.
5. Imprime los primeros 10 números pares con `while`.
6. Con `do-while`, suma desde 1 hasta el número indicado y continúa hasta recibir uno negativo.
7. Pide 5 números e imprime el mayor.
8. Cuenta de 20 a 0 de dos en dos.
9. Recibe un número de 1 a 12 y muestra el mes con `when` y rangos.
10. Cuenta cuántos números entre 1 y el indicado son divisibles por 3.

## ARRAYS

Formas habituales:

- `arrayOf(v1, v2, ..., vn)`: array genérico inicializado con valores.
- `Array(tamaño) { lambda }`: array inicializado mediante lambda.
- `IntArray(...)`, `DoubleArray(...)`: arrays especializados.
- `listOf(...)`: lista inmutable, no un array.

```kotlin
// Java
// int[] arr = new int[5];
// String[] names = {"Santi", "Sonia", "Guille", "Diego"};

val arr = IntArray(5)
val names = arrayOf("Santi", "Sonia", "Guille", "Diego")
```

`var` permite cambiar la referencia a otro array; `val` no permite cambiarla, aunque sí se pueden cambiar los elementos de un array mutable.

```kotlin
var array = arrayOf(1, 2, 3, 4)
val array2 = arrayOf(1, 2, 3, 4)
array[1] = 10
array2[0] = 20
array = array2
// array2 = array // Error: array2 es val
```

### Declaración, acceso y recorridos

```kotlin
fun main() {
	val arrayInmutable = listOf(1, 2, 3, 4)
	val myArray = arrayOf("lunes", "Martes", "Miercoles", "jueves", "Viernes", "Sabado", "Domingo")

	val martes = myArray[1]
	val miercoles = myArray.get(2)
	myArray[3] = "Jueves"
	myArray.set(0, "Lunes")

	myArray.forEach {
		if (it == "Sabado") println("Sabado, el mejor día de la semana") else println(it)
	}
	for (a in myArray) println(a)

	for (i in myArray.indices) print("${myArray[i]} ")
	for (i in myArray.indices step 2) print("${myArray[i]} ")
	for (i in 2 until myArray.size - 1) print("${myArray[i]} ")
	for (i in myArray.size - 1 downTo 0) print("${myArray[i]} ")
	for (pos in myArray.indices) println(myArray.get(pos))
	for ((pos, valor) in myArray.withIndex()) println("La posicion $pos tiene de valor $valor")
}
```

Un rango (`0..10`) puede recorrerse, pero no es un array y no permite acceso mediante `[]`.

### Funciones integradas

```kotlin
val myArray1 = arrayOf(1, 2, 3.3, "santi")
val myArray2 = intArrayOf(1, 2, 3, 4)
val myArray3 = doubleArrayOf(1.4, 2.6)
val myArray4 = Array(5) { it * 2 }
val myArray5 = Array(5) { index ->
	when (index) {
		0 -> 1.4
		1 -> 2.5
		3 -> 5.3
		else -> 0.0
	}
}
myArray5.forEach { println(it) }
```

`forEach` y `forEachIndexed` permiten recorrer elementos e índices. `filter`, `indexOf` y `contains` permiten buscar y filtrar. `sum`, `average`, `maxOrNull` y `minOrNull` agregan valores. `sorted` y `sortedDescending` ordenan.

```kotlin
val myArray = IntArray(20) { it }
val pares = myArray.filter { it % 2 == 0 }
val index = myArray.indexOf(3)
val exists = myArray.contains(4)

val total = myArray.sum()
val avg = myArray.average()
val max = myArray.maxOrNull()
val min = myArray.minOrNull()

val numbers = intArrayOf(5, 2, 9, 1, 7)
println(numbers.sorted())
println(numbers.sortedDescending())
```

Conversiones frecuentes: `toList()`, `toMutableList()`, `toSet()`, `toMap()` y `toTypedArray()`.

```kotlin
fun main() {
	val myArray = intArrayOf(5, 2, 9, 1, 7)
	println("Convertido a List: ${myArray.toList()}")
	println("Convertido a MutableList: ${myArray.toMutableList()}")
	println("Convertido a Set: ${myArray.toSet()}")

	val pairsArray = arrayOf("Santi" to 25, "Sonia" to 30, "Guille" to 15, "Diego" to 10)
	val mapFromArray = pairsArray.toMap()
	println(mapFromArray)
}
```

`map` devuelve una lista; para volver a un array hay que usar `toTypedArray()`.

```kotlin
fun main() {
	val originalPrices = doubleArrayOf(100.0, 150.0, 200.0, 250.0)
	val increasedPrices = originalPrices.map { price -> price * 1.20 }
	println("Precios originales: ${originalPrices.joinToString(", ")}")
	println("Precios incrementados en un 20%: $increasedPrices")
}
```

### ACTIVIDADES

1. Declara e inicializa un array de enteros y otro de cadenas.
2. Accede y modifica elementos de un array de cadenas.
3. Recorre un array con `forEach` y `forEachIndexed`.
4. Recorre un array con `for` tradicional y con `step`.
5. Declara y recorre el rango de 0 a 10.
6. Usa `filter`, `indexOf` y `contains`.
7. Calcula suma, media, máximo y mínimo.
8. Ordena ascendente y descendentemente.
9. Convierte un array a lista inmutable, lista mutable y conjunto.
10. Usa `map` y `mapIndexed`, recordando que `map` devuelve una lista.

## FUNCIONES

Una función se define con `fun`, seguida del nombre, parámetros, tipo de retorno y cuerpo.

```kotlin
fun saludar(nombre: String): String {
	return "Hola, $nombre!"
}

fun saludarConciso(nombre: String) = "Hola, $nombre!"

fun decirHola() {
	println("Hola mundo!")
}

fun mostrarMensaje(mensaje: String = "Mensaje por defecto") {
	println(mensaje)
}
```

Kotlin permite pasar funciones como argumentos. Si no devuelve un resultado, el tipo de retorno es `Unit`.

```kotlin
fun realizaSuma(a: Int, b: Int): Int = a + b

fun devuelveSumaArray(myArray: Array<Int>): Int {
	var suma = 0
	for (i in myArray.indices) suma += myArray[i]
	return suma
}

fun devuelveSumaArray1(myArray: IntRange): Int {
	var suma = 0
	for (x in myArray) suma += x
	return suma
}
```

Los tipos primitivos se pasan por valor; los objetos y arrays se pasan mediante referencia.

```kotlin
fun modificarNumero(numero: Int) {
	var copiaNumero = numero
	copiaNumero += 5
	println("Dentro de la función: $copiaNumero")
}

fun loadData(): IntArray = IntArray(10) { kotlin.random.Random.nextInt(0, 99) }

fun printValuesOfArr(arr: IntArray) {
	println(arr.joinToString(", "))
}
```

### ACTIVIDADES

1. Define una función de saludo completa y otra con cuerpo de expresión.
2. Crea una función sin parámetros que imprima `Hola mundo!`.
3. Crea una función con un parámetro `String` predeterminado.
4. Suma un array y un rango de enteros.
5. Modifica un `Int` dentro de una función y demuestra que el original no cambia.
6. Crea un array aleatorio, devuélvelo y muéstralo desde otra función.

## FUNCIONES LAMBDA

### Referencias a funciones

Una variable puede almacenar la referencia a una función.

```kotlin
fun imprimeTuNombre(nombre: String) {
	println("Tu nombre es $nombre")
}

fun main() {
	val myFun: (String) -> Unit = ::imprimeTuNombre
	myFun("Santiago Rodenas Herraiz")
	myFun("Sonia Mena Delgado")
}
```

También podemos cambiar la función almacenada:

```kotlin
fun suma(a: Int, b: Int) = a + b
fun resta(a: Int, b: Int) = a - b
fun multi(a: Int, b: Int) = a * b

fun main() {
	var operacion: (Int, Int) -> Int = ::suma
	println(operacion(2, 3))
	operacion = ::resta
	println(operacion(2, 3))
	operacion = ::multi
	println(operacion(2, 3))
}
```

### Expresiones lambda

Una lambda es una función anónima con la forma `{ parámetros -> cuerpo }`.

```kotlin
fun main() {
	var operacion: (Int, Int) -> Int = { a, b -> a + b }
	println(operacion(2, 3))
	operacion = { a, b -> a - b }
	println(operacion(2, 3))
	operacion = { a, b -> a * b }
	println(operacion(2, 3))
}
```

### Funciones de orden superior

```kotlin
fun operacion(a: Int, b: Int, fn: (Int, Int) -> Int): Int = fn(a, b)

fun main() {
	println(operacion(2, 3) { a, b -> a + b })
	println(operacion(2, 3) { a, b -> a - b })
}
```

Una función de orden superior recibe o devuelve otra función. La lógica queda definida en el lugar de la llamada.

```kotlin
fun printValuesOfArr(arr: IntArray, fn: (Int) -> Boolean) {
	val filteredArr = arr.filter(fn)
	println(filteredArr.joinToString(", "))
}

fun main() {
	val myArr = IntArray(10) { kotlin.random.Random.nextInt(0, 99) }
	printValuesOfArr(myArr) { true }
	printValuesOfArr(myArr) { it % 2 == 0 }
	printValuesOfArr(myArr) { it % 3 == 0 || it % 5 == 0 }
	printValuesOfArr(myArr) { it >= 50 }
	printValuesOfArr(myArr) {
		when (it) {
			in 1..10, in 20..30, in 90..95 -> true
			else -> false
		}
	}
}
```

Otros ejemplos son `forEach`, `count`, `all` y `any`:

```kotlin
fun myFun(arr: IntArray, fn: (Int) -> Unit) {
	for (v in arr) fn(v)
}

fun myFun2(arr: IntArray, fn: (Int) -> Boolean): Int {
	var cantidad = 0
	for (v in arr) if (fn(v)) cantidad++
	return cantidad
}

fun myFun3(arr: IntArray, fn: (Int) -> Boolean): Int = arr.count(fn)
```

### ACTIVIDADES

1. Define `saluda`, guarda su referencia en `miSaludo` e invócala con varios nombres.
2. Define `multiplica`, `divide` y `resta`; cambia la función almacenada en `operacion`.
3. Crea `sumaLambda` de tipo `(Int, Int) -> Int`.
4. Cambia la lógica de `sumaLambda` para realizar una resta.
5. Define `ejecutaOperacion` y úsala con suma y multiplicación.
6. Define `aplicaFiltro` para filtrar pares, impares y múltiplos.
7. Define `procesaArray` para contar y sumar usando lambdas.
8. Simplifica el ejercicio mediante `filter` y `map`.
9. Acepta lambdas anónimas que sumen, resten y multipliquen.
10. Cuenta elementos que cumplen una condición.
11. Suma elementos que cumplen una condición.
12. Filtra y cuenta elementos.
13. Imprime valores seleccionados por una lambda.
14. Transforma datos con una lambda.
15. Compón dos funciones y aplícalas en secuencia.

## CLASES

### Declaración de clases

```kotlin
class Persona(var name: String, var age: Int) {
	override fun toString() = "Nombre: $name y su edad: $age"
	fun isAdult() = age >= 18
}

fun myFun(persons: Array<Persona>, fn: (String) -> Unit) {
	persons.forEach { fn(it.name) }
}

fun main() {
	val persons = arrayOf(
		Persona("Santi", 46),
		Persona("Sonia", 45),
		Persona("Guille", 14),
		Persona("Diego", 11)
	)
	var cantidad = 0
	persons.forEach {
		println(it)
		if (it.isAdult()) cantidad++
	}
	println("La cantidad de personas adultas es $cantidad")
	myFun(persons) { if (it.count() > 5) println("$it tiene mas de 5 letras") }
}
```

### Constructores, `init`, getters y setters

```kotlin
class Alumno() {
	var dni: String = ""
	var name: String = ""
		set(value) {
			if (value.isNotEmpty()) field = value.uppercase()
		}
	var age: Int = 0
	var phone: String = ""

	constructor(dni: String, name: String, age: Int, phone: String) : this() {
		this.dni = dni
		this.name = name
		this.age = age
		this.phone = phone
	}

	init {
		dni = ""
	}

	override fun toString() = "Dni: $dni, nombre: $name, edad: $age, telefono: $phone"
}
```

El bloque `init` se ejecuta al crear el objeto. Un setter usa `value` y `field`. Los atributos `private` sólo son visibles dentro de la clase.

```kotlin
class PersonaGetSet {
	var name: String? = null
		set(value) {
			if (value.isNullOrEmpty()) println("El valor debe contener texto") else field = value
		}
		get() = field ?: "<Sin nombre>"

	var age: Int = 0
		set(value) { field = if (value >= 18) value else 0 }
}
```

### Lambdas y clases

```kotlin
class MyArray {
	var arr = IntArray(10) { kotlin.random.Random.nextInt(0, 11) }

	fun printArray() = println(arr.joinToString(", "))
	fun printElementTo5() = println("La cantidad de elementos menor que 5 es ${arr.count { it <= 5 }}")
	fun printAllTo9() = println(if (arr.all { it <= 9 }) "Todos son menor o igual que 9" else "Hay números mayores que 9")
	fun printElementoTo10() = println(if (arr.any { it == 10 }) "Hay un elemento que tiene al menos un 10" else "No hay ningun elemento que sea 10")
}
```

### Relaciones entre clases

```kotlin
data class PersonalData(val name: String?, val phone: String?)

class Employee(val nroEmp: Int, val personalData: PersonalData?)

fun main() {
	val enterprise = arrayOf(
		Employee(1, null),
		Employee(2, PersonalData(null, null)),
		Employee(3, PersonalData("santi", null)),
		Employee(4, PersonalData("sonia", "953 12 34 56"))
	)
	enterprise.forEach { employee ->
		employee.personalData?.let { data ->
			val name = data.name ?: "No tiene nombre"
			val phone = data.phone ?: "No tiene teléfono"
			println("$name. $phone")
		}
	}
}
```

### Herencia

Las clases son finales por defecto. Para permitir herencia se usa `open`; la subclase se declara con `:`.

```kotlin
open class SeleccionFutbol(
	protected var id: Int,
	protected var name: String,
	protected var surname: String,
	protected var age: Int
) {
	fun concentrate() = println("El integrante se está concentrando")
	fun travel() = println("El integrante está viajando")
	override fun toString() = "integrante con id=$id, nombre es $name, $surname, con edad $age"
}

class Entrenador(id: Int, name: String, surname: String, age: Int, var idFederacion: String) :
	SeleccionFutbol(id, name, surname, age) {
	fun leadGame() = println("El entrenador con nombre $name está dirigiendo un partido de futbol")
	fun leadTraining() = println("El entrenador con nombre $name está dirigiendo un entrenamiento")
	override fun toString() = "${super.toString()}, idFederacion=$idFederacion-ES"
}

class Futbolista(
	id: Int, name: String, surname: String, age: Int,
	var dorsal: Int, var demarcacion: String = "Sin demarcacion..."
) : SeleccionFutbol(id, name, surname, age) {
	fun playGame() = println("El jugador con nombre $name, $surname, está jugando un partido de futbol")
	fun train() = println("El jugador con nombre $name, $surname, está entrenando")
}

class Masajista(
	id: Int, name: String, surname: String, age: Int,
	var title: String, var ageExperience: Int
) : SeleccionFutbol(id, name, surname, age) {
	fun giveMassage() = println("El masajista con nombre $name, está dando un masaje")
}
```

Los atributos que deban heredarse pueden ser `protected`. Las subclases llaman al constructor de la superclase y pueden invocar sus métodos.

### Clases abstractas

Cuando cada subclase debe implementar su propia versión de una operación, se declara una clase abstracta y un método abstracto.

```kotlin
abstract class Integrante {
	abstract fun training()
}

class EntrenadorAbstracto : Integrante() {
	override fun training() = println("El entrenador está entrenando")
}

class FutbolistaAbstracto : Integrante() {
	override fun training() = println("El jugador está entrenando")
}

fun main() {
	val integrantes: List<Integrante> = listOf(EntrenadorAbstracto(), FutbolistaAbstracto())
	integrantes.forEach { it.training() }
}
```

Así no es necesario hacer casteos al recorrer objetos de la clase padre.

### Interfaces

```kotlin
interface IntegranteSeleccionFutbol {
	var anio: Int
	fun training()
	fun travel()
	fun concentrarse() {
		println("Estamos concentrados desde la interfaz")
	}
}
```

Una interfaz define un contrato. Puede declarar propiedades y métodos abstractos, y también incluir implementaciones por defecto.

### ACTIVIDADES

1. Declara `Animal` con nombre, edad y `toString()`.
2. Crea `Vehiculo` con constructor secundario y método de impresión.
3. Crea `Estudiante` e implementa `isAprobado()`.
4. Crea `Libro` con setter de título en mayúsculas.
5. Crea `CuentaBancaria` con método privado para actualizar el saldo.
6. Crea `Producto` y aplica un descuento porcentual.
7. Crea `Punto` y calcula la distancia entre dos puntos.
8. Crea `Empleado` con datos personales anulables.
9. Crea `Matriz` y suma sus diagonales.
10. Crea `Rectangulo` y calcula el área.
11. Implementa herencia con `Animal`, `Perro` y `Gato`.
12. Implementa herencia con `Vehiculo`, `Coche` y `Motocicleta`.
13. Implementa una clase abstracta `Animal` con `hacerSonido()`.
14. Implementa una clase abstracta `Vehiculo` con `mover()`.
15. Define `ComportamientoAnimal`, una clase abstracta `Animal` y las subclases `Perro` y `Gato`, aprovechando polimorfismo para llamar a `hacerSonido()`, `moverse()`, `dormir()` y `alimentarse()`.

## FUNCIÓN DE EXTENSIÓN EN KOTLIN

Las funciones de extensión permiten añadir funciones a clases existentes sin modificarlas.

```kotlin
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date?.myFormat(): String? {
	val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ", Locale.getDefault())
	return this?.let { formatter.format(it) }
}

fun Date?.myLength(): Int = this.myFormat()?.length ?: 0

fun Date?.toLower(): String? = this.myFormat()?.lowercase()

fun main() {
	val dat = Date()
	println("Santi, la fecha actual es ${dat.myFormat()} y su longitud es ${dat.myLength()}")
	println("Ahora devuelvo la misma fecha en minusculas ${dat.toLower()}")
	println("La longitud de un null es ${null.myLength()}")
}
```

El receptor también puede ser anulable (`Date?`) y el resultado puede serlo (`String?`).

### ACTIVIDADES

1. Crea las extensiones `isPalindrome`, `toPigLatin`, `reverseWords` y `wordCount` para `String`.
2. Crea `sumSquares`, `maxMinDiff`, `average` y `filterEven` para `List<Int>`.

## DATA CLASS

Una `data class` representa datos y genera automáticamente `toString()`, `equals()`, `hashCode()` y `copy()`. Sólo las propiedades del constructor primario participan en esas operaciones.

```kotlin
data class Persona(var nombre: String, var edad: Int)

fun main() {
	val per1 = Persona("Santi", 40)
	val per2 = Persona("Sonia", 35)
	println(per1)
	println(per1 == per2)
	val per3 = per1.copy(edad = 50)
	println(per1 == per3)
	println("El hashcode de Santi es ${per1.hashCode()} y el de Sonia es ${per2.hashCode()}")
	println("El hashcode de la copia de Santi es ${per3.hashCode()}")
}
```

Una propiedad declarada fuera del constructor no interviene en `equals`, `hashCode`, `toString` ni `copy`:

```kotlin
data class PersonaConTelefono(var nombre: String, var edad: Int) {
	var telefono: String? = null
}

fun main() {
	val per1 = PersonaConTelefono("Santi", 40)
	val per2 = PersonaConTelefono("Sonia", 35)
	per1.telefono = "953 111 222"
	per2.telefono = "953 222 222"
	val per3 = per1.copy()
	println(per1)
	println(per2)
	println(per1 == per2)
	println(per1 == per3)
}
```

### ACTIVIDADES

1. Crea `Producto(nombre, precio)`, compara dos objetos y usa `copy()` modificando el precio.
2. Crea `Empleado(nombre, salario)` con el método `anualSalario()`.
3. Crea `Libro(titulo, autor, anioPublicacion, genero?)`, usa valores opcionales y `copy()`.

## LISTAS INMUTABLES Y MUTABLES

### Listas inmutables

Una lista inmutable no permite añadir, eliminar ni modificar elementos.

```kotlin
val numbers: List<Int> = listOf(1, 2, 3, 4, 5)
println(numbers)
```

También se puede inicializar con una lambda:

```kotlin
data class PersonalData(val name: String, val phone: String?)

val listSamePersonal = List(3) { PersonalData("Santi", "953 34 54 34") }
val listAnonymous = List(3) { PersonalData("Anonimo_repetido", null) }
val listPersonal = listOf(
	PersonalData("Santi", "953 34 54 34"),
	PersonalData("Sonia", "953 34 54 35"),
	PersonalData("Guille", null),
	PersonalData("Diego", null)
)

println("Total de la lista ${listPersonal.size}")
println("Primero de la lista -> ${listPersonal.first()}")
println("Elemento en posición 1 -> ${listPersonal[1]}")
println("Último de la lista -> ${listPersonal.last()}")
listPersonal.forEach { println("Personal -> $it") }
```

Que la lista sea inmutable no significa que no puedan cambiarse propiedades mutables de los objetos que contiene.

### Listas mutables

```kotlin
data class PersonalDataMutable(val name: String, val phone: String?)

fun main() {
	val listPersonal = mutableListOf<PersonalDataMutable>()
	listPersonal.add(PersonalDataMutable("Santi", "953 34 54 34"))
	listPersonal.add(PersonalDataMutable("Sonia", "953 34 54 35"))
	listPersonal.add(PersonalDataMutable("Diego", null))
	listPersonal.add(PersonalDataMutable("Guille", null))

	listPersonal.removeAt(0)
	val numbersPhone = listPersonal.count { it.phone != null }
	println("El numero de telefonos disponibles son $numbersPhone")
	listPersonal.removeAll { it.phone == null }
	listPersonal.removeAll { it.name.count() >= 5 }
	println("El numero de Personal es de ${listPersonal.count()}")
}
```

`toMutableList()` convierte una lista en mutable sin alterar la lista original:

```kotlin
val immutableList = listOf("Elemento 1", "Elemento 2", "Elemento 3")
val mutableList = immutableList.toMutableList()
mutableList.add("Elemento 4")
mutableList[1] = "Elemento Modificado"
println("Lista mutable: $mutableList")
println("Lista inmutable original: $immutableList")
```

### ACTIVIDADES

1. Crea y recorre una lista inmutable de enteros del 1 al 5.
2. Convierte nombres de ciudades a mayúsculas con `map`.
3. Filtra los números pares.
4. Invierte una lista de `PersonalData` con `reversed`.
5. Usa `intersect` y `subtract` sobre dos listas.
6. Crea una `MutableList` inicializada a cero, añade y modifica elementos.
7. Usa `removeAt` y `count` sobre una lista de nombres.
8. Convierte una lista mutable y compara el resultado con la original.
9. Usa `removeAll` y `removeIf` para filtrar objetos.
10. Genera 15 números aleatorios, cuenta los menores que 5 y elimina los mayores que 8.

## MAPAS

Los mapas son colecciones de pares clave-valor. Las claves son únicas. Se usan `Map` para mapas no mutables y `MutableMap` para mapas modificables.

### Mapas inmutables

```kotlin
fun main() {
	val immutableMap = mapOf(
		"clave1" to "valor1",
		"clave2" to "valor2",
		"clave3" to "valor3"
	)
	println("Map inmutable: $immutableMap")
	println("Valor asociado con 'clave2': ${immutableMap["clave2"]}")
	for ((clave, valor) in immutableMap) println("Clave: $clave, Valor: $valor")
}
```

```kotlin
fun main() {
	val countries: Map<String, Int> = mapOf(
		Pair("España", 47000000),
		Pair("Francia", 60000000),
		"Alemania" to 80000000
	)
	val listCities = listOf(
		"Albacete" to 200000,
		"Jaen" to 120000,
		"Toledo" to 180000
	)
	val cities = listCities.toMap()
	countries.forEach { println("Pais-> ${it.key}, Hab-> ${it.value}") }
	cities.forEach { (city, population) -> println("Ciudad-> $city, Hab-> $population") }
	val numHabTo = cities.count { it.value > 150000 }
	println("Número de ciudades con mas de 150000 habitantes: $numHabTo")
	var totalHab = 0
	countries.forEach { totalHab += it.value }
	println("Número de habitantes de todos los paises: $totalHab")
	var totalHabLess = 0
	cities.forEach { if (it.value < 200000) totalHabLess += it.value }
	println("Suma de habitantes de ciudades inferiores a 200000: $totalHabLess")
}
```

### Mapas mutables

`apply` permite configurar el objeto dentro de un bloque de inicialización.

```kotlin
data class Signature(var name: String, var note: Double = 0.0)

class Alumn(var dni: String, var name: String) {
	val signatures: MutableList<Signature> = mutableListOf()

	fun addSignature(name: String, note: Double) {
		signatures.add(Signature(name, note))
	}

	fun removeSignature(name: String) {
		signatures.removeAll { it.name == name }
	}

	fun devSignatureNotab(): List<Signature> = signatures.filter { it.note >= 7 }

	override fun toString(): String = "(dni): $dni, (Alumno): $name\n$signatures"
}

fun printAll(alumns: MutableMap<String, Alumn>) {
	alumns.forEach { println(it.value) }
}

fun printAllWithSignature(alumns: MutableMap<String, Alumn>, fn: (String) -> Unit) {
	alumns.forEach { fn(it.key) }
}

fun main() {
	val alumns = mutableMapOf<String, Alumn>().apply {
		listOf(
			"11111" to Alumn("11111", "Santiago"),
			"22222" to Alumn("22222", "Sonia"),
			"33333" to Alumn("33333", "Mariano")
		).forEach { (dni, alum) -> put(dni, alum) }
	}

	alumns["11111"]?.apply {
		addSignature("Matematicas", 6.87)
		addSignature("Fisica", 7.87)
		addSignature("Tecnologia", 9.87)
		addSignature("Filosofia", 3.68)
		println(this)
		println(devSignatureNotab())
		removeSignature("Fisica")
	}

	printAll(alumns)
	printAllWithSignature(alumns) { dni ->
		val alum = alumns[dni]
		val failed = alum?.signatures?.count { it.note < 5 } ?: 0
		println("El alumno ${alum?.name} tiene $failed asignaturas suspensas")
	}
}
```

### ACTIVIDADES

1. Crea un mapa inmutable de tres claves y recórrelo.
2. Crea mapas de países y ciudades y cuenta las ciudades con más de 200.000 habitantes.
3. Suma habitantes totales y habitantes de ciudades con menos de 150.000.
4. Convierte una lista de pares a mapa.
5. Cuenta y filtra productos según su precio.
6. Define `Autor` y `Libro`, crea una lista de libros, muestra sus datos y comprueba si pertenecen a un autor determinado.

## CALLBACK

Un callback es una función que se pasa como parámetro y que otra función invoca al completar su tarea. Es especialmente útil en operaciones asíncronas como acceso a bases de datos, lectura de archivos o peticiones a una API.

### Llamada síncrona

El hilo principal espera a que termine la operación.

```kotlin
fun getDataBBDD(sql: String, callback: (List<String>) -> Unit) {
	println("Se procede a petición de datos.....")
	Thread.sleep(3000)
	val data = List(3) { "Datos obtenidos a partir de $sql" }
	println("Se han devuelto los datos")
	callback(data)
}

fun main() {
	println("Comenzamos nuestra aplicación..")
	Thread.sleep(1000)
	println("Ahora procedemos a petición de datos...")
	getDataBBDD("name = santi") { data ->
		println("Los datos obtenidos son:")
		data.forEach { println(it) }
	}
}
```

### Llamada asíncrona

La operación se ejecuta en otro hilo y el hilo principal puede continuar.

```kotlin
fun getDataBBDD(sql: String, callback: (List<String>) -> Unit) {
	println("Se procede a petición de datos.....")
	Thread {
		Thread.sleep(5000)
		val data = List(3) { "Datos obtenidos a partir de $sql - Elemento ${it + 1}" }
		println("Se han devuelto los datos asíncronos.")
		callback(data)
	}.start()
}

fun main() {
	println("Comenzamos nuestra aplicación...")
	println("Ahora procedemos a la petición de datos de manera asíncrona...")
	getDataBBDD("name = santi") { data ->
		println("Los datos obtenidos son:")
		data.forEach { println(it) }
		println("A que esto es lo último que veis?")
	}
	println("-------")
	println("Lo normal sería que esto se ejecutara al final, pero fijaros lo último que se ejecuta.")
}
```

### Ejemplo adaptado a corrutinas

En aplicaciones Android se utilizan corrutinas para ejecutar operaciones fuera del hilo de interfaz y volver después al hilo principal.

```kotlin
import kotlinx.coroutines.*

fun getDataBBDD(sql: String, callback: (List<String>) -> Unit) {
	GlobalScope.launch(Dispatchers.IO) {
		println("Se procede a petición de datos.....")
		delay(3000)
		val data = List(3) { "Datos obtenidos a partir de $sql" }
		println("Se han devuelto los datos asíncronos.")
		withContext(Dispatchers.Main) {
			callback(data)
		}
	}
}

fun main() {
	println("Comenzamos nuestra aplicación...")
	getDataBBDD("name = santi") { data ->
		println("Los datos obtenidos son:")
		data.forEach { println(it) }
		println("A que esto es lo último que veis?")
	}
	println("-------")
	println("Lo normal sería que esto se ejecutara al final, pero fijaros lo último que se ejecuta.")
	Thread.sleep(5000)
}
```

En una aplicación Android no sería necesario este `sleep` de consola: el ciclo de vida de la interfaz y del `CoroutineScope` se encargan de mantener la operación.

### ACTIVIDADES

#### 1. Callback síncrono

1. Define `procesarNombres(nombres, callback)` con `callback: (String) -> Unit`.
2. Recorre los nombres e invoca el callback de forma secuencial.
3. Crea una lista y muestra cómo cada nombre se procesa en el orden original.

#### 2. Callback asíncrono

1. Define `descargarContenido(url, callback)` con `callback: (String) -> Unit`.
2. Usa un hilo, una corrutina o un `ExecutorService` para simular la descarga.
3. Invoca el callback al terminar y muestra el contenido descargado.
4. Comprueba que el hilo principal no queda bloqueado y que el callback se ejecuta después de la operación.

---

**REPASO DE KOTLIN CON EJEMPLOS — PMDM**  
Santiago Rodenas Herráiz

