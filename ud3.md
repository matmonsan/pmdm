---
title: UD3. Listas, diálogos y arquitectura MVVM
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al inicio](index.md)

## Índice

0. [Resumen de términos](#0-resumen-de-términos)
1. [Objetivos](#1-objetivos)
2. [Tipos de mensajes al usuario](#2-tipos-de-mensajes-al-usuario)
3. [Listas con LazyColumn](#3-listas-con-lazycolumn)
4. [Imágenes remotas con Coil](#4-imágenes-remotas-con-coil)
5. [Navegación entre pantallas con estado](#5-navegación-entre-pantallas-con-estado)
6. [Arquitectura MVVM y flujo de datos](#6-arquitectura-mvvm-y-flujo-de-datos)
7. [Diálogos declarativos](#7-diálogos-declarativos)
8. [Scaffold y Snackbar](#8-scaffold-y-snackbar)
9. [Fragmentos y navegación](#9-fragmentos-y-navegación)
10. [Actividades](#10-actividades)
    - [10.1 Trabajo con LazyColumns](#101-actividad-1-trabajo-con-lazycolumns)
    - [10.2 Navegación y arquitectura MVVM](#102-actividad-2-navegación-y-arquitectura-mvvm)
    - [10.3 Diálogos, Scaffold y Snackbar](#103-actividad-3-diálogos-scaffold-y-snackbar)

---

## 0. Resumen de términos

| Término | Qué significa |
|---|---|
| [`LazyColumn`](#31-cómo-funciona-lazycolumn) | Lista vertical que solo dibuja los elementos visibles. Sustituye a `RecyclerView`. |
| [`LazyRow`](#31-cómo-funciona-lazycolumn) | Lo mismo que `LazyColumn`, pero con desplazamiento horizontal. |
| [`items`](#34-claves-estables) | Función que genera un elemento de la lista por cada dato de una colección. |
| [Clave (`key`)](#34-claves-estables) | Identificador único de cada elemento, para que Compose sepa cuál es cuál. |
| [`data class`](#32-el-modelo-de-datos) | Clase de Kotlin pensada para guardar datos; incluye `copy()`. |
| [Modelo](#32-el-modelo-de-datos) | Clase que representa un dato de la aplicación (`Hotel`). |
| [`ElevatedCard`](#33-el-ítem-de-la-lista) | Tarjeta con sombra que agrupa el contenido de un elemento. |
| [Coil / `AsyncImage`](#4-imágenes-remotas-con-coil) | Librería y composable para mostrar imágenes descargadas de internet. |
| [Navegación con estado](#5-navegación-entre-pantallas-con-estado) | Cambiar de pantalla dibujando otro composable según una variable de estado, sin `NavHost`. |
| [`rememberSaveable`](#55-paso-3-apppantallaskt) | Como `remember`, pero conserva el valor al girar el dispositivo. |
| [`BackHandler`](#55-paso-3-apppantallaskt) | Captura el botón Atrás del sistema para decidir qué hacer. |
| [MVVM](#6-arquitectura-mvvm-y-flujo-de-datos) | Arquitectura que separa Modelo, Vista (UI) y ViewModel. |
| [Flujo unidireccional (UDF)](#61-por-qué-separar-la-interfaz-de-la-lógica) | El estado baja a la interfaz y los eventos suben al ViewModel. |
| [Repositorio](#62-el-repositorio) | Clase que ofrece los datos a la aplicación, sin importar de dónde vienen. |
| [`UiState`](#63-el-estado-de-la-interfaz) | Un único objeto que contiene todo lo que la pantalla necesita mostrar. |
| [`ViewModel`](#64-el-viewmodel) | Guarda el estado y la lógica de una pantalla y sobrevive a los giros. |
| [`mutableStateOf` en el ViewModel](#64-el-viewmodel) | Guarda el `UiState`; al asignarle un valor nuevo, Compose redibuja las pantallas que lo leen. |
| [`private set`](#64-el-viewmodel) | Permite leer una propiedad desde fuera de la clase, pero solo cambiarla desde dentro. |
| [`copy()`](#64-el-viewmodel) | Crea una copia de una `data class` cambiando solo algunos campos. |
| [`AlertDialog`](#7-diálogos-declarativos) | Ventana emergente con título, texto y botones. |
| [Lambda de evento](#73-diálogo-de-alta-y-edición) | Función que se pasa como parámetro y se ejecuta al ocurrir algo (`onConfirm`). |
| [`isError` / `supportingText`](#74-validar-el-formulario) | Parámetros de un campo de texto para marcarlo como erróneo y mostrar un mensaje debajo. |
| [`Scaffold`](#81-estructura-con-scaffold) | Estructura de pantalla con barra superior, botón flotante y mensajes. |
| [`FloatingActionButton`](#81-estructura-con-scaffold) | Botón circular flotante para la acción principal (añadir). |
| [`Snackbar`](#82-mensajes-con-snackbar) | Mensaje breve en la parte inferior. |
| [`LaunchedEffect`](#82-mensajes-con-snackbar) | Ejecuta una acción cuando cambia un valor, sin bloquear la pantalla. |
| [Fragment](#91-qué-era-un-fragment) | Pieza de pantalla del sistema clásico; en Compose se sustituye por composables. |
| [Navigation Compose](#93-navigation-compose) | Librería para cambiar entre pantallas. |

---

## 1. Objetivos

Al terminar esta unidad, podrás:

1. Mostrar una lista de elementos con `LazyColumn` y diseñar cada elemento como una tarjeta.
2. Cargar imágenes desde internet con Coil.
3. Cambiar entre pantallas usando solo estado, sin `NavHost`, y gestionar el botón Atrás.
4. Separar la interfaz, el estado y los datos con la arquitectura MVVM y un repositorio que realice el CRUD.
5. Guardar el estado de una pantalla en un `ViewModel` y observarlo desde Compose.
6. Mostrar diálogos para añadir, editar y confirmar el borrado de un elemento, validando los datos del formulario.
7. Avisar al usuario con un `Snackbar` después de una acción.
8. Pasar de una lista al detalle de un elemento y editarlo o borrarlo desde allí.
9. Entender qué ocupa el lugar de los `Fragment` en Compose.

Esta unidad construye, paso a paso, una aplicación de gestión de hospedajes: una lista de hoteles en la que se puede añadir, consultar, editar y borrar. Es lo que se pide en la Versión 1.2 del proyecto.

> **Antes de empezar.** Esta unidad usa lo visto en la UD2: estado (`remember`, elevar el estado), eventos (lambdas), layouts (`Row`, `Column`, `Card`) y varias pantallas con estado. Si algo no te suena, repasa los apartados 8, 14, 15.3 y 16 de la UD2.

> **Dependencias necesarias.** En `build.gradle` del módulo `app` hacen falta `io.coil-kt:coil-compose` (imágenes) y `androidx.lifecycle:lifecycle-viewmodel-compose` (`viewModel()`). Para descargar imágenes, añade en `AndroidManifest.xml` el permiso `<uses-permission android:name="android.permission.INTERNET" />`.

---

## 2. Tipos de mensajes al usuario

Una aplicación necesita avisar al usuario de lo que ocurre: "Libro borrado", "Guardado correctamente", "¿Seguro que quieres eliminarlo?". Android ofrece varias formas, según lo importante que sea el mensaje y si hace falta que el usuario responda.

| Tipo | Qué es | Interrumpe al usuario | Admite botones | Cuándo usarlo |
|---|---|---|---|---|
| `Toast` | Mensaje breve flotante, desaparece solo | No | No | Avisos sencillos y rápidos |
| `Snackbar` | Mensaje breve en la parte inferior, desaparece solo | No | Una acción (ej. "Deshacer") | Confirmar una acción; es la opción recomendada en Material |
| `AlertDialog` | Ventana emergente con título, texto y botones | Sí, bloquea la pantalla | Sí | Confirmaciones importantes (borrar) o formularios cortos |
| Texto en pantalla | Un `Text` que aparece o desaparece según el estado | No | No | Errores junto a un campo, estados vacíos |

### 2.1 Toast

![Toast](./assets/img/toast.png)

El `Toast` es el más sencillo: necesita el contexto de la aplicación, que se obtiene con `LocalContext.current`.

```kotlin
val context = LocalContext.current

Button(onClick = {
    Toast.makeText(context, "Libro guardado", Toast.LENGTH_SHORT).show()
}) {
    Text("Guardar")
}
```

- `Toast.LENGTH_SHORT` dura unos 2 segundos y `Toast.LENGTH_LONG` unos 3,5.
- No forma parte de la composición de Compose: no se puede personalizar ni lleva botones. Por eso Material recomienda `Snackbar`, pero sigue siendo útil por lo fácil que es.

### 2.2 Snackbar

![Snackbar](./assets/img/snack.png)

El `Snackbar` sí está integrado en Compose. Necesita tres piezas:

1. Un `SnackbarHostState`, que guarda los mensajes pendientes.
2. Un `Scaffold` con `snackbarHost`, que indica dónde se dibuja el mensaje.
3. Una corrutina (`rememberCoroutineScope`), porque `showSnackbar` es una función *suspend* (tarda un rato, mientras el mensaje está visible).

```kotlin
@Composable
fun PantallaConSnackbar() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Button(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Libro guardado")
                }
            },
            modifier = Modifier.padding(padding)
        ) {
            Text("Guardar")
        }
    }
}
```

Con una acción, por ejemplo "Deshacer":

```kotlin
scope.launch {
    val resultado = snackbarHostState.showSnackbar(
        message = "Libro borrado",
        actionLabel = "Deshacer"
    )
    if (resultado == SnackbarResult.ActionPerformed) {
        // el usuario ha pulsado "Deshacer"
    }
}
```

### 2.3 AlertDialog

![Alert](./assets/img/alert.png)

Cuando la aplicación necesita que el usuario tome una decisión antes de continuar —por ejemplo, confirmar un borrado— puede mostrar un `AlertDialog`. A diferencia de una `Snackbar`, el diálogo permanece en pantalla hasta que el usuario responde o lo cierra.

El diálogo no aparece automáticamente al escribir `AlertDialog`: la aplicación controla su visibilidad mediante una variable de estado. En este ejemplo, `mostrarDialogo` empieza en `false`; al pulsar «Borrar» pasa a `true` y Compose dibuja el diálogo. Si vuelve a `false`, el diálogo desaparece. El uso de `remember` y `mutableStateOf` se vio en el apartado 14.1 de la UD2; el apartado 7 de esta unidad muestra cómo controlar los diálogos con estado.

```kotlin
@Composable
fun PantallaLibro() {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Button(onClick = { mostrarDialogo = true }) {
        Text("Borrar")
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Borrar libro") },
            text = { Text("¿Seguro que quieres borrar este libro?") },
            confirmButton = {
                TextButton(onClick = {
                    // Aquí se ejecuta el borrado del libro.
                    mostrarDialogo = false
                }) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
```

Las partes importantes son:

- `title` y `text` contienen el título y el mensaje del diálogo.
- `confirmButton` define la acción principal. Su `onClick` no realiza ninguna acción por sí solo: ahí debes escribir el código que confirma la operación. En el ejemplo, el comentario indica dónde borrar el libro; después se cambia el estado para cerrar el diálogo.
- `dismissButton` define una alternativa para cancelar. En este ejemplo solo cierra el diálogo; no borra el libro.
- `onDismissRequest` se ejecuta cuando el usuario intenta cerrar el diálogo sin pulsar esos botones, por ejemplo, tocando fuera o usando el botón Atrás. Hay que actualizar el estado para ocultarlo. Si la aplicación no debe permitir ese cierre, puede dejar el estado sin cambiar.

Los botones tampoco cierran el diálogo automáticamente: cada `onClick` debe actualizar el estado (y, en el caso de confirmar, ejecutar la operación correspondiente). Así se evita que el diálogo realice cambios que no se hayan programado explícitamente.

### 2.4 ¿Cuál elegir?

- ¿Solo informas de algo que ya ha ocurrido? → `Snackbar` (o `Toast` si quieres algo rápido).
- ¿Quieres permitir deshacer? → `Snackbar` con acción.
- ¿Es una acción peligrosa o necesitas una respuesta? → `AlertDialog`.
- ¿Es un error de un campo de texto? → un `Text` bajo el campo, no un mensaje temporal.

---

## 3. Listas con LazyColumn

![LazyColumn](./assets/img/lazy.png)

### 3.1 Cómo funciona LazyColumn

#### El problema que resuelve

Imagina una aplicación de contactos con 500 personas. En la pantalla del móvil solo caben unas 8 a la vez. Si la aplicación creara los 500 elementos de golpe, tardaría en arrancar y gastaría mucha memoria para dibujar cosas que el usuario no está viendo.

`LazyColumn` (*lazy* significa "perezoso") resuelve esto: solo compone los elementos que están visibles (y unos pocos más cercanos). Al hacer scroll, crea los que entran en pantalla y descarta los que salen.
Funciona igual que el antiguo `RecyclerView`, pero sin adaptador, sin `ViewHolder` y sin XML.

#### ¿Y si la lista es pequeña?

Si solo tienes 3 o 4 elementos fijos, basta una `Column` normal. Una `Column` dibuja todo a la vez y, además, no tiene scroll salvo que se lo añadas (`Modifier.verticalScroll(...)`).

| | `Column` | `LazyColumn` |
|---|---|---|
| Dibuja | Todos los elementos | Solo los visibles |
| Scroll | Hay que añadirlo | Incluido |
| Úsala para | Pocos elementos fijos | Listas largas o de tamaño variable |

#### Tu primera lista

```kotlin
@Composable
fun ListaNombres() {
    val nombres = listOf("Ana", "Luis", "Marta", "Pedro")

    LazyColumn {
        items(nombres) { nombre ->
            Text(text = nombre)
        }
    }
}
```

Cómo se lee:

1. `nombres` es la lista de datos.
2. `LazyColumn { ... }` crea el contenedor con scroll.
3. `items(nombres)` recorre la lista y ejecuta el bloque de llaves una vez por cada elemento.
4. `nombre ->` es el nombre que damos al elemento actual (como la variable de un `for`). Dentro se escribe cómo se ve un elemento.

Resultado: cuatro textos, uno debajo de otro, con scroll si no caben.

#### `LazyColumn` frente a `LazyRow`

`LazyRow` es idéntico pero horizontal. Solo cambia el nombre:

```kotlin
LazyRow {
    items(nombres) { nombre -> Text(text = nombre, modifier = Modifier.padding(8.dp)) }
}
```

#### Otras formas de añadir elementos

Dentro de `LazyColumn` puedes mezclar varios tipos de contenido:

```kotlin
LazyColumn {
    item { Text("Cabecera", style = MaterialTheme.typography.titleLarge) }   // un elemento suelto
    items(nombres) { nombre -> Text(nombre) }                                // uno por cada dato
    item { Text("Fin de la lista") }                                         // otro elemento suelto
}
```

- `item { }` añade un elemento fijo (cabecera, pie, botón...).
- `items(lista) { }` añade uno por cada dato.
- `itemsIndexed(lista) { indice, dato -> }` es igual que `items`, pero además te da la posición (0, 1, 2...).

> **Importante.** Dentro de `LazyColumn { }` no se pueden escribir composables directamente (`Text("hola")` suelto da error). Siempre tienen que ir dentro de `item { }` o `items { }`. Esto es porque el bloque no es composable: describe *qué elementos hay*, y Compose decide cuándo dibujarlos.

### 3.2 El modelo de datos

Una lista casi nunca contiene solo textos: contiene objetos con varios datos (un hotel tiene nombre, ciudad, teléfono...). Un modelo es la clase que describe uno de esos objetos. En Kotlin se usa una `data class`, pensada para guardar datos: genera automáticamente `equals`, `toString` y `copy()`.

```kotlin
data class Hotel(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val city: String,
    val province: String,
    val phone: String,
    val image: String
)
```

- Todas las propiedades son `val`: el objeto es inmutable. Para "cambiar" un hotel se crea una copia con `copy()` (apartado 6.6).
- `id` se genera solo con `UUID`, que devuelve un texto único. Sirve para distinguir un hotel de otro aunque tengan el mismo nombre.

Ejemplo de uso y de `copy()`:

```kotlin
val hotel = Hotel(name = "Sol", city = "Málaga", province = "Málaga", phone = "952000000", image = "")
val renombrado = hotel.copy(name = "Sol y Mar")   // nuevo objeto; `hotel` no cambia
```

> **¿Por qué inmutable?** Compose detecta que hay que redibujar cuando recibe un objeto distinto. Si modificaras un objeto existente "por dentro", Compose no se enteraría. Con `copy()` siempre se crea uno nuevo y el cambio se nota.

### 3.3 El ítem de la lista

En el sistema clásico, cada fila necesitaba un XML (`item_hotel.xml`) y una clase `ViewHolder`. En Compose basta una función composable que dibuja un elemento. La lista simplemente la llama una vez por cada dato.

Recibe el dato y los eventos que debe avisar (editar y borrar), siguiendo el patrón de la UD2: *el estado baja, los eventos suben*. La tarjeta detecta la pulsación (`onEditClick`), pero no edita nada: llama a la lambda y es quien la recibe quien decide qué hacer.

La estructura visual que vamos a construir es:

```text
┌──────────────────────────────────────┐  ← ElevatedCard
│ ┌────────┐  Nombre del hotel         │
│ │ imagen │  Ciudad                   │  ← Row (imagen | Column con textos)
│ │        │  Provincia                │
│ └────────┘  Teléfono        [✎] [🗑] │
└──────────────────────────────────────┘
```

```kotlin
@Composable
fun HotelCard(
    hotel: Hotel,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = hotel.image,
                contentDescription = "Fotografía de ${hotel.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = hotel.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = hotel.city,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = hotel.province,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = hotel.phone,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar hotel",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar hotel",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
```

Cómo está construida:

- **`ElevatedCard`**: tarjeta con sombra que agrupa todo el contenido del hotel.
- **`Row`**: coloca la imagen a la izquierda y los textos a la derecha.
- **`Column(Modifier.weight(1f))`**: ocupa todo el ancho que sobra después de la imagen (apartado 7.4 de la UD2).
- **`Spacer`**: deja un hueco entre la imagen y los textos.
- **Colores del tema**: `primary`, `secondary` y `tertiary` vienen de `MaterialTheme.colorScheme`, no de valores fijos (apartado 13 de la UD2).
- **`onEditClick` y `onDeleteClick`**: la tarjeta no sabe cómo se edita o se borra un hotel; solo avisa de que se ha pulsado el botón. Esa lógica pertenece al `ViewModel`.
- **`contentDescription`**: texto que leen los lectores de pantalla para describir imágenes e iconos.

### 3.4 Claves estables

Al usar `items` se puede indicar una clave (`key`) que identifica de forma única cada elemento:

```kotlin
LazyColumn {
    items(
        items = listaDeHoteles,
        key = { hotel -> hotel.id }
    ) { hotel ->
        HotelCard(hotel = hotel, onEditClick = { }, onDeleteClick = { })
    }
}
```

Sin clave, Compose identifica los elementos por su posición. Si borras el primero, todos los demás cambian de posición y se redibujan. Con una clave estable, Compose sabe qué elemento ha desaparecido y solo actualiza lo necesario, además de conservar el estado interno de cada elemento. La clave debe ser única y no cambiar nunca: por eso se usa el `id`, no el nombre.

**Ejemplo.** Lista `[A, B, C]` y borras `A`:

- *Sin clave*: Compose ve que la posición 0 ahora contiene `B`, la 1 contiene `C` y la 2 ha desaparecido. Actualiza todo.
- *Con clave*: Compose ve que `A` ya no está y que `B` y `C` siguen igual. Solo elimina `A`.

Además, si cada elemento guarda estado propio (por ejemplo, un cuadro de texto a medio escribir), sin clave ese estado puede acabar "pegado" al elemento equivocado.

### 3.5 Aspecto de la lista: espacios y separación

`LazyColumn` acepta parámetros para mejorar el aspecto sin tocar cada ítem:

```kotlin
LazyColumn(
    modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp),            // margen alrededor de TODO el contenido
    verticalArrangement = Arrangement.spacedBy(8.dp)  // hueco entre elementos
) {
    items(hoteles, key = { it.id }) { hotel -> HotelCard(hotel, {}, {}) }
}
```

- `contentPadding`: espacio interior de la lista. A diferencia de `Modifier.padding`, el contenido sigue haciendo scroll por debajo del margen, sin quedar recortado. Útil para que el último elemento no quede tapado por un botón flotante.
- `verticalArrangement = Arrangement.spacedBy(...)`: separación uniforme entre elementos (en `LazyRow` se usa `horizontalArrangement`).
- `Modifier.fillMaxSize()` o `weight(1f)`: da a la lista un tamaño. Una `LazyColumn` necesita una altura limitada.

> **Error típico.** No metas una `LazyColumn` dentro de una `Column` con `verticalScroll`: ambas intentan hacer scroll y la aplicación se cierra con un error de "altura infinita". Si hay más elementos en la pantalla, pon la `LazyColumn` con `Modifier.weight(1f)` dentro de la `Column`.

### 3.6 Lista vacía

Cuando no hay datos, una pantalla en blanco confunde. Lo habitual es comprobarlo y mostrar un mensaje:

```kotlin
if (hoteles.isEmpty()) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Todavía no hay hoteles. Pulsa + para añadir uno.")
    }
} else {
    LazyColumn { items(hoteles, key = { it.id }) { HotelCard(it, {}, {}) } }
}
```

### 3.7 Errores frecuentes

| Síntoma | Causa | Solución |
|---|---|---|
| Error al escribir `Text()` dentro de `LazyColumn` | Falta `item { }` | Envuélvelo en `item { }` o `items { }` |
| La app se cierra al abrir la pantalla | `LazyColumn` dentro de un contenedor con scroll vertical | Quita el otro scroll |
| Al borrar, se redibuja todo o se mezcla el estado | No hay `key` | Añade `key = { it.id }` |
| La lista no se actualiza al añadir | Se modifica una `MutableList` normal | Usa una lista inmutable guardada en un estado (`mutableStateOf`) y asígnale una lista nueva (apartado 6) |
| El último elemento queda tapado por el botón `+` | Sin espacio inferior | `contentPadding = PaddingValues(bottom = 80.dp)` |

> **Resumen mental.** Datos (`List<Hotel>`) → `LazyColumn` → `items(key)` → un composable por dato (`HotelCard`). Cambias la lista, Compose actualiza la pantalla.

---

## 4. Imágenes remotas con Coil

Para mostrar una imagen que está en internet hay que descargarla sin bloquear la pantalla. Coil es la librería que se encarga de ello, y su composable `AsyncImage` descarga, guarda en caché y muestra la imagen:

```kotlin
AsyncImage(
    model = "https://picsum.photos/id/1011/300/300",
    contentDescription = "Fotografía del hotel",
    contentScale = ContentScale.Crop,
    modifier = Modifier
        .size(110.dp)
        .clip(RoundedCornerShape(16.dp))
)
```

| Parámetro | Para qué sirve |
|---|---|
| `model` | Dirección (URL) de la imagen. |
| `contentDescription` | Descripción para accesibilidad. |
| `contentScale` | Cómo se ajusta la imagen al espacio: `Crop` la recorta para llenar todo el hueco, `Fit` la muestra entera. |
| `modifier` | Tamaño (`size`) y forma (`clip`) de la imagen. |

Sustituye a Glide, que se usaba con las vistas clásicas. Recuerda añadir el permiso de internet al manifiesto (apartado 1).

---

## 5. Navegación entre pantallas con estado

La mayoría de aplicaciones tienen más de una pantalla: una lista y, al pulsar un elemento, su detalle. En este apartado se hace sin ninguna librería de navegación, solo con una variable de estado. Es la forma de cambiar de pantalla que se usa en las Versiones 1.1 y 1.2 del proyecto. (La navegación con `NavHost` se estudiará en la UD4).

### 5.1 La idea

En Compose, una pantalla es una función `@Composable`. Cambiar de pantalla es dibujar otra función. Para decidir cuál se dibuja basta una variable y un `if`:

```text
idSeleccionado == null      →  se dibuja PantallaPrincipal (la lista)
idSeleccionado == "a1b2"    →  se dibuja PantallaDetalle (el hotel "a1b2")
```

- Al pulsar un hotel de la lista, la variable pasa a valer su `id` → aparece el detalle.
- Al pulsar «Volver», la variable vuelve a `null` → aparece la lista.

![Navegación con estado: AppPantallas guarda idSeleccionado y, según su valor, dibuja PantallaPrincipal o PantallaDetalle; las pantallas avisan con eventos que cambian la variable](./assets/img/navegacion_estado.svg)

- Las flechas azules indican qué pantalla dibuja `AppPantallas` según el valor de `idSeleccionado`.
- Las flechas rojas son los eventos con los que avisan las pantallas. `AppPantallas` responde cambiando `idSeleccionado`, y Compose dibuja la otra pantalla.

Todo ocurre dentro de la misma `MainActivity`: no hay que crear otra `Activity` ni tocar el `AndroidManifest.xml`.

### 5.2 Los archivos del ejemplo

Cada pantalla va en su propio archivo `.kt`:

```text
app/src/main/java/com/ejemplo/hospedajes/
├── Hotel.kt                ← el modelo (apartado 3.2)
├── PantallaPrincipal.kt    ← la lista de hoteles
├── PantallaDetalle.kt      ← los datos de un hotel
├── AppPantallas.kt         ← decide qué pantalla se ve
└── MainActivity.kt         ← arranca AppPantallas
```

Para crear un archivo: clic derecho sobre el paquete → *New* → *Kotlin Class/File* → File, y se escribe el nombre (`PantallaPrincipal`). Se elige *File* porque dentro no hay una clase, sino una función `@Composable`.

La regla más importante: las pantallas no se conocen entre sí. `PantallaPrincipal` no sabe que existe `PantallaDetalle`; solo avisa de que se ha pulsado un hotel. Quien decide qué se muestra es `AppPantallas`.

### 5.3 Paso 1: `PantallaPrincipal.kt`

Muestra la lista. Recibe los hoteles y una función `onHotelClick` que llama al pulsar una tarjeta.

```kotlin
package com.ejemplo.hospedajes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaPrincipal(
    hoteles: List<Hotel>,
    onHotelClick: (Hotel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mis hoteles", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(hoteles, key = { it.id }) { hotel ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onHotelClick(hotel) }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(hotel.name, style = MaterialTheme.typography.titleMedium)
                        Text(hotel.city)
                    }
                }
            }
        }
    }
}
```

`onHotelClick: (Hotel) -> Unit` es una función que recibe un `Hotel` y no devuelve nada (apartado 14.2.3 de la UD2). La pantalla la llama con el hotel pulsado: `onHotelClick(hotel)`.

### 5.4 Paso 2: `PantallaDetalle.kt`

Muestra los datos de un hotel. Recibe el hotel y una función `onVolver` que llama al pulsar el botón.

```kotlin
package com.ejemplo.hospedajes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PantallaDetalle(
    hotel: Hotel,
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(hotel.name, style = MaterialTheme.typography.headlineMedium)
        Text("Ciudad: ${hotel.city}")
        Text("Provincia: ${hotel.province}")
        Text("Teléfono: ${hotel.phone}")

        Button(onClick = onVolver) {
            Text("Volver")
        }
    }
}
```

### 5.5 Paso 3: `AppPantallas.kt`

Aquí está la navegación. Guarda qué hotel está seleccionado y, según eso, dibuja una pantalla u otra.

```kotlin
package com.ejemplo.hospedajes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

// Datos de ejemplo: se crean una sola vez, al arrancar la aplicación
val hotelesDeEjemplo = listOf(
    Hotel(name = "Hotel Peñalta", city = "Bogarra", province = "Albacete", phone = "967 98 89 98", image = ""),
    Hotel(name = "Hotel Felipe II", city = "Ayna", province = "Albacete", phone = "967 22 33 44", image = ""),
    Hotel(name = "La Casa del Vaquero", city = "Abiada", province = "Cantabria", phone = "978 65 56 65", image = "")
)

@Composable
fun AppPantallas() {
    // null → se ve la lista; un id → se ve el detalle de ese hotel
    var idSeleccionado by rememberSaveable { mutableStateOf<String?>(null) }

    val hotelSeleccionado = hotelesDeEjemplo.find { it.id == idSeleccionado }

    // Botón Atrás del móvil: desde el detalle, vuelve a la lista
    BackHandler(enabled = hotelSeleccionado != null) {
        idSeleccionado = null
    }

    Box(modifier = Modifier.safeDrawingPadding()) {
        if (hotelSeleccionado == null) {
            PantallaPrincipal(
                hoteles = hotelesDeEjemplo,
                onHotelClick = { hotel -> idSeleccionado = hotel.id }
            )
        } else {
            PantallaDetalle(
                hotel = hotelSeleccionado,
                onVolver = { idSeleccionado = null }
            )
        }
    }
}
```

Línea a línea:

- **`var idSeleccionado by rememberSaveable { mutableStateOf<String?>(null) }`**: la variable que decide la pantalla. Empieza en `null` (la lista). Se usa `rememberSaveable` en vez de `remember` para que, al girar el móvil, no se vuelva a la lista (apartado 14.1.3 de la UD2). `<String?>` indica que guardará un texto o `null`.
- **`hotelesDeEjemplo.find { it.id == idSeleccionado }`**: busca el hotel con ese `id`. Si la variable es `null`, no encuentra ninguno y devuelve `null`.
- **`BackHandler`**: sin él, pulsar Atrás en el detalle cierra la aplicación. Con él, cuando hay un hotel seleccionado (`enabled = true`), Atrás pone la variable a `null` y se vuelve a la lista.
- **`Box(Modifier.safeDrawingPadding())`**: evita que el contenido quede debajo de la barra de estado del móvil.
- **`if / else`**: es la navegación. Solo una de las dos pantallas existe en cada momento.
- **`onHotelClick = { hotel -> idSeleccionado = hotel.id }`**: cuando la lista avisa, se guarda el `id` → Compose redibuja → aparece el detalle.
- **`onVolver = { idSeleccionado = null }`**: cuando el detalle avisa, se borra el `id` → aparece la lista.

> **¿Por qué se guarda el `id` y no el `Hotel`?** `rememberSaveable` solo sabe guardar tipos sencillos (`String`, `Int`, `Boolean`...). Además, en el apartado 7.3 los hoteles se podrán editar: guardando el `id` y buscando el hotel cada vez, el detalle siempre muestra los datos actualizados.
>
> **¿Por qué `hotelesDeEjemplo` está fuera de la función?** Cada `Hotel` genera un `id` nuevo al crearse (apartado 3.2). Si la lista se creara dentro de `AppPantallas`, los `id` cambiarían en cada recomposición y nunca coincidirían con el guardado. Fuera de la función se crea una sola vez. Más adelante esta lista la proporcionará el repositorio (apartado 6.2).

### 5.6 Paso 4: `MainActivity.kt`

La `Activity` ya no dibuja una pantalla concreta: llama a `AppPantallas()`.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HospedajesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppPantallas()
                }
            }
        }
    }
}
```

`HospedajesTheme` es el tema que Android Studio crea con el proyecto; su nombre depende del nombre de tu aplicación. `Surface` pone el color de fondo del tema detrás de las pantallas.

### 5.7 Qué ocurre al pulsar un hotel

1. El usuario pulsa la tarjeta de «Hotel Felipe II» en `PantallaPrincipal`.
2. La tarjeta llama a `onHotelClick(hotel)`.
3. En `AppPantallas` se ejecuta `idSeleccionado = hotel.id`.
4. El estado ha cambiado, así que Compose vuelve a ejecutar `AppPantallas`: ahora `hotelSeleccionado` no es `null` y el `if` dibuja `PantallaDetalle`.
5. El usuario pulsa «Volver» (o Atrás): `idSeleccionado = null` y vuelve a dibujarse la lista.

**Errores frecuentes**

| Síntoma | Causa | Solución |
|---|---|---|
| Al pulsar un hotel no pasa nada | La variable es un `var` normal, sin `mutableStateOf` | Usa `rememberSaveable { mutableStateOf(...) }` |
| Al girar el móvil se vuelve a la lista | Se usa `remember`, o la lista de hoteles se crea dentro del composable | Usa `rememberSaveable` y crea la lista fuera de la función |
| Atrás cierra la aplicación desde el detalle | Falta `BackHandler` | Añádelo en `AppPantallas` |
| `Unresolved reference: PantallaDetalle` | El archivo tiene otro `package` | Todos los archivos deben empezar con el mismo `package` |

> **¿Y con más pantallas?** La variable puede guardar el nombre de la pantalla (`"lista"`, `"detalle"`, `"ajustes"`) y elegir con un `when`, como en el apartado 15.3.2 de la UD2. Cuando la aplicación tenga muchas pantallas, menú lateral o barra inferior, se usará `NavHost` (UD4).

---

## 6. Arquitectura MVVM y flujo de datos

En el apartado 5, `AppPantallas` hacía todo: guardaba los datos, decidía qué pantalla se veía y respondía a los botones. Con dos pantallas y sin cambios en los datos funciona, pero en cuanto el usuario puede borrar, añadir o editar, aparecen problemas. En este apartado verás cuáles son y cómo se resuelven repartiendo el trabajo en tres piezas: repositorio, ViewModel y pantallas.

> **Vídeos introductorios.** Antes de empezar, puedes ver estos dos vídeos, en este orden:
>
> 1. [Android MVVM y ViewModel explicado](https://www.youtube.com/watch?v=KQDeY7oIxp4): qué es MVVM, cómo se estructura y qué es un ViewModel.
> 2. [ViewModel y State básico con Jetpack Compose](https://www.youtube.com/watch?v=X4gFllRa1_0): un ViewModel que guarda el estado y una pantalla de Compose que lo muestra.

### 6.1 Por qué separar la interfaz de la lógica

#### El problema

Imagina que añades un botón «Borrar» al detalle del apartado 5. Lo más directo sería escribir el borrado dentro de `AppPantallas`:

```kotlin
@Composable
fun AppPantallas() {
    var hoteles by remember { mutableStateOf(hotelesDeEjemplo) }
    var idSeleccionado by rememberSaveable { mutableStateOf<String?>(null) }

    val hotelSeleccionado = hoteles.find { it.id == idSeleccionado }

    if (hotelSeleccionado == null) {
        PantallaPrincipal(hoteles = hoteles, onHotelClick = { idSeleccionado = it.id })
    } else {
        PantallaDetalle(
            hotel = hotelSeleccionado,
            onVolver = { idSeleccionado = null },
            onBorrar = {
                hoteles = hoteles.filter { it.id != hotelSeleccionado.id }   // ← lógica aquí
                idSeleccionado = null
            }
        )
    }
}
```

Funciona, pero tiene tres problemas:

| # | Problema | Pruébalo |
|---|---|---|
| 1 | **Los cambios se pierden al girar el móvil.** `remember` se borra cuando la `Activity` se recrea, y la lista vuelve a ser la inicial. | Borra un hotel y gira el móvil: vuelve a aparecer. |
| 2 | **La lógica está mezclada con el dibujo.** Cuando haya que añadir, editar y validar, `AppPantallas` crecerá sin control y será difícil de leer. | Imagina este archivo con 10 operaciones más. |
| 3 | **Los datos están pegados a la pantalla.** Cuando vengan de una base de datos (Room, UD6) o de internet (UD7), habría que cambiar las pantallas. | ¿Dónde cambiarías `hotelesDeEjemplo` por una base de datos? |

#### La solución: repartir el trabajo

La arquitectura MVVM (*Model – View – ViewModel*) reparte el código en tres capas, cada una con una sola responsabilidad:

| Capa | Archivos del ejemplo | Qué hace | Qué no hace |
|---|---|---|---|
| **Vista** | `PantallaPrincipal.kt`, `PantallaDetalle.kt`, `AppPantallas.kt` | Dibuja el estado y avisa de las acciones del usuario con lambdas | No modifica los datos ni decide qué hacer ante una acción |
| **ViewModel** | `HospedajeViewModel.kt`, `HospedajeUiState.kt` | Guarda el estado de la pantalla y tiene una función por cada acción del usuario | No dibuja nada ni sabe cómo se guardan los datos |
| **Repositorio** (*Model*) | `Hotel.kt`, `HotelRepository.kt` | Guarda los datos y ofrece funciones para consultarlos y modificarlos | No conoce las pantallas ni su estado |

Cada capa resuelve uno de los problemas anteriores:

- El repositorio guarda los datos fuera de las pantallas → resuelve el problema 3.
- El ViewModel guarda el estado y sobrevive al giro de pantalla → resuelve el problema 1.
- El ViewModel contiene la lógica, y las pantallas solo dibujan → resuelve el problema 2.

#### El flujo de datos: el estado baja, los eventos suben

Las tres piezas se comunican siempre en el mismo sentido:

![Capas de MVVM: la vista envía eventos al ViewModel y recibe de él el estado; el ViewModel pide operaciones al repositorio y recibe los datos](./assets/img/mvvm_capas.svg)

- De la vista al ViewModel solo viajan eventos: llamadas a sus funciones, como `viewModel.onDeleteHotel(hotel)`.
- Del ViewModel a la vista solo viaja el estado: la pantalla lee `viewModel.uiState` y lo dibuja.
- El ViewModel pide operaciones al repositorio (`deleteHotel`) y le pide los datos actualizados (`getHotels()`). La vista nunca habla con el repositorio.

Esto se llama flujo unidireccional de datos (UDF, *Unidirectional Data Flow*). Es el mismo principio que usaste al elevar el estado en la UD2 (apartado 14.1.4) y en el apartado 5: las pantallas reciben datos y avisan con lambdas.

> **Regla de oro.** La vista nunca cambia los datos directamente: avisa al ViewModel. El ViewModel actualiza el estado y Compose redibuja la pantalla.

En los apartados siguientes se construye cada pieza, de abajo arriba: primero el repositorio (6.2), luego el estado (6.3), luego el ViewModel (6.4) y, por último, se conecta todo con las pantallas (6.5).

### 6.2 El repositorio

El repositorio es la clase que guarda los datos de la aplicación y la única que sabe dónde están: en una lista en memoria, en una base de datos o en internet. El resto de la aplicación solo llama a sus funciones: obtener los hoteles, añadir uno, actualizarlo o borrarlo.

De momento los datos están en una lista en memoria. Crea el archivo `HotelRepository.kt`:

```kotlin
object HotelRepository {
    private val hotels = mutableListOf(
        Hotel(
            name = "Antiguo Palacio de Atienza",
            city = "Atienza",
            province = "Guadalajara",
            phone = "975 45 54 45",
            image = "https://picsum.photos/id/1011/300/300"
        ),
        Hotel(
            name = "La Casa del Vaquero",
            city = "Abiada",
            province = "Cantabria",
            phone = "978 65 56 65",
            image = "https://picsum.photos/id/1025/300/300"
        ),
        Hotel(
            name = "Hotel Peñalta",
            city = "Bogarra",
            province = "Albacete",
            phone = "967 98 89 98",
            image = "https://picsum.photos/id/1040/300/300"
        ),
        Hotel(
            name = "Hotel Felipe II",
            city = "Ayna",
            province = "Albacete",
            phone = "967 22 33 44",
            image = "https://picsum.photos/id/1060/300/300"
        )
    )

    fun getHotels(): List<Hotel> = hotels.toList()

    fun getHotelById(id: String): Hotel? = hotels.find { it.id == id }

    fun addHotel(hotel: Hotel) {
        hotels.add(hotel)
    }

    fun updateHotel(hotel: Hotel) {
        val index = hotels.indexOfFirst { it.id == hotel.id }
        if (index != -1) {
            hotels[index] = hotel
        }
    }

    fun deleteHotel(id: String) {
        hotels.removeAll { it.id == id }
    }
}
```

**`object`** en lugar de `class` crea un único repositorio para toda la aplicación (patrón *Singleton*). No hace falta crearlo con `HotelRepository()`: se usa directamente por su nombre, y todas las pantallas comparten los mismos datos.

Cada función es una operación del CRUD:

| Función | Qué hace | Ejemplo de uso |
|---|---|---|
| `getHotels()` | Devuelve una copia de todos los hoteles | `val lista = HotelRepository.getHotels()` |
| `getHotelById(id)` | Busca un hotel; devuelve `null` si no existe | `val hotel = HotelRepository.getHotelById("a1b2")` |
| `addHotel(hotel)` | Añade un hotel | `HotelRepository.addHotel(nuevoHotel)` |
| `updateHotel(hotel)` | Sustituye el hotel que tiene el mismo `id` | `HotelRepository.updateHotel(hotelEditado)` |
| `deleteHotel(id)` | Borra el hotel con ese `id` | `HotelRepository.deleteHotel(hotel.id)` |

Detalles del código:

- **`private val hotels`**: la lista es privada. Nadie de fuera puede tocarla; solo se puede usar a través de las funciones.
- **`toList()`**: `getHotels()` no entrega la lista original, sino una copia. Cada llamada devuelve una lista nueva, y eso es lo que permite a Compose notar los cambios (apartado 3.2).
- **`indexOfFirst { ... }`**: devuelve la posición del primer hotel que cumple la condición, o `-1` si no hay ninguno.
- **`removeAll { ... }`**: borra todos los hoteles que cumplen la condición.

> **¿Una `MutableList` no daba problemas?** La tabla del apartado 3.7 avisa de que modificar una `MutableList` no actualiza la pantalla. Aquí no ocurre porque la lista mutable es privada del repositorio: la pantalla nunca la ve. Después de cada operación, el ViewModel pide una copia nueva con `getHotels()` (apartado 6.4).
>
> Cuando en versiones posteriores los datos pasen a Room o a una API, solo cambiará el interior del repositorio. El ViewModel y las pantallas seguirán llamando a las mismas funciones.

### 6.3 El estado de la interfaz

El estado de la interfaz (`UiState`) es una `data class` que reúne todo lo que la pantalla necesita para dibujarse en un momento dado. Conociendo el valor del `UiState` se sabe exactamente qué debe verse en pantalla.

Para la lista y el detalle del apartado 5 bastan dos datos: los hoteles y cuál está seleccionado. Crea el archivo `HospedajeUiState.kt`:

```kotlin
data class HospedajeUiState(
    val hotelList: List<Hotel> = emptyList(),
    val selectedHotelId: String? = null
)
```

- **`hotelList`**: los hoteles que se muestran en la lista.
- **`selectedHotelId`**: el `id` del hotel cuyo detalle se ve, o `null` si se ve la lista. Es la misma variable `idSeleccionado` del apartado 5, que ahora vive aquí.
- **Valores por defecto** (`= emptyList()`, `= null`): permiten crear el estado inicial con solo escribir `HospedajeUiState()`.

Así cambia el valor del `UiState` mientras se usa la aplicación, y lo que se ve en cada momento. En rojo, los datos que cambian en cada paso:

![Tres valores sucesivos del UiState y la pantalla que se ve con cada uno: lista con 4 hoteles, detalle de Hotel Peñalta y lista con 3 hoteles](./assets/img/uistate_pantallas.svg)

1. **Al abrir la app**: `selectedHotelId` es `null`, así que se ve la lista con los 4 hoteles.
2. **Al pulsar «Hotel Peñalta»**: `selectedHotelId` pasa a valer su `id`, así que se ve su detalle. La lista no ha cambiado.
3. **Al pulsar «Borrar»**: la lista pasa a tener 3 hoteles y `selectedHotelId` vuelve a `null`, así que se ve la lista sin Hotel Peñalta.

La pantalla no decide nada por su cuenta: solo dibuja lo que dice el `UiState`.

> **¿Por qué una sola `data class` y no varias variables sueltas?** Porque así todo el estado está en un único sitio (*fuente única de verdad*). Nunca puede pasar que una variable diga una cosa y otra variable diga lo contrario.

Más adelante, en los apartados 7 y 8, se añadirán al `UiState` más datos: qué diálogo está abierto o qué mensaje hay que mostrar. El estado completo de la unidad está en el apartado 6.6.

### 6.4 El ViewModel

El ViewModel es la clase que guarda el estado de la pantalla y tiene una función por cada acción del usuario. Además, Android lo conserva al girar el móvil, que es lo que soluciona el problema 1.

![Al girar el móvil, Android destruye la Activity y crea otra: el estado guardado con remember se pierde, pero viewModel() devuelve el mismo ViewModel con el estado conservado](./assets/img/viewmodel_giro.svg)

Al girar el móvil, Android destruye la `Activity` y crea una nueva. Todo lo guardado con `remember` dentro de los composables se pierde, y la lista vuelve a tener 4 hoteles. El ViewModel, en cambio, no se destruye: la nueva `Activity` llama a `viewModel()` y recibe el mismo objeto, con el estado que tenía.

Se construye en cuatro pasos. Crea el archivo `HospedajeViewModel.kt`.

#### Paso 1. La clase y el estado

```kotlin
class HospedajeViewModel : ViewModel() {

    var uiState by mutableStateOf(HospedajeUiState())
        private set

}
```

- **`: ViewModel()`**: la clase hereda de `ViewModel`. Eso es lo que hace que Android la conserve al girar la pantalla.
- **`var uiState by mutableStateOf(HospedajeUiState())`**: es el mismo `mutableStateOf` que usas en los composables (apartado 14.1 de la UD2), pero ahora dentro del ViewModel. Guarda el estado actual y, cada vez que cambia, Compose redibuja las pantallas que lo leen. Empieza con el estado por defecto (lista vacía, nada seleccionado).
- **Sin `remember`**: `remember` solo se usa dentro de funciones `@Composable`. Aquí no hace falta, porque es el propio ViewModel el que conserva el valor.
- **`private set`**: la propiedad se puede leer desde fuera, pero solo se puede cambiar desde dentro del ViewModel.

| | Desde el ViewModel | Desde una pantalla |
|---|---|---|
| Leer `uiState` | Sí | Sí |
| Cambiar `uiState` | Sí | No: el código no compila |

Así se cumple la regla del apartado 6.1: las pantallas no pueden modificar el estado; tienen que llamar a una función del ViewModel.

#### Paso 2. Cargar los datos al empezar

```kotlin
    init {
        uiState = uiState.copy(hotelList = HotelRepository.getHotels())
    }
```

- **`init { ... }`**: se ejecuta una sola vez, al crear el ViewModel.
- **`uiState = uiState.copy(...)`** es la forma de cambiar el estado. Léelo así: *«toma el estado actual, haz una copia cambiando solo `hotelList`, y guarda esa copia como nuevo estado»*.

```text
Antes:   HospedajeUiState(hotelList = [],           selectedHotelId = null)
Después: HospedajeUiState(hotelList = [4 hoteles],  selectedHotelId = null)
```

> **¿Por qué `copy` y no `uiState.hotelList = ...`?** Porque las propiedades del `UiState` son `val` y no se pueden cambiar. Además, Compose solo redibuja cuando se asigna un valor nuevo a `uiState`; si se pudiera modificar el objeto por dentro, Compose no se enteraría. Es la misma idea del apartado 3.2.

#### Paso 3. Eventos que solo cambian el estado: la navegación

Lo que en el apartado 5 hacían las lambdas de `AppPantallas` (`idSeleccionado = hotel.id`, `idSeleccionado = null`) pasa a ser una función del ViewModel:

```kotlin
    fun onHotelClicked(hotel: Hotel) {
        uiState = uiState.copy(selectedHotelId = hotel.id)
    }

    fun onBackToList() {
        uiState = uiState.copy(selectedHotelId = null)
    }
```

Los nombres empiezan por `on` y describen lo que ha hecho el usuario («ha pulsado un hotel», «quiere volver»), igual que los eventos del apartado 14.2 de la UD2.

#### Paso 4. Eventos que cambian los datos: borrar

Cuando un evento cambia los datos, se sigue siempre el mismo patrón de dos pasos:

1. Pedir la operación al repositorio.
2. **Recargar** la lista del repositorio en el estado.

```kotlin
    fun onDeleteHotel(hotel: Hotel) {
        HotelRepository.deleteHotel(hotel.id)        // 1. el repositorio borra
        uiState = uiState.copy(                      // 2. el estado se actualiza
            hotelList = HotelRepository.getHotels(),
            selectedHotelId = null                   //    y se vuelve a la lista
        )
    }
```

Si te olvidas del paso 2, el hotel se borra del repositorio, pero la pantalla no se entera porque el estado no ha cambiado.

#### El ViewModel completo de este ejemplo

```kotlin
package com.ejemplo.hospedajes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HospedajeViewModel : ViewModel() {

    var uiState by mutableStateOf(HospedajeUiState())
        private set

    init {
        uiState = uiState.copy(hotelList = HotelRepository.getHotels())
    }

    fun onHotelClicked(hotel: Hotel) {
        uiState = uiState.copy(selectedHotelId = hotel.id)
    }

    fun onBackToList() {
        uiState = uiState.copy(selectedHotelId = null)
    }

    fun onDeleteHotel(hotel: Hotel) {
        HotelRepository.deleteHotel(hotel.id)
        uiState = uiState.copy(
            hotelList = HotelRepository.getHotels(),
            selectedHotelId = null
        )
    }
}
```

Fíjate en que el ViewModel no tiene nada de Compose: ni `@Composable`, ni `Text`, ni `Modifier`. No dibuja; solo guarda el estado y decide qué hacer.

### 6.5 Leer el estado desde la pantalla

Falta la última pieza: que las pantallas lean el estado del ViewModel y se redibujen cuando cambie.

Como `uiState` se creó con `mutableStateOf`, ya es estado de Compose. Basta con leerlo:

```kotlin
val uiState = viewModel.uiState
```

Cada vez que el ViewModel asigna un nuevo valor a `uiState` (`uiState = uiState.copy(...)`), Compose redibuja los composables que lo han leído, igual que ocurre con cualquier `mutableStateOf` (apartado 14.1 de la UD2).

#### `AppPantallas` con ViewModel

Este es el `AppPantallas.kt` del apartado 5, ahora conectado al ViewModel:

```kotlin
package com.ejemplo.hospedajes

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppPantallas(
    viewModel: HospedajeViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    val hotelSeleccionado = uiState.hotelList.find { it.id == uiState.selectedHotelId }

    BackHandler(enabled = hotelSeleccionado != null) {
        viewModel.onBackToList()
    }

    Box(modifier = Modifier.safeDrawingPadding()) {
        if (hotelSeleccionado == null) {
            PantallaPrincipal(
                hoteles = uiState.hotelList,
                onHotelClick = { hotel -> viewModel.onHotelClicked(hotel) }
            )
        } else {
            PantallaDetalle(
                hotel = hotelSeleccionado,
                onVolver = { viewModel.onBackToList() },
                onBorrar = { viewModel.onDeleteHotel(hotelSeleccionado) }
            )
        }
    }
}
```

`PantallaDetalle` recibe un evento nuevo, `onBorrar: () -> Unit`, que llama desde un botón «Borrar». `PantallaPrincipal` no cambia nada.

- **`viewModel()`** (de la dependencia `lifecycle-viewmodel-compose`, apartado 1): obtiene el ViewModel. La primera vez lo crea; después, incluso tras girar el móvil, devuelve el mismo. Por eso no se escribe `HospedajeViewModel()`, que crearía uno nuevo cada vez.
- **Las pantallas no reciben el ViewModel**: siguen recibiendo datos y lambdas, como en el apartado 5. Solo `AppPantallas` habla con el ViewModel.

Compara las dos versiones:

| | Apartado 5 (solo estado) | Apartado 6 (MVVM) |
|---|---|---|
| Dónde están los hoteles | `hotelesDeEjemplo` | `HotelRepository` |
| Dónde se guarda el seleccionado | `rememberSaveable` en `AppPantallas` | `selectedHotelId` en el `UiState` |
| Quién decide qué hacer al pulsar | Las lambdas de `AppPantallas` | Las funciones del ViewModel |
| Al girar el móvil tras borrar | El hotel reaparece | El hotel sigue borrado |

#### Recorrido completo: borrar un hotel

Sigue el viaje de un evento por las tres capas:

![Diagrama de secuencia al borrar un hotel: la vista envía el evento al ViewModel, el ViewModel pide el borrado al repositorio, recarga la lista, actualiza uiState y la vista se redibuja](./assets/img/recorrido_borrar.svg)

Los mismos pasos, en detalle:

| Paso | Capa | Qué ocurre |
|---|---|---|
| 1 | Vista | El usuario pulsa «Borrar» en `PantallaDetalle`, que llama a `onBorrar()`. |
| 2 | Vista → ViewModel | `AppPantallas` ejecuta `viewModel.onDeleteHotel(hotel)`. Sube un evento. |
| 3 | ViewModel → Repositorio | `HotelRepository.deleteHotel(hotel.id)`: el repositorio borra el hotel. |
| 4 | ViewModel | `uiState = uiState.copy(...)`: nuevo estado con 3 hoteles y `selectedHotelId = null`. |
| 5 | ViewModel → Vista | Como `uiState` es un `mutableStateOf`, Compose detecta el cambio. Baja el estado. |
| 6 | Vista | Compose redibuja `AppPantallas`: como no hay hotel seleccionado, muestra `PantallaPrincipal` con 3 hoteles. |

En ningún momento la pantalla ha borrado nada: el evento sube, el estado cambia y la pantalla se redibuja con el nuevo estado.

### 6.6 El ViewModel completo de la unidad

En los apartados 7 y 8 se añaden diálogos para crear, editar y confirmar el borrado, y mensajes con `Snackbar`. Todo eso también es estado, así que el `UiState` y el ViewModel crecen. Aquí tienes la versión final como referencia; cada parte nueva se explica en el apartado indicado.

```kotlin
data class HospedajeUiState(
    val hotelList: List<Hotel> = emptyList(),
    val selectedHotelId: String? = null,
    val hotelToEdit: Hotel? = null,        // 7.3: hotel en edición
    val hotelToDelete: Hotel? = null,      // 7.2: hotel pendiente de confirmar borrado
    val isAddDialogOpen: Boolean = false,  // 7.3: diálogo de alta abierto
    val userMessage: String? = null        // 8.2: mensaje para el Snackbar
)
```

| Propiedad | Significado | Apartado |
|---|---|---|
| `hotelList` | Hoteles que se muestran | 6.3 |
| `selectedHotelId` | Hotel cuyo detalle se ve; `null` = se ve la lista | 6.3 |
| `hotelToEdit` | Hotel que se está editando; `null` si no hay ninguna edición en curso | 7.3 |
| `hotelToDelete` | Hotel pendiente de confirmar su borrado; `null` si no hay ninguno | 7.2 |
| `isAddDialogOpen` | `true` mientras el diálogo de alta está abierto | 7.3 |
| `userMessage` | Mensaje pendiente de mostrar en el `Snackbar`; `null` si no hay ninguno | 8.2 |

Fíjate en que los diálogos también se controlan con el estado: abrir un diálogo es cambiar un dato del `UiState`.

```kotlin
private const val DEFAULT_IMAGE = "https://picsum.photos/id/1043/300/300"

class HospedajeViewModel : ViewModel() {

    var uiState by mutableStateOf(HospedajeUiState())
        private set

    init {
        uiState = uiState.copy(hotelList = HotelRepository.getHotels())
    }

    // ---------- Navegación (6.4) ----------

    fun onHotelClicked(hotel: Hotel) {
        uiState = uiState.copy(selectedHotelId = hotel.id)
    }

    fun onBackToList() {
        uiState = uiState.copy(selectedHotelId = null)
    }

    // ---------- Alta (7.3) ----------

    fun onAddHotelClicked() {
        uiState = uiState.copy(isAddDialogOpen = true)
    }

    fun onDismissAddDialog() {
        uiState = uiState.copy(isAddDialogOpen = false)
    }

    fun onInsertHotel(name: String, city: String, province: String, phone: String, image: String) {
        val newHotel = Hotel(
            name = name,
            city = city,
            province = province,
            phone = phone,
            image = image.ifBlank { DEFAULT_IMAGE }
        )
        HotelRepository.addHotel(newHotel)
        uiState = uiState.copy(
            hotelList = HotelRepository.getHotels(),
            isAddDialogOpen = false,
            userMessage = "Hospedaje '${newHotel.name}' añadido correctamente"
        )
    }

    // ---------- Edición (7.3) ----------

    fun onEditHotelClicked(hotel: Hotel) {
        uiState = uiState.copy(hotelToEdit = hotel)
    }

    fun onDismissEditDialog() {
        uiState = uiState.copy(hotelToEdit = null)
    }

    fun onUpdateHotel(id: String, name: String, city: String, province: String, phone: String, image: String) {
        val existingHotel = HotelRepository.getHotelById(id) ?: return
        val updatedHotel = existingHotel.copy(
            name = name,
            city = city,
            province = province,
            phone = phone,
            image = image.ifBlank { DEFAULT_IMAGE }
        )
        HotelRepository.updateHotel(updatedHotel)
        uiState = uiState.copy(
            hotelList = HotelRepository.getHotels(),
            hotelToEdit = null,
            userMessage = "Hospedaje actualizado"
        )
    }

    // ---------- Borrado con confirmación (7.2) ----------

    fun onDeleteHotelClicked(hotel: Hotel) {
        uiState = uiState.copy(hotelToDelete = hotel)
    }

    fun onDismissDeleteDialog() {
        uiState = uiState.copy(hotelToDelete = null)
    }

    fun onConfirmDeleteHotel() {
        val target = uiState.hotelToDelete ?: return
        HotelRepository.deleteHotel(target.id)
        uiState = uiState.copy(
            hotelList = HotelRepository.getHotels(),
            hotelToDelete = null,
            selectedHotelId = null,
            userMessage = "Hospedaje '${target.name}' borrado"
        )
    }

    // ---------- Mensajes (8.2) ----------

    fun onUserMessageShown() {
        uiState = uiState.copy(userMessage = null)
    }
}
```

Todas las funciones siguen los dos patrones del apartado 6.4: o solo cambian el estado (abrir y cerrar diálogos, navegar) o piden algo al repositorio y recargan la lista (insertar, actualizar, borrar). Novedades respecto al apartado 6.4:

- **El borrado ahora tiene dos pasos**: `onDeleteHotelClicked` solo apunta qué hotel se quiere borrar (y así se abre el diálogo de confirmación), y `onConfirmDeleteHotel` lo borra de verdad. Sustituyen a `onDeleteHotel` del apartado 6.4.
- **Editar**: `existingHotel.copy(...)` crea un hotel nuevo con el mismo `id` y los datos modificados; `updateHotel` sustituye al antiguo. Si la URL de la imagen se deja vacía, se usa la imagen por defecto, igual que al insertar.
- **`?: return`**: si lo de la izquierda es `null` (el hotel no existe o no hay ninguno pendiente de borrar), la función termina sin hacer nada.
- **`uiState.hotelToDelete`**: dentro del ViewModel se puede leer el estado directamente; aquí, para saber qué hotel hay que borrar.

> **Resumen mental.** El repositorio guarda los datos. El ViewModel guarda el estado (`UiState`) y tiene una función por cada evento. Las pantallas dibujan el estado y avisan con lambdas. El evento sube → el ViewModel actualiza el estado (y el repositorio si hace falta) → el estado baja → Compose redibuja.

---

## 7. Diálogos declarativos

Al final del apartado 6, la aplicación ya tiene lista, detalle y borrado, pero le faltan dos cosas:

1. Al pulsar «Borrar», el hotel desaparece al instante. Un toque por error borra datos sin posibilidad de arrepentirse.
2. No se pueden añadir ni editar hoteles: no hay ningún formulario.

Las dos se resuelven con diálogos: una ventana de confirmación antes de borrar (7.2) y una ventana con un formulario para añadir y editar (7.3 y 7.4).

### 7.1 Cómo se muestra un diálogo

En el apartado 2.3 viste que un `AlertDialog` no aparece por escribirlo: se dibuja solo si una variable de estado lo indica.

```kotlin
var mostrarDialogo by remember { mutableStateOf(false) }

if (mostrarDialogo) {
    AlertDialog(/* ... */)
}
```

Si la condición es verdadera, el diálogo se dibuja; si pasa a falsa, desaparece. No hay que "cerrarlo" a mano: se cambia el estado.

Con la arquitectura MVVM, esa variable deja de estar en la pantalla y pasa al `UiState` del ViewModel, como el resto del estado. Para el diálogo de confirmación de borrado, el dato es `hotelToDelete`:

![Con hotelToDelete a null no se dibuja el diálogo; al pulsar Borrar, el ViewModel guarda el hotel en hotelToDelete y se dibuja ConfirmDeleteDialog encima del detalle; al cancelar vuelve a null](./assets/img/dialogo_estado.svg)

- Mientras `hotelToDelete` vale `null`, no hay diálogo.
- Al pulsar «Borrar», el ViewModel guarda en `hotelToDelete` el hotel que se quiere borrar, y el diálogo aparece encima del detalle.
- Al cancelar, `hotelToDelete` vuelve a `null` y el diálogo desaparece sin borrar nada.

Fíjate en que `hotelToDelete` no es un `Boolean`, sino un `Hotel?`. Así sirve para dos cosas a la vez: indica si el diálogo está abierto (no es `null`) y qué hotel se va a borrar.

En esta unidad hay tres diálogos, y cada uno depende de un dato del `UiState`:

| Diálogo | Dato del `UiState` | Se dibuja cuando… | Apartado |
|---|---|---|---|
| Confirmar borrado | `hotelToDelete: Hotel?` | no es `null` | 7.2 |
| Alta de hotel | `isAddDialogOpen: Boolean` | es `true` | 7.3 |
| Edición de hotel | `hotelToEdit: Hotel?` | no es `null` | 7.3 |

### 7.2 Diálogo de confirmación de borrado

Se construye en cuatro pasos, partiendo del código del apartado 6.5.

#### Paso 1. Añadir el dato al `UiState`

```kotlin
data class HospedajeUiState(
    val hotelList: List<Hotel> = emptyList(),
    val selectedHotelId: String? = null,
    val hotelToDelete: Hotel? = null      // NUEVO: hotel pendiente de confirmar
)
```

#### Paso 2. Cambiar el borrado en el ViewModel

La función `onDeleteHotel` del apartado 6.4 borraba directamente. Ahora el borrado se divide en tres funciones:

```kotlin
// El usuario pulsa «Borrar»: solo se apunta qué hotel se quiere borrar
fun onDeleteHotelClicked(hotel: Hotel) {
    uiState = uiState.copy(hotelToDelete = hotel)
}

// El usuario pulsa «Cancelar» o toca fuera del diálogo
fun onDismissDeleteDialog() {
    uiState = uiState.copy(hotelToDelete = null)
}

// El usuario pulsa «Eliminar»: ahora sí se borra
fun onConfirmDeleteHotel() {
    val target = uiState.hotelToDelete ?: return
    HotelRepository.deleteHotel(target.id)
    uiState = uiState.copy(
        hotelList = HotelRepository.getHotels(),
        hotelToDelete = null,
        selectedHotelId = null
    )
}
```

- `onDeleteHotelClicked` no toca el repositorio: solo abre el diálogo.
- `onConfirmDeleteHotel` sigue el patrón del apartado 6.4: borra en el repositorio y recarga la lista. Además, cierra el diálogo (`hotelToDelete = null`) y vuelve a la lista (`selectedHotelId = null`).
- `?: return`: si no hay ningún hotel pendiente de borrar, la función termina sin hacer nada.

#### Paso 3. Crear el diálogo

Crea el archivo `Dialogos.kt`, donde irán todos los diálogos de la aplicación:

```kotlin
@Composable
fun ConfirmDeleteDialog(
    hotel: Hotel,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar hotel") },
        text = { Text("¿Seguro que quieres eliminar ${hotel.name}?") },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
```

- El diálogo sigue la misma regla que las pantallas: recibe datos (`hotel`) y avisa con eventos (`onConfirm`, `onDismiss`). No borra nada por su cuenta.
- `onDismissRequest` se ejecuta cuando el usuario toca fuera del diálogo o pulsa Atrás. Aquí hace lo mismo que «Cancelar».
- El botón «Eliminar» usa el color `error` del tema para indicar que la acción no se puede deshacer.

#### Paso 4. Conectarlo en `AppPantallas`

En `AppPantallas` cambian dos cosas: el evento `onBorrar` del detalle ya no borra, sino que abre el diálogo, y se añade el diálogo después del `if/else` de las pantallas:

```kotlin
    Box(modifier = Modifier.safeDrawingPadding()) {
        if (hotelSeleccionado == null) {
            PantallaPrincipal(/* igual que en 6.5 */)
        } else {
            PantallaDetalle(
                hotel = hotelSeleccionado,
                onVolver = { viewModel.onBackToList() },
                onBorrar = { viewModel.onDeleteHotelClicked(hotelSeleccionado) }   // CAMBIA
            )
        }
    }

    // NUEVO: el diálogo se dibuja encima de la pantalla que esté visible
    uiState.hotelToDelete?.let { hotel ->
        ConfirmDeleteDialog(
            hotel = hotel,
            onDismiss = { viewModel.onDismissDeleteDialog() },
            onConfirm = { viewModel.onConfirmDeleteHotel() }
        )
    }
```

- `uiState.hotelToDelete?.let { hotel -> ... }` ejecuta el bloque solo si `hotelToDelete` no es `null`, y dentro `hotel` ya es un `Hotel` (no un `Hotel?`). Es la forma habitual de mostrar un diálogo que depende de un dato opcional.
- El diálogo se coloca en `AppPantallas` y no dentro de `PantallaDetalle` porque es `AppPantallas` quien habla con el ViewModel. `PantallaDetalle` sigue sin saber nada de diálogos: solo avisa de que se ha pulsado «Borrar».

#### Recorrido: borrar un hotel con confirmación

| Paso | Qué ocurre | Estado después |
|---|---|---|
| 1 | En el detalle, el usuario pulsa «Borrar». `AppPantallas` llama a `viewModel.onDeleteHotelClicked(hotel)`. | `hotelToDelete = hotel` |
| 2 | Compose redibuja: como `hotelToDelete` no es `null`, aparece `ConfirmDeleteDialog`. Todavía no se ha borrado nada. | — |
| 3a | Si pulsa «Cancelar»: `onDismissDeleteDialog()`. El diálogo desaparece y el hotel sigue ahí. | `hotelToDelete = null` |
| 3b | Si pulsa «Eliminar»: `onConfirmDeleteHotel()`. El repositorio borra el hotel y se recarga la lista. | `hotelToDelete = null`, `selectedHotelId = null` |
| 4 | Compose redibuja: el diálogo desaparece y se ve la lista sin el hotel borrado. | — |

### 7.3 Diálogo de alta y edición

Para añadir y editar se usa un único diálogo con un formulario. La diferencia entre ambas operaciones es que, al editar, los campos aparecen rellenos con los datos del hotel. En este apartado se construye el formulario sin validar; la validación se añade en el 7.4.

#### Paso 1. Añadir los datos al `UiState`

```kotlin
data class HospedajeUiState(
    val hotelList: List<Hotel> = emptyList(),
    val selectedHotelId: String? = null,
    val hotelToDelete: Hotel? = null,
    val isAddDialogOpen: Boolean = false,  // NUEVO: diálogo de alta abierto
    val hotelToEdit: Hotel? = null         // NUEVO: hotel que se está editando
)
```

- Para el alta basta un `Boolean`: el diálogo empieza vacío y no necesita saber nada más.
- Para la edición se usa un `Hotel?`, igual que en el borrado: indica si el diálogo está abierto y qué hotel se edita, para rellenar los campos con sus datos.

#### Paso 2. Añadir las funciones al ViewModel

```kotlin
private const val DEFAULT_IMAGE = "https://picsum.photos/id/1043/300/300"

// ---------- Alta ----------

fun onAddHotelClicked() {
    uiState = uiState.copy(isAddDialogOpen = true)
}

fun onDismissAddDialog() {
    uiState = uiState.copy(isAddDialogOpen = false)
}

fun onInsertHotel(name: String, city: String, province: String, phone: String, image: String) {
    val newHotel = Hotel(
        name = name,
        city = city,
        province = province,
        phone = phone,
        image = image.ifBlank { DEFAULT_IMAGE }
    )
    HotelRepository.addHotel(newHotel)
    uiState = uiState.copy(
        hotelList = HotelRepository.getHotels(),
        isAddDialogOpen = false
    )
}

// ---------- Edición ----------

fun onEditHotelClicked(hotel: Hotel) {
    uiState = uiState.copy(hotelToEdit = hotel)
}

fun onDismissEditDialog() {
    uiState = uiState.copy(hotelToEdit = null)
}

fun onUpdateHotel(id: String, name: String, city: String, province: String, phone: String, image: String) {
    val existingHotel = HotelRepository.getHotelById(id) ?: return
    val updatedHotel = existingHotel.copy(
        name = name,
        city = city,
        province = province,
        phone = phone,
        image = image.ifBlank { DEFAULT_IMAGE }
    )
    HotelRepository.updateHotel(updatedHotel)
    uiState = uiState.copy(
        hotelList = HotelRepository.getHotels(),
        hotelToEdit = null
    )
}
```

Son los dos patrones del apartado 6.4:

| Función | Patrón | Qué hace |
|---|---|---|
| `onAddHotelClicked`, `onEditHotelClicked` | Solo cambia el estado | Abre el diálogo |
| `onDismissAddDialog`, `onDismissEditDialog` | Solo cambia el estado | Cierra el diálogo sin guardar |
| `onInsertHotel`, `onUpdateHotel` | Repositorio + recargar | Guarda los datos, recarga la lista y cierra el diálogo |

- `image.ifBlank { DEFAULT_IMAGE }`: si la URL de la imagen se deja vacía, se usa una imagen por defecto.
- Al editar, `existingHotel.copy(...)` crea un hotel con el mismo `id` y los datos nuevos, y `updateHotel` sustituye al antiguo. Como el detalle busca el hotel por su `id` (apartado 6.3), muestra los datos nuevos en cuanto se guarda.

#### Paso 3. Crear el diálogo con el formulario

En `Dialogos.kt`:

```kotlin
@Composable
fun AddEditHotelDialog(
    hotelToEdit: Hotel? = null,
    onDismiss: () -> Unit,
    onConfirm: (name: String, city: String, province: String, phone: String, image: String) -> Unit
) {
    // Estado local del formulario: un valor por campo
    var name by remember { mutableStateOf(hotelToEdit?.name ?: "") }
    var city by remember { mutableStateOf(hotelToEdit?.city ?: "") }
    var province by remember { mutableStateOf(hotelToEdit?.province ?: "") }
    var phone by remember { mutableStateOf(hotelToEdit?.phone ?: "") }
    var image by remember { mutableStateOf(hotelToEdit?.image ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (hotelToEdit == null) "Nuevo hospedaje" else "Editar hospedaje")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") }, singleLine = true)
                OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("Ciudad") }, singleLine = true)
                OutlinedTextField(value = province, onValueChange = { province = it }, label = { Text("Provincia") }, singleLine = true)
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Teléfono") }, singleLine = true)
                OutlinedTextField(value = image, onValueChange = { image = it }, label = { Text("URL de la imagen") }, singleLine = true)
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(name, city, province, phone, image) }) {
                Text(if (hotelToEdit == null) "Guardar" else "Actualizar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
```

- Un solo diálogo para las dos operaciones: si `hotelToEdit` es `null`, es un alta (título «Nuevo hospedaje», campos vacíos); si recibe un hotel, es una edición (título «Editar hospedaje», campos rellenos).
- `hotelToEdit?.name ?: ""` significa: el nombre del hotel si existe; si `hotelToEdit` es `null`, un texto vacío. El operador `?:` (*elvis*) da un valor por defecto.
- El estado del formulario se guarda con `remember` dentro del diálogo, no en el ViewModel. Es un estado temporal: lo que el usuario va escribiendo solo importa mientras el diálogo está abierto. El ViewModel no recibe nada hasta que se pulsa «Guardar».
- Al pulsar «Guardar», el diálogo no guarda el hotel: llama a `onConfirm(...)` con los textos escritos, y es `AppPantallas` quien decide qué hacer con ellos.
- Al pulsar «Cancelar», el diálogo se cierra y lo escrito se pierde, porque el estado local desaparece con el diálogo. No se aplica ningún cambio.

#### Paso 4. Conectarlo en `AppPantallas` y en las pantallas

Las pantallas necesitan un botón para cada operación. Añade un evento nuevo a cada una:

| Pantalla | Evento nuevo | Botón |
|---|---|---|
| `PantallaPrincipal` | `onAddClick: () -> Unit` | «Añadir hotel», debajo del título |
| `PantallaDetalle` | `onEditar: () -> Unit` | «Editar», junto a «Borrar» |

El botón «Añadir hotel» es provisional: en el apartado 8.1 se sustituye por un botón flotante.

En `AppPantallas` se conectan los eventos y se añaden los dos usos del diálogo, junto al de borrado:

```kotlin
        if (hotelSeleccionado == null) {
            PantallaPrincipal(
                hoteles = uiState.hotelList,
                onHotelClick = { hotel -> viewModel.onHotelClicked(hotel) },
                onAddClick = { viewModel.onAddHotelClicked() }                    // NUEVO
            )
        } else {
            PantallaDetalle(
                hotel = hotelSeleccionado,
                onVolver = { viewModel.onBackToList() },
                onEditar = { viewModel.onEditHotelClicked(hotelSeleccionado) },   // NUEVO
                onBorrar = { viewModel.onDeleteHotelClicked(hotelSeleccionado) }
            )
        }

    // ... (diálogo de borrado del apartado 7.2)

    // NUEVO: diálogo de alta
    if (uiState.isAddDialogOpen) {
        AddEditHotelDialog(
            onDismiss = { viewModel.onDismissAddDialog() },
            onConfirm = { name, city, province, phone, image ->
                viewModel.onInsertHotel(name, city, province, phone, image)
            }
        )
    }

    // NUEVO: diálogo de edición (el mismo composable, con el hotel a editar)
    uiState.hotelToEdit?.let { hotel ->
        AddEditHotelDialog(
            hotelToEdit = hotel,
            onDismiss = { viewModel.onDismissEditDialog() },
            onConfirm = { name, city, province, phone, image ->
                viewModel.onUpdateHotel(hotel.id, name, city, province, phone, image)
            }
        )
    }
```

`onConfirm` recibe cinco parámetros. La lambda `{ name, city, province, phone, image -> ... }` les da nombre y los pasa al ViewModel. En la edición se añade además `hotel.id`, para que el ViewModel sepa qué hotel sustituir.

### 7.4 Validar el formulario

Con el diálogo del apartado 7.3 se puede guardar un hotel sin nombre, o con un teléfono como «abc». Hay que comprobar los datos antes de guardar y, si algo está mal, explicar qué falla junto al campo correspondiente (apartado 2.4).

Las reglas del ejemplo son:

| Campo | Regla | Mensaje si no se cumple |
|---|---|---|
| Nombre | Obligatorio | «El nombre es obligatorio» |
| Ciudad | Obligatoria | «La ciudad es obligatoria» |
| Provincia | Obligatoria | «La provincia es obligatoria» |
| Teléfono | Opcional; si se escribe, solo números y espacios, y 9 cifras | «Solo se admiten números y espacios» o «El teléfono debe tener 9 cifras» |
| URL de la imagen | Opcional | — |

#### Paso 1. Calcular el error de cada campo

Dentro de `AddEditHotelDialog`, después de los `remember` de los campos:

```kotlin
    val nameError = if (name.isBlank()) "El nombre es obligatorio" else null
    val cityError = if (city.isBlank()) "La ciudad es obligatoria" else null
    val provinceError = if (province.isBlank()) "La provincia es obligatoria" else null
    val phoneError = when {
        phone.isBlank() -> null // el teléfono es opcional
        phone.any { !it.isDigit() && it != ' ' } -> "Solo se admiten números y espacios"
        phone.count { it.isDigit() } != 9 -> "El teléfono debe tener 9 cifras"
        else -> null
    }

    val isFormValid = nameError == null && cityError == null &&
        provinceError == null && phoneError == null
```

- Cada variable `...Error` contiene el mensaje de error del campo, o `null` si el dato es correcto.
- `isBlank()` es `true` si el texto está vacío o solo tiene espacios.
- `when` comprueba las condiciones en orden y se queda con la primera que se cumple.
- `isFormValid` es `true` solo si ningún campo tiene error.

Como estas variables se calculan con los valores actuales de los campos, se recalculan solas cada vez que el usuario escribe.

#### Paso 2. Mostrar el error debajo del campo

`OutlinedTextField` tiene dos parámetros para esto:

- `isError`: si es `true`, el campo se dibuja con el color `error` del tema.
- `supportingText`: un texto que aparece debajo del campo; aquí, el mensaje de error.

Para no repetirlos en cada campo, se crea un composable `FormField` en `Dialogos.kt`:

```kotlin
@Composable
fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        isError = error != null,
        supportingText = if (error != null) {
            { Text(error) }
        } else {
            null
        },
        modifier = Modifier.fillMaxWidth()
    )
}
```

Si `error` es `null`, el campo se ve normal y `supportingText` no ocupa espacio. Si contiene un mensaje, el campo se pinta en rojo y el mensaje aparece debajo.

#### Paso 3. Decidir cuándo mostrar los errores

Si los errores se mostraran desde el principio, el diálogo de alta se abriría con todos los campos en rojo antes de que el usuario escriba nada. Por eso se añade una variable más:

```kotlin
    var showErrors by remember { mutableStateOf(false) }
```

- Al abrir el diálogo vale `false`: no se muestra ningún error.
- Al pulsar «Guardar» con algún error, pasa a `true` y cada campo incorrecto muestra su mensaje.
- A partir de ahí, los mensajes se actualizan mientras el usuario escribe y desaparecen cuando el dato es correcto.

El botón «Guardar» queda así:

```kotlin
            Button(
                onClick = {
                    if (isFormValid) {
                        onConfirm(name.trim(), city.trim(), province.trim(), phone.trim(), image.trim())
                    } else {
                        showErrors = true
                    }
                }
            ) {
                Text(if (hotelToEdit == null) "Guardar" else "Actualizar")
            }
```

- Si el formulario es válido, se llama a `onConfirm`. `trim()` quita los espacios del principio y del final de cada texto.
- Si no lo es, no se guarda nada y se activan los mensajes de error.

Otra opción sería desactivar el botón (`enabled = isFormValid`) mientras haya errores, pero un botón desactivado no explica al usuario qué le falta.

#### El diálogo completo

```kotlin
@Composable
fun AddEditHotelDialog(
    hotelToEdit: Hotel? = null,
    onDismiss: () -> Unit,
    onConfirm: (name: String, city: String, province: String, phone: String, image: String) -> Unit
) {
    var name by remember { mutableStateOf(hotelToEdit?.name ?: "") }
    var city by remember { mutableStateOf(hotelToEdit?.city ?: "") }
    var province by remember { mutableStateOf(hotelToEdit?.province ?: "") }
    var phone by remember { mutableStateOf(hotelToEdit?.phone ?: "") }
    var image by remember { mutableStateOf(hotelToEdit?.image ?: "") }

    var showErrors by remember { mutableStateOf(false) }

    val nameError = if (name.isBlank()) "El nombre es obligatorio" else null
    val cityError = if (city.isBlank()) "La ciudad es obligatoria" else null
    val provinceError = if (province.isBlank()) "La provincia es obligatoria" else null
    val phoneError = when {
        phone.isBlank() -> null
        phone.any { !it.isDigit() && it != ' ' } -> "Solo se admiten números y espacios"
        phone.count { it.isDigit() } != 9 -> "El teléfono debe tener 9 cifras"
        else -> null
    }

    val isFormValid = nameError == null && cityError == null &&
        provinceError == null && phoneError == null

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(if (hotelToEdit == null) "Nuevo hospedaje" else "Editar hospedaje")
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FormField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Nombre *",
                    error = if (showErrors) nameError else null
                )
                FormField(
                    value = city,
                    onValueChange = { city = it },
                    label = "Ciudad *",
                    error = if (showErrors) cityError else null
                )
                FormField(
                    value = province,
                    onValueChange = { province = it },
                    label = "Provincia *",
                    error = if (showErrors) provinceError else null
                )
                FormField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = "Teléfono",
                    error = if (showErrors) phoneError else null
                )
                FormField(
                    value = image,
                    onValueChange = { image = it },
                    label = "URL de la imagen"
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (isFormValid) {
                        onConfirm(name.trim(), city.trim(), province.trim(), phone.trim(), image.trim())
                    } else {
                        showErrors = true
                    }
                }
            ) {
                Text(if (hotelToEdit == null) "Guardar" else "Actualizar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
```

Los campos obligatorios llevan un `*` en la etiqueta para que el usuario sepa cuáles debe rellenar. La validación ocurre entera dentro del diálogo: el ViewModel solo recibe datos que ya son correctos.

---

## 8. Scaffold y Snackbar

La aplicación ya hace el CRUD completo, pero las pantallas todavía son muy básicas:

- No tienen barra superior con el título.
- El botón «Añadir hotel» del apartado 7.3 es provisional.
- Para volver desde el detalle solo hay un botón «Volver» dentro del contenido.
- Después de guardar o borrar, el usuario no recibe ninguna confirmación.

Este apartado lo resuelve con `Scaffold` (8.1) y `Snackbar` (8.2), y termina con el código completo de la aplicación (8.3).

### 8.1 Estructura con Scaffold

`Scaffold` es el composable que organiza una pantalla de Material Design. Tiene un hueco para cada elemento habitual y se encarga de colocarlos sin que se solapen:

| Parámetro | Qué se coloca | En el ejemplo |
|---|---|---|
| `topBar` | Barra superior | `TopAppBar` con el título de la pantalla |
| `floatingActionButton` | Botón flotante, abajo a la derecha | Botón «+» para añadir un hotel |
| `snackbarHost` | Zona donde aparecen los mensajes | Se usa en el apartado 8.2 |
| Bloque final `{ paddingValues -> ... }` | El contenido de la pantalla | La lista o el detalle |

```kotlin
Scaffold(
    topBar = { TopAppBar(title = { Text("Mis hospedajes") }) },
    floatingActionButton = {
        FloatingActionButton(onClick = onAddClick) {
            Icon(Icons.Default.Add, contentDescription = "Agregar hospedaje")
        }
    }
) { paddingValues ->
    // contenido de la pantalla, con Modifier.padding(paddingValues)
}
```

- `paddingValues` es el espacio que ocupan la barra superior y las barras del sistema. Hay que aplicarlo al contenido con `Modifier.padding(paddingValues)`; si no, la barra superior tapa la parte de arriba de la lista.
- Como `Scaffold` ya respeta las barras del sistema, el `Box(Modifier.safeDrawingPadding())` que había en `AppPantallas` (apartado 5.5) deja de ser necesario.
- `TopAppBar` es un componente experimental de Material 3. La función que lo usa debe llevar `@OptIn(ExperimentalMaterial3Api::class)`; si falta, Android Studio lo marca en rojo y lo añade con *Alt + Enter*.
- El `FloatingActionButton` se reserva para la acción principal de la pantalla. En la lista es «Añadir», así que sustituye al botón provisional del apartado 7.3.

Cada pantalla tiene su propio `Scaffold`, con la barra que le corresponde:

| Pantalla | `topBar` | `floatingActionButton` |
|---|---|---|
| `PantallaPrincipal` | Título «Mis hospedajes» | «+» para añadir |
| `PantallaDetalle` | Nombre del hotel y flecha para volver | No tiene |

En la barra del detalle, la flecha de volver va en el parámetro `navigationIcon` de `TopAppBar`, que es el hueco para el icono de la izquierda:

```kotlin
TopAppBar(
    title = { Text(hotel.name) },
    navigationIcon = {
        IconButton(onClick = onVolver) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver a la lista"
            )
        }
    }
)
```

`Icons.AutoMirrored.Filled.ArrowBack` es la flecha hacia la izquierda. *AutoMirrored* significa que se invierte automáticamente en idiomas que se escriben de derecha a izquierda. Con esta flecha, el botón «Volver» del contenido deja de ser necesario.

El código completo de las dos pantallas está en el apartado 8.3.

### 8.2 Mensajes con Snackbar

Después de guardar o borrar, conviene confirmar al usuario que la acción se ha hecho, con un mensaje breve como «Hospedaje borrado». Para eso se usa un `Snackbar` (apartado 2.2).

El mensaje es estado de la pantalla, así que lo decide el ViewModel. Se añade en cuatro pasos.

#### Paso 1. Añadir el mensaje al `UiState`

```kotlin
    val userMessage: String? = null   // NUEVO: mensaje pendiente de mostrar
```

Vale `null` mientras no hay ningún mensaje que mostrar.

#### Paso 2. Escribir el mensaje desde el ViewModel

Las funciones que guardan o borran añaden un mensaje al estado. Por ejemplo, en `onConfirmDeleteHotel`:

```kotlin
    uiState = uiState.copy(
        hotelList = HotelRepository.getHotels(),
        hotelToDelete = null,
        selectedHotelId = null,
        userMessage = "Hospedaje '${target.name}' borrado"   // NUEVO
    )
```

Lo mismo en `onInsertHotel` («Hospedaje añadido correctamente») y en `onUpdateHotel` («Hospedaje actualizado»). Además, se añade una función para borrar el mensaje una vez mostrado:

```kotlin
fun onUserMessageShown() {
    uiState = uiState.copy(userMessage = null)
}
```

#### Paso 3. Mostrar el mensaje desde `AppPantallas`

```kotlin
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onUserMessageShown()
        }
    }
```

- `SnackbarHostState` es el objeto que controla qué mensaje se está mostrando. Se guarda con `remember`.
- `showSnackbar(message)` muestra el mensaje y espera a que desaparezca. Es una función *suspend* (tarda un tiempo en terminar), por eso no se puede llamar directamente desde el código de un composable: necesita un efecto.
- `LaunchedEffect(uiState.userMessage)` ejecuta su bloque al aparecer y cada vez que cambia el valor indicado entre paréntesis. Cuando el ViewModel escribe un mensaje nuevo, el bloque se ejecuta y lo muestra.
- `onUserMessageShown()` devuelve el mensaje a `null`. Sin este paso, el mismo mensaje volvería a mostrarse, por ejemplo, al girar el móvil.

El `SnackbarHostState` se crea en `AppPantallas` y no en cada pantalla porque el mensaje debe verse aunque cambie la pantalla. Al borrar un hotel desde el detalle, el mensaje «Hospedaje borrado» aparece cuando ya se está viendo la lista.

#### Paso 4. Pasar el `SnackbarHostState` a las pantallas

Cada pantalla recibe un parámetro más y lo coloca en su `Scaffold`:

```kotlin
@Composable
fun PantallaPrincipal(
    hoteles: List<Hotel>,
    snackbarHostState: SnackbarHostState,   // NUEVO
    onHotelClick: (Hotel) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        // topBar y floatingActionButton como en 8.1
        snackbarHost = { SnackbarHost(snackbarHostState) }   // NUEVO
    ) { paddingValues ->
        // ...
    }
}
```

`SnackbarHost` es el hueco del `Scaffold` donde se dibuja el mensaje, en la parte inferior de la pantalla. `PantallaDetalle` cambia de la misma forma.

#### Recorrido: el mensaje tras borrar un hotel

| Paso | Qué ocurre |
|---|---|
| 1 | El usuario confirma el borrado. `onConfirmDeleteHotel()` borra el hotel y pone `userMessage = "Hospedaje 'Hotel Peñalta' borrado"`. |
| 2 | Compose redibuja `AppPantallas`: se ve la lista, y como `userMessage` ha cambiado, se ejecuta el `LaunchedEffect`. |
| 3 | `showSnackbar(...)` muestra el mensaje en la parte inferior de `PantallaPrincipal`. |
| 4 | Cuando el mensaje desaparece, se llama a `onUserMessageShown()` y `userMessage` vuelve a `null`. |

### 8.3 El código completo: lista y detalle con CRUD

Este es el resultado de juntar los apartados 5 a 8. Es la referencia para la Versión 1.2 del proyecto.

```text
app/src/main/java/com/ejemplo/hospedajes/
├── Hotel.kt                  ← modelo (3.2)
├── HotelRepository.kt        ← repositorio (6.2)
├── HospedajeUiState.kt       ← estado completo (6.6)
├── HospedajeViewModel.kt     ← ViewModel completo (6.6)
├── Dialogos.kt               ← ConfirmDeleteDialog (7.2), FormField y AddEditHotelDialog (7.4)
├── PantallaPrincipal.kt      ← lista, barra superior y botón + (este apartado)
├── PantallaDetalle.kt        ← datos del hotel, flecha de volver, Editar y Eliminar (este apartado)
├── AppPantallas.kt           ← elige la pantalla, muestra los diálogos y el Snackbar (este apartado)
└── MainActivity.kt           ← setContent { AppPantallas() } (5.6)
```

El `UiState` y el ViewModel son los del apartado 6.6, que ya incluyen todo lo añadido en los apartados 7 y 8.

#### `PantallaPrincipal.kt`

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    hoteles: List<Hotel>,
    snackbarHostState: SnackbarHostState,
    onHotelClick: (Hotel) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Mis hospedajes") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Agregar hospedaje")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        if (hoteles.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay hospedajes registrados. Pulsa + para añadir uno.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(hoteles, key = { it.id }) { hotel ->
                    HotelCard(hotel = hotel, onClick = { onHotelClick(hotel) })
                }
            }
        }
    }
}

@Composable
fun HotelCard(
    hotel: Hotel,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Mismo contenido que en el apartado 3.3 (imagen y textos),
        // pero sin la fila de botones de editar y borrar.
    }
}
```

- La tarjeta del apartado 3.3 tenía botones de editar y borrar. Aquí ya no los necesita, porque esas acciones están en el detalle: solo avisa de que se ha pulsado, con `onClick`. `ElevatedCard` admite directamente ese parámetro.
- `contentPadding = PaddingValues(bottom = 80.dp)` deja espacio al final de la lista para que el botón «+» no tape el último hotel (apartado 3.5).

#### `PantallaDetalle.kt`

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaDetalle(
    hotel: Hotel,
    snackbarHostState: SnackbarHostState,
    onVolver: () -> Unit,
    onEditar: () -> Unit,
    onBorrar: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(hotel.name) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver a la lista"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = hotel.image,
                contentDescription = "Fotografía de ${hotel.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(hotel.name, style = MaterialTheme.typography.headlineSmall)
            Text("Ciudad: ${hotel.city}")
            Text("Provincia: ${hotel.province}")
            Text("Teléfono: ${hotel.phone}")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
            ) {
                OutlinedButton(onClick = onEditar) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Editar")
                }
                Button(
                    onClick = onBorrar,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Eliminar")
                }
            }
        }
    }
}
```

- `verticalScroll(rememberScrollState())` permite desplazar el detalle si no cabe en la pantalla.
- Los iconos de los botones llevan `contentDescription = null` porque el texto del botón ya describe la acción; así el lector de pantalla no la lee dos veces.

#### `AppPantallas.kt`

```kotlin
@Composable
fun AppPantallas(
    viewModel: HospedajeViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }

    // Se busca en la lista actual: si el hotel se edita, aquí llegan los datos nuevos.
    // Si se borra, find devuelve null y se vuelve a mostrar la lista.
    val hotelSeleccionado = uiState.hotelList.find { it.id == uiState.selectedHotelId }

    // Botón Atrás del móvil: desde el detalle, vuelve a la lista (apartado 5.5)
    BackHandler(enabled = hotelSeleccionado != null) {
        viewModel.onBackToList()
    }

    // Mensajes del Snackbar (apartado 8.2)
    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onUserMessageShown()
        }
    }

    // Navegación (apartado 5)
    if (hotelSeleccionado == null) {
        PantallaPrincipal(
            hoteles = uiState.hotelList,
            snackbarHostState = snackbarHostState,
            onHotelClick = { hotel -> viewModel.onHotelClicked(hotel) },
            onAddClick = { viewModel.onAddHotelClicked() }
        )
    } else {
        PantallaDetalle(
            hotel = hotelSeleccionado,
            snackbarHostState = snackbarHostState,
            onVolver = { viewModel.onBackToList() },
            onEditar = { viewModel.onEditHotelClicked(hotelSeleccionado) },
            onBorrar = { viewModel.onDeleteHotelClicked(hotelSeleccionado) }
        )
    }

    // Diálogos (apartado 7): se dibujan encima de la pantalla visible
    uiState.hotelToDelete?.let { hotel ->
        ConfirmDeleteDialog(
            hotel = hotel,
            onDismiss = { viewModel.onDismissDeleteDialog() },
            onConfirm = { viewModel.onConfirmDeleteHotel() }
        )
    }

    if (uiState.isAddDialogOpen) {
        AddEditHotelDialog(
            onDismiss = { viewModel.onDismissAddDialog() },
            onConfirm = { name, city, province, phone, image ->
                viewModel.onInsertHotel(name, city, province, phone, image)
            }
        )
    }

    uiState.hotelToEdit?.let { hotel ->
        AddEditHotelDialog(
            hotelToEdit = hotel,
            onDismiss = { viewModel.onDismissEditDialog() },
            onConfirm = { name, city, province, phone, image ->
                viewModel.onUpdateHotel(hotel.id, name, city, province, phone, image)
            }
        )
    }
}
```

`AppPantallas` queda organizado en bloques, cada uno con el apartado donde se explica: botón Atrás (5.5), mensajes (8.2), navegación (5) y diálogos (7).

#### Cómo queda cada operación

| Acción del usuario | Qué cambia en el estado | Qué se ve |
|---|---|---|
| Pulsa una tarjeta | `selectedHotelId = hotel.id` | `PantallaDetalle` de ese hotel |
| Pulsa la flecha o Atrás | `selectedHotelId = null` | `PantallaPrincipal` |
| Pulsa «+» en la lista | `isAddDialogOpen = true` | Diálogo de alta sobre la lista |
| Guarda el alta | Repositorio con el hotel nuevo, `isAddDialogOpen = false`, `userMessage` | La lista con el hotel nuevo y el `Snackbar` |
| Pulsa «Editar» en el detalle | `hotelToEdit = hotel` | Diálogo de edición sobre el detalle |
| Guarda la edición | Repositorio actualizado, `hotelToEdit = null`, `userMessage` | El detalle con los datos nuevos y el `Snackbar` |
| Pulsa «Eliminar» en el detalle | `hotelToDelete = hotel` | Diálogo de confirmación |
| Confirma el borrado | Repositorio sin el hotel, `selectedHotelId = null`, `userMessage` | La lista sin el hotel y el `Snackbar` |
| Cancela cualquier diálogo | El dato del diálogo vuelve a `null` o `false` | La misma pantalla, sin cambios |

> Este ejemplo sirve de referencia para la Versión 1.2 del proyecto. En esa versión es obligatorio el repositorio para el CRUD; el ViewModel es opcional. Si no lo usas, el estado (la lista, el elemento seleccionado y los diálogos abiertos) se guarda en `AppPantallas` con `remember`, como en el apartado 5. Cuando en la UD4 se use `NavHost`, solo cambiará `AppPantallas.kt`: las pantallas y el ViewModel seguirán igual (apartado 9.3).

---

## 9. Fragmentos y navegación

Si consultas documentación, tutoriales o proyectos antiguos de Android, encontrarás `Fragment`, `DialogFragment` y `FragmentManager`. Este apartado explica qué eran y qué los sustituye en Compose, usando lo que ya has hecho en esta unidad.

### 9.1 Qué era un Fragment

En el sistema clásico de Android, con las pantallas diseñadas en XML, una `Activity` podía dividirse en `Fragment`: piezas de interfaz con su propio diseño XML, su propia clase Kotlin y su propio ciclo de vida. Se usaban para:

- Reutilizar una parte de pantalla en varias `Activity`.
- Cambiar de pantalla dentro de una misma `Activity`, sustituyendo un `Fragment` por otro.
- Mostrar diálogos, con una clase especial llamada `DialogFragment`.

Para cambiar de `Fragment` se usaba el `FragmentManager`, con una "transacción":

```kotlin
supportFragmentManager.beginTransaction()
    .replace(R.id.contenedor, DetalleFragment())   // sustituye el Fragment visible
    .addToBackStack(null)                          // permite volver con Atrás
    .commit()
```

Cada pantalla necesitaba un archivo XML, una clase que heredaba de `Fragment` y código para pasarle los datos. Con Compose nada de esto hace falta.

### 9.2 Qué lo sustituye en Compose

| Sistema clásico | En Compose | Dónde lo has visto |
|---|---|---|
| `Fragment` con una pantalla completa | Una función `@Composable` en su propio archivo | `PantallaPrincipal`, `PantallaDetalle` (5.2) |
| `Fragment` con una parte de pantalla | Una función `@Composable` pequeña | `HotelCard` (3.3), `FormField` (7.4) |
| `FragmentManager` y transacciones | Una variable de estado y un `if` | `AppPantallas` (5.5) |
| `addToBackStack` (volver con Atrás) | `BackHandler` | `AppPantallas` (5.5) |
| `DialogFragment` | Un `AlertDialog` que se dibuja según el estado | `ConfirmDeleteDialog`, `AddEditHotelDialog` (7) |
| Datos que sobreviven al giro de pantalla | `ViewModel` | `HospedajeViewModel` (6.4) |

Una aplicación en Compose suele tener una sola `Activity` (`MainActivity`) y una función `@Composable` por pantalla.

### 9.3 Navigation Compose

En esta unidad, el cambio entre pantallas lo decide `AppPantallas` con una variable de estado y un `if` (apartado 5). Es suficiente para dos o tres pantallas. En aplicaciones con muchas pantallas, menú lateral o barra inferior se usa la librería Navigation Compose, que decide qué composable se muestra según una ruta.

Para que veas la relación, este sería el equivalente de `AppPantallas` con Navigation Compose:

```kotlin
val navController = rememberNavController()

NavHost(navController = navController, startDestination = "lista") {
    composable("lista") {
        PantallaPrincipal(/* ... */ onHotelClick = { hotel -> navController.navigate("detalle/${hotel.id}") })
    }
    composable("detalle/{id}") {
        PantallaDetalle(/* ... */ onVolver = { navController.popBackStack() })
    }
}
```

| | Con estado (esta unidad) | Con Navigation Compose (UD4) |
|---|---|---|
| Qué pantalla se ve | `if (hotelSeleccionado == null)` | La ruta actual: `"lista"` o `"detalle/{id}"` |
| Ir al detalle | `viewModel.onHotelClicked(hotel)` | `navController.navigate("detalle/${hotel.id}")` |
| Volver | `viewModel.onBackToList()` y `BackHandler` | `navController.popBackStack()`; Atrás funciona solo |

`PantallaPrincipal` y `PantallaDetalle` no cambian: siguen recibiendo datos y avisando con eventos. Solo cambia quién decide qué pantalla se ve.

> En esta unidad todavía no se usa `NavHost`. El código anterior es solo para que veas la relación. Navigation Compose se estudiará en la UD4 y se incorpora al proyecto en la Versión 1.4.

---

## 10. Actividades

### 10.1 Actividad 1: Trabajo con LazyColumns

Crea un proyecto nuevo de Compose (`Biblioteca`). Las partes A a D se hacen en una sola pantalla; en la parte E añadirás una pantalla de detalle. De momento los datos estarán en el propio código.

**Parte A. Lista básica**

1. Crea la `data class Libro(id, titulo, autor, anio, leido: Boolean)` con `id` generado con `UUID`.
2. Crea una lista de al menos 8 libros con `listOf(...)`.
3. Muestra los títulos con una `LazyColumn` y `items`. Comprueba que hace scroll.

**Parte B. Ítem personalizado**

4. Crea `@Composable fun LibroCard(libro: Libro, onClick: () -> Unit)` con una `ElevatedCard` que muestre título (en negrita), autor y año.
5. Si `leido` es `true`, muestra un icono `Icons.Default.Check` con su `contentDescription`.
6. Sustituye en la lista los `Text` por `LibroCard`. Al pulsar una tarjeta (`Modifier.clickable`), muestra un `Toast` con el título.

**Parte C. Calidad de la lista**

7. Añade `key = { it.id }` a `items`.
8. Usa `contentPadding` (16 dp) y `Arrangement.spacedBy(8.dp)`; elimina los márgenes del ítem si los duplican.
9. Añade un `item { }` de cabecera con el texto "Mis libros" y otro de pie con "Total: N libros".
10. Si la lista está vacía, muestra un mensaje centrado en lugar de la lista (prueba con `emptyList()`).

**Parte D. Interacción con estado**

11. Mantén la lista en un estado: `var libros by remember { mutableStateOf(listaInicial) }`.
12. Añade un botón en cada tarjeta para borrar el libro: crea una nueva lista sin él (`libros = libros.filter { it.id != libro.id }`) y avisa con un `Snackbar` ("Libro borrado").
13. Añade un botón o Checkbox en cada tarjeta para marcar el libro como leído.
14. Añade una imagen de internet a cada tarjeta con `AsyncImage`.

**Parte E. Pantalla de detalle (apartado 5)**

15. Mueve la lista inicial de libros fuera de cualquier función, en un `val librosDeEjemplo = listOf(...)`, para que los `id` no cambien en cada recomposición.
16. Crea `PantallaPrincipal.kt` y mueve allí la lista de las partes A a D. La función recibe los libros y los eventos: `fun PantallaPrincipal(libros: List<Libro>, onLibroClick: (Libro) -> Unit, onBorrar: (Libro) -> Unit, onMarcarLeido: (Libro) -> Unit)`. Al pulsar una tarjeta, en lugar del `Toast`, llama a `onLibroClick(libro)`.
17. Crea `PantallaDetalle.kt` con `fun PantallaDetalle(libro: Libro, onVolver: () -> Unit)`. Debe mostrar todos los datos del libro (imagen, título, autor, año y si está leído) y un botón «Volver».
18. Crea `AppPantallas.kt` con:
    - el estado de la lista: `var libros by remember { mutableStateOf(librosDeEjemplo) }`, que antes estaba en la pantalla;
    - el libro seleccionado: `var idSeleccionado by rememberSaveable { mutableStateOf<String?>(null) }`;
    - un `if` que muestre `PantallaPrincipal` o `PantallaDetalle`, igual que en el apartado 5.5.
19. Haz que `MainActivity` llame a `AppPantallas()` dentro de `setContent`.
20. Añade un `BackHandler` para que el botón Atrás del móvil vuelva del detalle a la lista sin cerrar la aplicación.
21. Comprueba que:
    - si marcas un libro como leído y abres su detalle, el detalle lo muestra como leído;
    - si giras el móvil en el detalle, sigues en el detalle.

    ¿Qué pasa con los libros borrados al girar? Vuelven a aparecer, porque `remember` pierde la lista. Esto se resuelve guardando los datos en un `ViewModel` (apartado 6).

### 10.2 Actividad 2: Navegación y arquitectura MVVM

Crea un proyecto nuevo de Compose (`Peliculas`): un catálogo de películas con una lista y una pantalla de detalle. La actividad practica los apartados 5 y 6: navegación con estado, repositorio, `UiState` y `ViewModel` con `mutableStateOf`. No se usan diálogos ni `Snackbar` (apartados 7 y 8).

Añade al `build.gradle` del módulo `app` las dependencias `lifecycle-viewmodel-compose` y `coil-compose` (apartado 1).

Estructura final del proyecto:

```text
app/src/main/java/com/ejemplo/peliculas/
├── Pelicula.kt               ← modelo
├── PeliculaRepository.kt     ← datos en memoria
├── PeliculasUiState.kt       ← estado de la interfaz
├── PeliculasViewModel.kt     ← estado y lógica
├── PantallaPrincipal.kt      ← lista de películas
├── PantallaDetalle.kt        ← datos de una película
├── AppPantallas.kt           ← decide qué pantalla se ve
└── MainActivity.kt
```

**Parte A. Modelo y repositorio (apartados 3.2 y 6.2)**

1. Crea `Pelicula.kt` con `data class Pelicula(id, titulo, director, anio: Int, genero, imagen, vista: Boolean = false)`, con un `id` generado con `UUID`.
2. Crea `PeliculaRepository.kt` con un `object PeliculaRepository` que guarde una lista privada de al menos 6 películas. Para la imagen puedes usar `https://picsum.photos/id/N/300/300`.
3. Añade al repositorio las funciones `getPeliculas()`, `getPeliculaById(id)`, `updatePelicula(pelicula)` y `deletePelicula(id)`. `getPeliculas()` debe devolver una copia (`toList()`).

**Parte B. Estado y ViewModel (apartados 6.3, 6.4 y 6.5)**

4. Crea `PeliculasUiState.kt` con `data class PeliculasUiState(val peliculas: List<Pelicula> = emptyList(), val idSeleccionado: String? = null)`.
5. Crea `PeliculasViewModel.kt` con `var uiState by mutableStateOf(PeliculasUiState())` y `private set`, para que las pantallas puedan leer el estado pero no cambiarlo. En el bloque `init`, carga las películas del repositorio.
6. Añade estas funciones de evento al ViewModel. Recuerda el patrón: primero se pide la operación al repositorio y después se recarga la lista en el estado con `uiState = uiState.copy(...)`.

    | Función | Qué hace |
    |---|---|
    | `onPeliculaClick(pelicula)` | Guarda su `id` en `idSeleccionado` |
    | `onVolver()` | Pone `idSeleccionado = null` |
    | `onMarcarVista(pelicula)` | Actualiza la película en el repositorio con `copy(vista = !pelicula.vista)` y recarga la lista |
    | `onBorrar(pelicula)` | La borra del repositorio, recarga la lista y pone `idSeleccionado = null` |

**Parte C. Pantallas, cada una en su archivo (apartado 5)**

7. Crea `PantallaPrincipal.kt` con `fun PantallaPrincipal(peliculas: List<Pelicula>, onPeliculaClick: (Pelicula) -> Unit)`:
    - una `LazyColumn` con `key`, en la que cada película se muestra en una `ElevatedCard` con imagen (`AsyncImage`), título y año;
    - si la película está vista, muestra el icono `Icons.Default.Check`;
    - una cabecera con el texto «Vistas: X de N», calculado a partir de la lista (`peliculas.count { it.vista }`).
8. Crea `PantallaDetalle.kt` con `fun PantallaDetalle(pelicula: Pelicula, onVolver: () -> Unit, onMarcarVista: () -> Unit, onBorrar: () -> Unit)`:
    - debe mostrar todos los datos de la película;
    - tendrá tres botones: «Volver», «Marcar como vista» (o «Marcar como no vista», según el estado) y «Borrar».
9. Comprueba que ninguna de las dos pantallas recibe el ViewModel: solo datos y lambdas.

**Parte D. Conectar las piezas (apartados 5.5 y 6.5)**

10. Crea `AppPantallas.kt` con `fun AppPantallas(viewModel: PeliculasViewModel = viewModel())`. Dentro:
    - lee el estado con `val uiState = viewModel.uiState`;
    - busca la película seleccionada en `uiState.peliculas` a partir de `uiState.idSeleccionado`;
    - muestra `PantallaPrincipal` o `PantallaDetalle` con un `if`, y conecta cada evento con su función del ViewModel;
    - añade un `BackHandler` que, en el detalle, llame a `viewModel.onVolver()`.
11. Haz que `MainActivity` llame a `AppPantallas()` dentro de `setContent`.

**Parte E. Comprobaciones**

12. Marca una película como vista desde el detalle y vuelve a la lista: el icono y el contador «Vistas: X de N» deben haberse actualizado.
13. Gira el móvil en el detalle: debes seguir en el mismo detalle. Gira también en la lista después de borrar una película: no debe reaparecer. Compara este comportamiento con el de la parte E de la actividad 10.1 y explica por qué ahora sí funciona.
14. Borra una película desde el detalle: debes volver a la lista y la película ya no debe aparecer.
15. Escribe, como comentario al principio de `AppPantallas.kt`, el recorrido del evento «Marcar como vista» paso a paso, desde que el usuario pulsa el botón hasta que la lista se redibuja. Indica en cada paso qué capa actúa (vista, ViewModel o repositorio) y si lo que viaja es un evento o el estado. Sigue el modelo de la tabla «Recorrido completo: borrar un hotel» del apartado 6.5.

**Parte F. Ampliación (opcional)**

16. Añade al `UiState` un campo `soloPendientes: Boolean = false` y al ViewModel la función `onFiltroCambiado(valor: Boolean)`.
17. En `PantallaPrincipal`, añade un `Switch` con el texto «Solo pendientes», que reciba el valor y avise con un evento.
18. En `AppPantallas`, pasa a `PantallaPrincipal` la lista filtrada (`peliculas.filter { !it.vista }`) cuando `soloPendientes` sea `true`. Piensa por qué el filtro no debe borrar películas del repositorio.

### 10.3 Actividad 3: Diálogos, Scaffold y Snackbar

Continúa el proyecto `Peliculas` de la actividad 10.2. Al terminar, el catálogo tendrá el CRUD completo: añadir, consultar, editar y borrar películas, con confirmación, validación y mensajes. La actividad practica los apartados 7 y 8.

Estructura final del proyecto. Las flechas indican lo que es nuevo o cambia respecto a la actividad 10.2:

```text
app/src/main/java/com/ejemplo/peliculas/
├── Pelicula.kt
├── PeliculaRepository.kt     ← añade addPelicula
├── PeliculasUiState.kt       ← nuevos campos para diálogos y mensajes
├── PeliculasViewModel.kt     ← nuevas funciones de evento
├── Dialogos.kt               ← NUEVO: confirmación de borrado y formulario
├── PantallaPrincipal.kt      ← Scaffold con barra superior y botón +
├── PantallaDetalle.kt        ← Scaffold con flecha de volver, Editar y Borrar
├── AppPantallas.kt           ← muestra los diálogos y el Snackbar
└── MainActivity.kt
```

**Parte A. Confirmar el borrado (apartado 7.2)**

1. Añade a `PeliculasUiState` el campo `peliculaABorrar: Pelicula? = null`.
2. En el ViewModel, sustituye `onBorrar(pelicula)` de la actividad 10.2 por tres funciones:

    | Función | Qué hace |
    |---|---|
    | `onBorrarClick(pelicula)` | Guarda la película en `peliculaABorrar`; no toca el repositorio |
    | `onCancelarBorrado()` | Pone `peliculaABorrar = null` |
    | `onConfirmarBorrado()` | Borra `peliculaABorrar` del repositorio, recarga la lista y pone `peliculaABorrar = null` e `idSeleccionado = null` |

3. Crea `Dialogos.kt` con `fun ConfirmarBorradoDialog(pelicula: Pelicula, onCancelar: () -> Unit, onConfirmar: () -> Unit)`. El texto debe incluir el título de la película, y el botón «Borrar» debe usar el color `error` del tema.
4. En `AppPantallas`, haz que el botón «Borrar» del detalle llame a `onBorrarClick` y muestra el diálogo con `uiState.peliculaABorrar?.let { ... }`.

**Parte B. Formulario de alta y edición (apartado 7.3)**

5. Añade al repositorio la función `addPelicula(pelicula)`.
6. Añade a `PeliculasUiState` los campos `mostrarDialogoAlta: Boolean = false` y `peliculaAEditar: Pelicula? = null`.
7. Añade al ViewModel estas funciones:

    | Función | Qué hace |
    |---|---|
    | `onAnadirClick()` / `onCerrarDialogoAlta()` | Abre o cierra el diálogo de alta |
    | `onGuardarNueva(titulo, director, anio, genero, imagen)` | Crea la película, la añade al repositorio, recarga la lista y cierra el diálogo |
    | `onEditarClick(pelicula)` / `onCerrarDialogoEdicion()` | Abre o cierra el diálogo de edición |
    | `onGuardarEdicion(id, titulo, director, anio, genero, imagen)` | Actualiza la película en el repositorio con `copy(...)`, recarga la lista y cierra el diálogo |

    Si la URL de la imagen llega vacía, usa una imagen por defecto con `ifBlank { ... }`.
8. Crea en `Dialogos.kt` el composable `PeliculaDialog(peliculaAEditar: Pelicula? = null, onCancelar: () -> Unit, onGuardar: (titulo: String, director: String, anio: Int, genero: String, imagen: String) -> Unit)`:
    - un campo de texto por cada dato, con su estado local (`remember`) y su valor inicial tomado de `peliculaAEditar` con `?:`;
    - el título del diálogo y el texto del botón cambian según sea un alta o una edición;
    - el año se escribe como texto. Antes de llamar a `onGuardar`, conviértelo a número con `anio.toInt()`. Hasta la parte C, escribe siempre un número en ese campo: si escribes letras, `toInt()` cierra la aplicación con un error.
9. Añade a `PantallaPrincipal` un botón provisional «Añadir película» que llame a un nuevo evento `onAnadirClick`, y a `PantallaDetalle` un botón «Editar» que llame a `onEditar`.
10. En `AppPantallas`, conecta los eventos y muestra el diálogo dos veces: para el alta cuando `mostrarDialogoAlta` es `true`, y para la edición cuando `peliculaAEditar` no es `null`.

**Parte C. Validación del formulario (apartado 7.4)**

11. Copia en `Dialogos.kt` el composable `FormField` del apartado 7.4 y úsalo en lugar de `OutlinedTextField` en `PeliculaDialog`.
12. Calcula un mensaje de error para cada campo con estas reglas:

    | Campo | Regla |
    |---|---|
    | Título | Obligatorio |
    | Director | Obligatorio |
    | Año | Obligatorio; debe ser un número (`toIntOrNull()` devuelve `null` si no lo es) entre 1888 y el año actual |
    | Género | Obligatorio |
    | Imagen | Opcional |

13. Añade la variable `showErrors` para que los errores solo aparezcan después del primer intento de guardar. Si el formulario no es válido, el botón «Guardar» no llama a `onGuardar`.
14. Marca los campos obligatorios con `*` en la etiqueta.

**Parte D. Barra superior y botón flotante (apartado 8.1)**

15. En `PantallaPrincipal`, envuelve el contenido en un `Scaffold` con:
    - una `TopAppBar` con el título «Mis películas»;
    - un `FloatingActionButton` con el icono `Icons.Default.Add`, que sustituye al botón provisional de la parte B.
16. En `PantallaDetalle`, envuelve el contenido en un `Scaffold` con una `TopAppBar` que muestre el título de la película y una flecha de volver en `navigationIcon`. Quita el botón «Volver» del contenido.
17. Aplica `paddingValues` al contenido de las dos pantallas y quita el `Box(Modifier.safeDrawingPadding())` de `AppPantallas`.
18. Añade `contentPadding = PaddingValues(bottom = 80.dp)` a la `LazyColumn` para que el botón flotante no tape la última película.

**Parte E. Mensajes con Snackbar (apartado 8.2)**

19. Añade a `PeliculasUiState` el campo `mensaje: String? = null` y al ViewModel la función `onMensajeMostrado()`, que lo vuelve a poner a `null`.
20. Haz que `onGuardarNueva`, `onGuardarEdicion` y `onConfirmarBorrado` escriban un mensaje: «Película añadida», «Película actualizada» y «Película 'Título' borrada», con el título de la película borrada.
21. En `AppPantallas`, crea un `SnackbarHostState` con `remember` y un `LaunchedEffect(uiState.mensaje)` que muestre el mensaje y después llame a `onMensajeMostrado()`.
22. Pasa el `SnackbarHostState` a las dos pantallas y colócalo en el parámetro `snackbarHost` de su `Scaffold`.

**Parte F. Comprobaciones**

23. Pulsa «Borrar» en el detalle y después «Cancelar»: la película no debe borrarse. Repite tocando fuera del diálogo.
24. Abre el diálogo de alta y pulsa «Guardar» sin escribir nada: deben aparecer los mensajes de error. Escribe en un campo obligatorio: su mensaje debe desaparecer mientras escribes.
25. Escribe «abc» y después «1500» en el año: deben aparecer mensajes de error distintos.
26. Edita una película desde el detalle: al guardar, el detalle debe mostrar los datos nuevos y debe aparecer el `Snackbar`.
27. Borra una película desde el detalle: debes volver a la lista, la película no debe aparecer y el `Snackbar` debe verse sobre la lista.
28. Abre el diálogo de edición, cambia el título sin guardar y gira el móvil. Observa qué ocurre con el diálogo y con el texto que habías escrito. Explica por qué el diálogo sigue abierto (pista: ¿dónde está guardado `peliculaAEditar`?) y por qué el texto escrito se pierde (pista: ¿dónde está guardado el estado de los campos?).

**Parte G. Ampliación (opcional)**

29. Al borrar una película, muestra el `Snackbar` con una acción «Deshacer» (apartado 2.2). Para ello:
    - guarda en el ViewModel la última película borrada;
    - en el `LaunchedEffect`, usa `showSnackbar(message = ..., actionLabel = "Deshacer")` y comprueba si el resultado es `SnackbarResult.ActionPerformed`;
    - si lo es, llama a una nueva función `onDeshacerBorrado()` que vuelva a añadir la película al repositorio y recargue la lista.
30. En la parte F, paso 28, el texto escrito se pierde al girar. Cambia `remember` por `rememberSaveable` en los campos de `PeliculaDialog` y comprueba la diferencia.
