---
title: UD 2. Interfaz de usuario con Jetpack Compose
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al inicio](index.md)

# UD 2. Interfaz de usuario con Jetpack Compose

> **Criterio de esta unidad:** todos los ejemplos de interfaz se escriben en **Kotlin** y con **Jetpack Compose**. No se utilizan layouts XML, `findViewById`, `ViewBinding`, `Activity` con vistas tradicionales ni Java.

Jetpack Compose es el toolkit moderno de Android para construir interfaces nativas mediante funciones de Kotlin. En lugar de describir una pantalla en un fichero XML y modificar objetos `View`, escribimos una función `@Composable` que describe qué interfaz debe mostrarse para un estado determinado.

La idea central puede resumirse así:

```text
estado de la aplicación -> composición -> interfaz visible
```

Cuando el estado cambia, Compose vuelve a ejecutar las partes necesarias de la composición. Este proceso se llama **recomposición**. El programador no debe indicar manualmente cómo encontrar cada control ni cómo actualizarlo; debe modelar el estado y describir la interfaz que corresponde a ese estado.

## Índice

1. [Objetivos](#1-objetivos)
2. [Del layout clásico a Compose](#2-del-layout-clásico-a-compose)
3. [Preparación del proyecto](#3-preparación-del-proyecto)
4. [El modelo mental de Compose](#4-el-modelo-mental-de-compose)
5. [Funciones `@Composable` y composición](#5-funciones-composable-y-composición)
6. [Medidas, unidades y modificadores](#6-medidas-unidades-y-modificadores)
7. [Layouts: `Column`, `Row`, `Box` y pesos](#7-layouts-column-row-box-y-pesos)
8. [Listas, tablas y tarjetas](#8-listas-tablas-y-tarjetas)
9. [Texto, imágenes e iconos](#9-texto-imágenes-e-iconos)
10. [Botones y acciones](#10-botones-y-acciones)
11. [Entrada de texto y teclado](#11-entrada-de-texto-y-teclado)
12. [Controles de selección](#12-controles-de-selección)
13. [Estado y eventos](#13-estado-y-eventos)
14. [Material Design y temas](#14-material-design-y-temas)
15. [Visibilidad, diálogos y mensajes](#15-visibilidad-diálogos-y-mensajes)
16. [Animaciones y transiciones](#16-animaciones-y-transiciones)
17. [Operaciones asíncronas y `ProgressIndicator`](#17-operaciones-asíncronas-y-progressindicator)
18. [Navegación entre pantallas](#18-navegación-entre-pantallas)
19. [Accesibilidad y diseño adaptable](#19-accesibilidad-y-diseño-adaptable)
20. [Arquitectura de una pantalla](#20-arquitectura-de-una-pantalla)
21. [Prácticas guiadas](#21-prácticas-guiadas)
22. [Proyecto de unidad](#22-proyecto-de-unidad)
23. [Errores frecuentes](#23-errores-frecuentes)
24. [Comprobación de conocimientos](#24-comprobación-de-conocimientos)
25. [Documentación y videotutoriales](#25-documentación-y-videotutoriales)

---

## 1. Objetivos

Al finalizar esta unidad el alumnado será capaz de:

1. Explicar qué problema resuelve Jetpack Compose y diferenciarlo del sistema de vistas tradicional.
2. Crear funciones reutilizables anotadas con `@Composable`.
3. Organizar una pantalla con `Column`, `Row`, `Box`, `Surface` y `Card`.
4. Aplicar tamaño, márgenes visuales, relleno, alineación, fondo, borde y clics con `Modifier`.
5. Mostrar texto, imágenes, iconos, botones y tarjetas usando Material 3.
6. Recoger texto mediante `TextField` y validar formularios.
7. Implementar `Checkbox`, `RadioButton`, `Switch`, `Slider` y menús desplegables.
8. Separar el estado de la interfaz y elevarlo al componente adecuado.
9. Comprender la recomposición y evitar efectos secundarios durante la composición.
10. Presentar listas eficientes con `LazyColumn` y `LazyVerticalGrid`.
11. Crear temas coherentes con colores, tipografías y formas de Material 3.
12. Añadir animaciones, indicadores de progreso, diálogos y navegación.
13. Diseñar interfaces que se adapten a distintas pantallas y sean accesibles.
14. Probar componentes Compose y justificar las decisiones de diseño.

---

## 2. Del layout clásico a Compose

Los PDF de partida presentan una jerarquía de `View` y `ViewGroup`, layouts XML, `TextView`, `Button`, `EditText`, `ConstraintLayout`, `CardView` y controles de selección. Esos conceptos siguen siendo útiles para entender el problema, pero en esta asignatura se implementan con Compose.

| Concepto tradicional | Equivalente recomendado en Compose |
|---|---|
| `FrameLayout` | `Box` |
| `LinearLayout` vertical | `Column` |
| `LinearLayout` horizontal | `Row` |
| `layout_weight` | `Modifier.weight` |
| `TableLayout` | `Row`, `Column` o `LazyVerticalGrid` |
| `ConstraintLayout` | `Row`/`Column`/`Box`; `ConstraintLayout` para composiciones realmente constreñidas |
| `CardView` | `Card` o `ElevatedCard` |
| `TextView` | `Text` |
| `ImageView` | `Image` o `AsyncImage` |
| `Button` | `Button`, `OutlinedButton`, `TextButton`, `IconButton` |
| `EditText` | `TextField` u `OutlinedTextField` |
| `CheckBox` | `Checkbox` |
| `RadioButton` y `RadioGroup` | `RadioButton` dentro de un grupo lógico |
| `ToggleButton`/`Switch` | `Switch`, `FilterChip` o `SegmentedButton` |
| `ProgressBar` | `CircularProgressIndicator` o `LinearProgressIndicator` |
| `Toast` | `Snackbar` como feedback principal |
| `Handler`/actualizaciones manuales | corrutinas, `LaunchedEffect` y estado observable |
| `res/values`, estilos XML | `MaterialTheme`, `stringResource`, recursos Compose |

### 2.1 La interfaz como función

Una vista clásica se crea y después se modifica:

```kotlin
// Enfoque que no utilizaremos en esta unidad:
// val titulo = findViewById<TextView>(...)
// titulo.text = "Hola"
```

En Compose se describe directamente:

```kotlin
@Composable
fun Saludo(nombre: String) {
    Text(text = "Hola, $nombre")
}
```

La función no devuelve una `View`. Su salida es una descripción de UI que Compose puede insertar, actualizar y eliminar.

### 2.2 Ventajas y responsabilidades

**Ventajas:**

- Menos código de infraestructura.
- Componentes reutilizables y parametrizables.
- Estado y UI más fáciles de razonar conjuntamente.
- Previsualización mediante `@Preview`.
- APIs de accesibilidad, animación y Material integradas.
- Mejor adaptación a tamaños de pantalla cuando se utilizan layouts declarativos.

**Responsabilidades:**

- No introducir trabajo costoso en el cuerpo de un composable.
- No modificar variables normales esperando que Compose observe el cambio.
- Elegir correctamente el alcance del estado.
- Proporcionar claves estables a listas dinámicas.
- Diseñar estados de carga, error y contenido, no solo el caso feliz.

---

## 3. Preparación del proyecto

Se recomienda crear en Android Studio un proyecto **Empty Activity** con Kotlin y Jetpack Compose. La plantilla actual suele configurar Compose y Material 3 automáticamente. Si se configura el proyecto manualmente, las versiones concretas deben corresponderse con la documentación oficial y con el catálogo de versiones del proyecto.

Ejemplo conceptual de dependencias en Gradle Kotlin DSL:

```kotlin
dependencies {
    implementation(platform("androidx.compose:compose-bom:2025.10.00"))
    implementation("androidx.activity:activity-compose:1.11.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation(platform("androidx.compose:compose-bom:2025.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
```

> Las versiones son orientativas. Antes de copiar el bloque, comprueba las versiones estables actuales en la documentación de Android y en el proyecto generado por Android Studio. No se deben mezclar versiones incompatibles de librerías Compose.

Una actividad mínima queda reducida a proporcionar el contenido Compose:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                PantallaPrincipal()
            }
        }
    }
}
```

La actividad no construye controles ni contiene la lógica de cada botón. Su responsabilidad inicial es conectar el ciclo de vida de Android con el árbol de composición.

---

## 4. El modelo mental de Compose

### 4.1 Composición, recomposición y abandono

- **Composición:** primera ejecución de una función `@Composable`.
- **Recomposición:** ejecución posterior de las partes que dependen de un estado que ha cambiado.
- **Abandono:** Compose elimina una parte cuando deja de pertenecer al árbol.

Ejemplo:

```kotlin
@Composable
fun Contador() {
    var contador by remember { mutableIntStateOf(0) }

    Column {
        Text(text = "Pulsaciones: $contador")
        Button(onClick = { contador++ }) {
            Text("Sumar")
        }
    }
}
```

Al pulsar el botón, cambia `contador`. Compose vuelve a ejecutar las funciones afectadas y el texto pasa a mostrar el nuevo valor.

### 4.2 Composables puros

Un composable debería comportarse como una función: mismos parámetros, misma UI. No debe lanzar una petición de red, escribir en una base de datos ni modificar un singleton cada vez que se recompone.

La interfaz recibe datos y eventos:

```kotlin
@Composable
fun FilaProducto(
    nombre: String,
    precio: String,
    onEliminar: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(text = nombre, style = MaterialTheme.typography.titleMedium)
            Text(text = precio, style = MaterialTheme.typography.bodyMedium)
        }
        IconButton(onClick = onEliminar) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Eliminar $nombre",
            )
        }
    }
}
```

Este patrón se denomina **flujo unidireccional de datos**:

```text
estado -> composable -> evento -> propietario del estado -> nuevo estado
```

### 4.3 `remember` y `rememberSaveable`

`remember` conserva un valor durante recomposiciones, pero no necesariamente ante la recreación de la actividad:

```kotlin
var texto by remember { mutableStateOf("") }
```

`rememberSaveable` permite conservar valores simples cuando Android recrea la actividad por un cambio de configuración:

```kotlin
var texto by rememberSaveable { mutableStateOf("") }
```

No es un sustituto de un `ViewModel`. Para estado de pantalla importante, especialmente si procede de repositorios o debe sobrevivir a procesos, se utiliza una arquitectura con `ViewModel` y `StateFlow`.

---

## 5. Funciones `@Composable` y composición

Una función composable:

1. Lleva la anotación `@Composable`.
2. Puede llamar a otros composables.
3. Puede recibir parámetros normales y lambdas.
4. No tiene que devolver un objeto visual.

```kotlin
@Composable
fun Cabecera(titulo: String, subtitulo: String?) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = titulo, style = MaterialTheme.typography.headlineSmall)
        if (subtitulo != null) {
            Text(
                text = subtitulo,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
```

### 5.1 Composición de una pantalla

```kotlin
@Composable
fun PantallaPerfil() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil") }) },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
        ) {
            Cabecera(
                titulo = "Datos personales",
                subtitulo = "Actualiza la información de tu perfil",
            )
            FormularioPerfil()
        }
    }
}
```

`Scaffold` proporciona espacios para elementos comunes de una pantalla, como barra superior, barra inferior, botón de acción flotante y mensajes.

### 5.2 Previsualizaciones

```kotlin
@Preview(showBackground = true)
@Composable
private fun CabeceraPreview() {
    AppTheme {
        Cabecera(
            titulo = "Ejemplo",
            subtitulo = "Vista previa en Android Studio",
        )
    }
}
```

Una preview debe ser rápida y no depender de red, base de datos ni navegación real. Se pueden crear varias previews para tema claro, tema oscuro, contenido largo y estados vacíos.

---

## 6. Medidas, unidades y modificadores

### 6.1 `dp`, `sp` y píxeles

- `dp` representa una unidad independiente de la densidad para tamaños y espacios.
- `sp` se utiliza para texto y respeta la escala de fuente elegida por la persona usuaria.
- Los píxeles físicos no deben utilizarse como medida de diseño normal.

```kotlin
Text(
    text = "Texto adaptable",
    fontSize = 18.sp,
    modifier = Modifier.padding(16.dp),
)
```

La accesibilidad exige no fijar tamaños de texto que impidan que el usuario amplíe la fuente.

### 6.2 El orden de los modificadores importa

```kotlin
Text(
    text = "Ejemplo",
    modifier = Modifier
        .background(MaterialTheme.colorScheme.primary)
        .padding(16.dp),
)
```

Aquí el fondo incluye el espacio interno. Si se invierte el orden, el relleno queda fuera del fondo:

```kotlin
Text(
    text = "Otro ejemplo",
    modifier = Modifier
        .padding(16.dp)
        .background(MaterialTheme.colorScheme.primary),
)
```

### 6.3 Modificadores esenciales

```kotlin
Modifier
    .fillMaxWidth()
    .fillMaxHeight()
    .fillMaxSize()
    .width(200.dp)
    .height(56.dp)
    .size(48.dp)
    .padding(16.dp)
    .padding(horizontal = 16.dp, vertical = 8.dp)
    .background(Color.LightGray)
    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
    .clip(RoundedCornerShape(12.dp))
    .clickable { /* evento */ }
```

Para zonas táctiles, se debe preferir un componente Material que ya proporcione semántica y comportamiento. No se debe convertir cualquier texto en un botón usando solo `clickable` si lo que se necesita es una acción de botón.

### 6.4 Márgenes y relleno

Compose no tiene un atributo `margin` equivalente. El espacio exterior se obtiene colocando `padding` en el elemento o en el contenedor que corresponda:

```kotlin
Card(
    modifier = Modifier.padding(vertical = 8.dp),
) {
    Text(
        text = "Contenido",
        modifier = Modifier.padding(16.dp),
    )
}
```

---

## 7. Layouts: `Column`, `Row`, `Box` y pesos

### 7.1 `Column`

Apila elementos verticalmente:

```kotlin
Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(12.dp),
) {
    Text("Título")
    Text("Descripción")
    Button(onClick = {}) {
        Text("Continuar")
    }
}
```

### 7.2 `Row`

Coloca elementos horizontalmente:

```kotlin
Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
) {
    Text("Notificaciones")
    Switch(checked = true, onCheckedChange = {})
}
```

### 7.3 `Box`

Permite superponer elementos y es el sustituto natural de `FrameLayout`:

```kotlin
Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(220.dp),
) {
    Image(
        painter = painterResource(R.drawable.portada),
        contentDescription = "Portada",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize(),
    )
    Text(
        text = "Título sobre la imagen",
        color = Color.White,
        modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(16.dp),
    )
}
```

### 7.4 Peso proporcional

El equivalente Compose de `layout_weight` es `Modifier.weight`:

```kotlin
Row(modifier = Modifier.fillMaxWidth()) {
    Button(
        onClick = {},
        modifier = Modifier.weight(1f),
    ) {
        Text("25 %")
    }
    Button(
        onClick = {},
        modifier = Modifier.weight(1f),
    ) {
        Text("25 %")
    }
    Button(
        onClick = {},
        modifier = Modifier.weight(2f),
    ) {
        Text("50 %")
    }
}
```

En una `Column`, el peso reparte la altura disponible; en un `Row`, la anchura disponible.

### 7.5 Separación y `Arrangement`

En lugar de añadir márgenes manualmente a cada hijo:

```kotlin
Column(
    verticalArrangement = Arrangement.spacedBy(8.dp),
) {
    Text("Primero")
    Text("Segundo")
    Text("Tercero")
}
```

---

## 8. Listas, tablas y tarjetas

### 8.1 `LazyColumn`

Para una lista grande se deben utilizar componentes lazy: solo componen los elementos visibles.

```kotlin
data class Alumno(
    val id: Int,
    val nombre: String,
    val curso: String,
)

@Composable
fun ListaAlumnos(alumnos: List<Alumno>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = alumnos,
            key = { alumno -> alumno.id },
        ) { alumno ->
            AlumnoCard(alumno)
        }
    }
}
```

Las claves estables ayudan a Compose a conservar correctamente el estado de los elementos cuando cambia el orden.

### 8.2 Grids

Una tabla de tarjetas se puede crear con `LazyVerticalGrid`:

```kotlin
@Composable
fun Galeria(productos: List<String>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(productos) { producto ->
            Card {
                Text(
                    text = producto,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
```

`GridCells.Adaptive` adapta el número de columnas al ancho disponible.

### 8.3 Tarjetas Material

```kotlin
@Composable
fun AlumnoCard(alumno: Alumno) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { /* abrir detalle */ },
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = alumno.nombre,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = alumno.curso,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
```

La elevación, las esquinas y los colores deben estar al servicio de la jerarquía visual. No se deben llenar todas las pantallas de tarjetas solo por tener una sombra.

### 8.4 Una tabla de datos

Para una tabla pequeña y conocida:

```kotlin
@Composable
fun TablaResumen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline),
    ) {
        FilaTabla("Asignatura", "Nota", esCabecera = true)
        FilaTabla("Kotlin", "8,5")
        FilaTabla("Android", "9,0")
    }
}

@Composable
private fun FilaTabla(
    izquierda: String,
    derecha: String,
    esCabecera: Boolean = false,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (esCabecera) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    Color.Transparent
                },
            )
            .padding(12.dp),
    ) {
        Text(izquierda, modifier = Modifier.weight(1f))
        Text(derecha)
    }
}
```

---

## 9. Texto, imágenes e iconos

### 9.1 Texto

```kotlin
Text(
    text = "Título principal",
    style = MaterialTheme.typography.headlineMedium,
    maxLines = 2,
    overflow = TextOverflow.Ellipsis,
)
```

No se debe usar `fontSize` para imitar todos los estilos. Material 3 proporciona una escala tipográfica coherente: `display`, `headline`, `title`, `body` y `label`.

### 9.2 Texto enriquecido

```kotlin
Text(
    buildAnnotatedString {
        append("Lee las ")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("condiciones")
        }
        append(" antes de continuar.")
    },
)
```

### 9.3 Imágenes locales

```kotlin
Image(
    painter = painterResource(R.drawable.logo),
    contentDescription = "Logotipo de la aplicación",
    modifier = Modifier.size(96.dp),
)
```

Si la imagen es decorativa, el `contentDescription` debe ser `null`. Si transmite información, debe describirse de forma útil y no con frases como “imagen de”.

### 9.4 Iconos

```kotlin
IconButton(onClick = { /* editar */ }) {
    Icon(
        imageVector = Icons.Default.Edit,
        contentDescription = "Editar perfil",
    )
}
```

Los iconos sin descripción dentro de controles accionables no son accesibles. El texto de la descripción debe expresar la acción.

Para imágenes remotas, se puede utilizar Coil y `AsyncImage`. La pantalla debe contemplar carga, error y contenido:

```kotlin
@Composable
fun Avatar(url: String, nombre: String) {
    AsyncImage(
        model = url,
        contentDescription = "Avatar de $nombre",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape),
    )
}
```

---

## 10. Botones y acciones

Compose Material ofrece distintos botones según la importancia de la acción:

```kotlin
Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
    Button(onClick = {}) {
        Text("Acción principal")
    }
    OutlinedButton(onClick = {}) {
        Text("Acción secundaria")
    }
    TextButton(onClick = {}) {
        Text("Acción de bajo énfasis")
    }
    IconButton(onClick = {}) {
        Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
    }
}
```

### 10.1 Estados del botón

```kotlin
Button(
    onClick = { /* guardar */ },
    enabled = formularioValido && !estaGuardando,
) {
    if (estaGuardando) {
        CircularProgressIndicator(
            modifier = Modifier.size(18.dp),
            strokeWidth = 2.dp,
        )
    } else {
        Text("Guardar")
    }
}
```

Un botón deshabilitado no debe ser la única forma de explicar un error. La interfaz debe indicar qué campo falta o qué condición no se cumple.

### 10.2 Botón con imagen

```kotlin
Button(
    onClick = {},
    contentPadding = PaddingValues(horizontal = 16.dp),
) {
    Icon(Icons.Default.Download, contentDescription = null)
    Spacer(Modifier.width(8.dp))
    Text("Descargar")
}
```

---

## 11. Entrada de texto y teclado

### 11.1 `TextField` y `OutlinedTextField`

```kotlin
@Composable
fun CampoNombre(
    nombre: String,
    onNombreChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = nombre,
        onValueChange = onNombreChange,
        label = { Text("Nombre") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}
```

El valor es controlado por el estado que recibe la función. El campo no guarda por su cuenta la verdad de la aplicación.

### 11.2 Contraseña, teclado y transformación

```kotlin
@Composable
fun CampoPassword(
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        singleLine = true,
    )
}
```

No se deben registrar contraseñas en logs ni almacenarlas en texto plano.

### 11.3 Campo multilínea y contador

```kotlin
@Composable
fun CampoDescripcion(
    descripcion: String,
    onDescripcionChange: (String) -> Unit,
) {
    val maximo = 200

    OutlinedTextField(
        value = descripcion,
        onValueChange = { nuevo ->
            if (nuevo.length <= maximo) onDescripcionChange(nuevo)
        },
        label = { Text("Descripción") },
        supportingText = {
            Text("${descripcion.length}/$maximo")
        },
        minLines = 3,
        maxLines = 5,
        modifier = Modifier.fillMaxWidth(),
    )
}
```

### 11.4 Validación

```kotlin
data class ErroresFormulario(
    val nombre: String? = null,
    val email: String? = null,
)

fun validarFormulario(nombre: String, email: String): ErroresFormulario =
    ErroresFormulario(
        nombre = if (nombre.isBlank()) "El nombre es obligatorio" else null,
        email = if (!email.contains("@")) "Introduce un correo válido" else null,
    )
```

El mensaje de error debe acompañar al campo y no aparecer únicamente en un `Toast`.

---

## 12. Controles de selección

### 12.1 `Checkbox`

Permite seleccionar varias opciones simultáneamente:

```kotlin
@Composable
fun OpcionModulo(
    nombre: String,
    seleccionado: Boolean,
    onSeleccionadoChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSeleccionadoChange(!seleccionado) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = seleccionado,
            onCheckedChange = onSeleccionadoChange,
        )
        Text(nombre)
    }
}
```

Cuando se hace clic en la fila, el checkbox debe seguir siendo coherente con la fila y no crear dos acciones contradictorias.

### 12.2 `RadioButton`

Permite una elección única dentro de un conjunto:

```kotlin
enum class TipoEntrega(val etiqueta: String) {
    DOMICILIO("A domicilio"),
    TIENDA("Recoger en tienda"),
    PUNTO("Punto de recogida"),
}

@Composable
fun SelectorEntrega(
    seleccionada: TipoEntrega,
    onSeleccionadaChange: (TipoEntrega) -> Unit,
) {
    Column {
        TipoEntrega.entries.forEach { opcion ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = opcion == seleccionada,
                        onClick = { onSeleccionadaChange(opcion) },
                        role = Role.RadioButton,
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = opcion == seleccionada,
                    onClick = null,
                )
                Text(opcion.etiqueta)
            }
        }
    }
}
```

### 12.3 `Switch`

Para una preferencia binaria:

```kotlin
@Composable
fun Preferencia(
    activada: Boolean,
    onActivadaChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("Recibir notificaciones")
        Switch(
            checked = activada,
            onCheckedChange = onActivadaChange,
        )
    }
}
```

### 12.4 `Slider`

```kotlin
@Composable
fun SelectorVolumen(
    volumen: Float,
    onVolumenChange: (Float) -> Unit,
) {
    Column {
        Text("Volumen: ${(volumen * 100).roundToInt()} %")
        Slider(
            value = volumen,
            onValueChange = onVolumenChange,
            valueRange = 0f..1f,
        )
    }
}
```

### 12.5 Menú desplegable

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorCurso(
    cursos: List<String>,
    curso: String,
    onCursoChange: (String) -> Unit,
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido },
    ) {
        OutlinedTextField(
            value = curso,
            onValueChange = {},
            readOnly = true,
            label = { Text("Curso") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false },
        ) {
            cursos.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onCursoChange(opcion)
                        expandido = false
                    },
                )
            }
        }
    }
}
```

---

## 13. Estado y eventos

### 13.1 Estado local

Adecuado para un detalle de presentación que no necesita salir del componente:

```kotlin
@Composable
fun MostrarOcultar() {
    var visible by rememberSaveable { mutableStateOf(false) }

    Column {
        Button(onClick = { visible = !visible }) {
            Text(if (visible) "Ocultar" else "Mostrar")
        }
        AnimatedVisibility(visible = visible) {
            Text("Contenido visible")
        }
    }
}
```

### 13.2 Elevación del estado

Si dos componentes necesitan el mismo dato, el estado se eleva a su ancestro común:

```kotlin
@Composable
fun PantallaContador() {
    var contador by rememberSaveable { mutableIntStateOf(0) }

    ContadorTexto(contador = contador)
    ControlesContador(
        onSumar = { contador++ },
        onRestar = { contador-- },
    )
}

@Composable
fun ContadorTexto(contador: Int) {
    Text("Valor: $contador")
}

@Composable
fun ControlesContador(
    onSumar: () -> Unit,
    onRestar: () -> Unit,
) {
    Row {
        Button(onClick = onRestar) { Text("-") }
        Button(onClick = onSumar) { Text("+") }
    }
}
```

### 13.3 Estado de pantalla

Para una pantalla real conviene modelar estados explícitos:

```kotlin
sealed interface EstadoAlumnos {
    data object Cargando : EstadoAlumnos
    data class Contenido(val alumnos: List<Alumno>) : EstadoAlumnos
    data class Error(val mensaje: String) : EstadoAlumnos
}

@Composable
fun AlumnosContent(
    estado: EstadoAlumnos,
    onReintentar: () -> Unit,
) {
    when (estado) {
        EstadoAlumnos.Cargando -> CircularProgressIndicator()
        is EstadoAlumnos.Contenido -> ListaAlumnos(estado.alumnos)
        is EstadoAlumnos.Error -> Column {
            Text(estado.mensaje)
            Button(onClick = onReintentar) {
                Text("Reintentar")
            }
        }
    }
}
```

### 13.4 Efectos

`LaunchedEffect` ejecuta una corrutina asociada al ciclo de vida del composable:

```kotlin
@Composable
fun CargarAlEntrar(
    cargar: suspend () -> Unit,
) {
    LaunchedEffect(Unit) {
        cargar()
    }
}
```

La clave debe representar cuándo debe reiniciarse el efecto. Una clave que cambia en cada recomposición puede cancelar y relanzar trabajo continuamente.

---

## 14. Material Design y temas

Material Design proporciona reglas de color, tipografía, formas, elevación, estados y componentes. Material 3 incorpora el sistema de diseño actual y Material You.

### 14.1 Tema centralizado

```kotlin
@Composable
fun AppTheme(
    contenido: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) {
            darkColorScheme()
        } else {
            lightColorScheme()
        },
        typography = Typography(),
        shapes = Shapes(),
        content = contenido,
    )
}
```

Los componentes deben consultar `MaterialTheme` en lugar de repetir colores arbitrarios:

```kotlin
Text(
    text = "Información secundaria",
    color = MaterialTheme.colorScheme.onSurfaceVariant,
)
```

### 14.2 Superficies y contraste

```kotlin
Surface(
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 2.dp,
) {
    Text(
        text = "Contenido",
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(16.dp),
    )
}
```

Cada color de fondo debe tener un color de contenido con contraste suficiente. No se debe elegir texto claro sobre fondos claros solo por estética.

### 14.3 Material 3 y recursos

Los textos visibles deben externalizarse en recursos de Android cuando la aplicación vaya a traducirse. En Compose se pueden leer con `stringResource`:

```kotlin
Text(text = stringResource(R.string.bienvenida))
```

---

## 15. Visibilidad, diálogos y mensajes

### 15.1 Visibilidad condicional

```kotlin
if (mostrarResultado) {
    Text("Resultado disponible")
}
```

Cuando se quiere animar:

```kotlin
AnimatedVisibility(visible = mostrarResultado) {
    Card {
        Text("Resultado", modifier = Modifier.padding(16.dp))
    }
}
```

No existe una propiedad `visibility = invisible` que debamos manipular. La ausencia de un composable del árbol es la forma declarativa de ocultarlo.

### 15.2 `AlertDialog`

```kotlin
@Composable
fun ConfirmarEliminacion(
    visible: Boolean,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit,
) {
    if (visible) {
        AlertDialog(
            onDismissRequest = onCancelar,
            title = { Text("Eliminar elemento") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = onConfirmar) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = onCancelar) {
                    Text("Cancelar")
                }
            },
        )
    }
}
```

### 15.3 `Snackbar`

```kotlin
@Composable
fun PantallaConMensaje() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Button(
            onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Guardado correctamente")
                }
            },
            modifier = Modifier.padding(innerPadding),
        ) {
            Text("Guardar")
        }
    }
}
```

Un `Snackbar` suele ser preferible a un `Toast` porque queda integrado en la composición y puede incluir una acción.

---

## 16. Animaciones y transiciones

### 16.1 Aparición y desaparición

```kotlin
AnimatedVisibility(visible = visible) {
    Text(
        text = "Aparezco con una transición",
        modifier = Modifier.animateContentSize(),
    )
}
```

### 16.2 Animar un valor

```kotlin
@Composable
fun TarjetaExpandible(expandida: Boolean) {
    val altura by animateDpAsState(
        targetValue = if (expandida) 240.dp else 80.dp,
        label = "altura de tarjeta",
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(altura),
    ) {
        Text("Contenido", modifier = Modifier.padding(16.dp))
    }
}
```

### 16.3 Buenas decisiones

- Animar cambios que ayuden a comprender una transición.
- Evitar animaciones constantes o demasiado largas.
- Respetar la preferencia de reducir movimiento cuando sea relevante.
- No animar elementos cuya actualización deba ser inmediata, como un error crítico.

---

## 17. Operaciones asíncronas y `ProgressIndicator`

Una interfaz no debe bloquearse mientras carga datos. Se representa el estado de carga y se ejecuta el trabajo fuera del cuerpo de composición.

```kotlin
@Composable
fun PantallaCarga(
    cargando: Boolean,
    contenido: @Composable () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        contenido()
        if (cargando) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Black.copy(alpha = 0.25f),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
```

Indicador determinado:

```kotlin
LinearProgressIndicator(
    progress = { progreso },
    modifier = Modifier.fillMaxWidth(),
)
```

Indicador indeterminado:

```kotlin
CircularProgressIndicator()
```

Ejemplo con `ViewModel`:

```kotlin
data class UiState(
    val cargando: Boolean = false,
    val mensaje: String? = null,
)

class EjemploViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun cargar() {
        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true, mensaje = null) }
            runCatching {
                delay(1_000)
                "Datos recibidos"
            }.onSuccess { resultado ->
                _uiState.update {
                    it.copy(cargando = false, mensaje = resultado)
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(cargando = false, mensaje = error.message)
                }
            }
        }
    }
}
```

La interfaz puede recoger el flujo con `collectAsStateWithLifecycle()` para respetar el ciclo de vida.

---

## 18. Navegación entre pantallas

La navegación se implementa con Navigation Compose. La ruta es un contrato; conviene evitar construir rutas concatenando texto sin codificar.

```kotlin
@Composable
fun NavegacionPrincipal() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inicio",
    ) {
        composable("inicio") {
            PantallaInicio(
                onAbrirDetalle = { id ->
                    navController.navigate("detalle/$id")
                },
            )
        }
        composable(
            route = "detalle/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            }),
        ) { entrada ->
            val id = entrada.arguments?.getInt("id")
                ?: return@composable
            PantallaDetalle(id = id)
        }
    }
}
```

La pantalla no debería crear directamente el repositorio ni asumir que siempre existe un argumento. Los errores de navegación deben modelarse y mostrarse de forma explícita.

---

## 19. Accesibilidad y diseño adaptable

### 19.1 Semántica

Compose genera semántica para muchos componentes Material. Cuando se construye un componente personalizado:

```kotlin
Box(
    modifier = Modifier.semantics {
        contentDescription = "Imagen de perfil"
    },
) {
    // contenido
}
```

Buenas prácticas:

- Describir imágenes informativas.
- Usar `Role.Button`, `Role.RadioButton` o componentes Material adecuados.
- Mantener objetivos táctiles cómodos.
- No comunicar información solo mediante color.
- Permitir fuentes grandes y contenido desplazable.
- Comprobar el orden de foco con TalkBack.

### 19.2 Diseño adaptable

No hay que diseñar para una única resolución. Se deben usar `fillMaxWidth`, `weight`, `WindowSizeClass` y grids adaptativos.

```kotlin
@Composable
fun ContenidoAdaptable(anchoCompacto: Boolean) {
    if (anchoCompacto) {
        Column { /* móvil estrecho */ }
    } else {
        Row { /* tablet o pantalla ancha */ }
    }
}
```

No se debe colocar todo con offsets absolutos. El diseño debe responder a cambios de orientación, idioma y tamaño de fuente.

---

## 20. Arquitectura de una pantalla

Una separación útil consiste en tres capas:

1. **Pantalla conectada:** obtiene el `ViewModel` y recoge el estado.
2. **Contenido puro:** recibe estado y callbacks.
3. **Componentes:** dibujan partes pequeñas y reutilizables.

```kotlin
@Composable
fun AlumnosRoute(
    viewModel: AlumnosViewModel = viewModel(),
) {
    val estado by viewModel.uiState.collectAsStateWithLifecycle()

    AlumnosScreen(
        estado = estado,
        onReintentar = viewModel::cargar,
        onAlumnoClick = viewModel::seleccionar,
    )
}

@Composable
fun AlumnosScreen(
    estado: EstadoAlumnos,
    onReintentar: () -> Unit,
    onAlumnoClick: (Int) -> Unit,
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Alumnos") }) },
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            when (estado) {
                EstadoAlumnos.Cargando -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
                is EstadoAlumnos.Contenido -> LazyColumn {
                    items(
                        items = estado.alumnos,
                        key = { it.id },
                    ) { alumno ->
                        AlumnoCard(
                            alumno = alumno,
                            onClick = { onAlumnoClick(alumno.id) },
                        )
                    }
                }
                is EstadoAlumnos.Error -> ErrorContent(
                    mensaje = estado.mensaje,
                    onReintentar = onReintentar,
                )
            }
        }
    }
}
```

Este diseño facilita las previews y las pruebas: `AlumnosScreen` no necesita Android ni red para comprobar la representación de cada estado.

---

## 21. Prácticas guiadas

### Práctica 1. Presentación con `Column`, `Row` y `Box`

Construye una tarjeta de presentación con:

- Imagen de avatar.
- Nombre y curso.
- Botón para editar.
- Fondo y esquinas redondeadas.
- Un texto superpuesto sobre una imagen mediante `Box`.

**Restricciones:** no usar coordenadas absolutas ni componentes XML.

**Entrega:** preview en tema claro y oscuro, código dividido en al menos tres composables.

### Práctica 2. Calculadora con Compose

Recrea la calculadora planteada en los PDF utilizando una `Column` para la pantalla y varias `Row` para la botonera.

Requisitos:

- Números del 0 al 9.
- Operaciones suma, resta, multiplicación y división.
- Botón de borrar.
- Resultado visible en la parte superior.
- Distribución proporcional usando `weight`.
- Estado elevado a un composable de pantalla.
- Evitar división por cero con un mensaje claro.

Modelo mínimo:

```kotlin
data class CalculadoraState(
    val pantalla: String = "0",
    val operando: Double? = null,
    val operador: Operador? = null,
)

enum class Operador(val simbolo: String) {
    SUMA("+"),
    RESTA("-"),
    MULTIPLICACION("×"),
    DIVISION("÷"),
}
```

No se debe evaluar una expresión arbitraria con `eval` ni ejecutar texto como código. La operación debe controlarse con tipos y lógica propia.

### Práctica 3. Formulario de inscripción

Diseña un formulario con:

- Nombre.
- Correo.
- Contraseña.
- Curso mediante menú desplegable.
- Módulos mediante `Checkbox`.
- Modalidad mediante `RadioButton`.
- Aceptación de condiciones.
- Botón de envío.
- Estado de carga y mensaje de resultado.

Casos que deben probarse:

1. Envío vacío.
2. Correo inválido.
3. Contraseña corta.
4. Condiciones no aceptadas.
5. Envío correcto.
6. Rotación durante la edición.

### Práctica 4. Lista de tarjetas

Crea una lista de productos o alumnos con `LazyColumn`:

- Estado vacío.
- Estado de carga.
- Estado de error con reintento.
- Claves estables.
- Acción de favorito.
- Diálogo de confirmación para eliminar.
- `Snackbar` después de guardar.

### Práctica 5. Animación de visibilidad

Crea un botón “Mostrar detalles” que expanda y contraiga una tarjeta. Debe utilizar `AnimatedVisibility` o `animateContentSize`, mantener la información en estado y tener una descripción accesible.

### Práctica 6. Interfaz adaptable

Presenta los mismos datos en:

- Una columna para ancho compacto.
- Dos paneles para ancho amplio.
- Un grid adaptativo para una galería.

Prueba el emulador en orientación vertical y horizontal y con una fuente del sistema ampliada.

---

## 22. Proyecto de unidad

Desarrolla una aplicación de gestión de módulos del curso.

### Funcionalidades mínimas

1. Pantalla de inicio con resumen.
2. Lista de módulos en tarjetas.
3. Filtro por texto.
4. Selección de módulos favoritos.
5. Pantalla de detalle.
6. Formulario para añadir una nota.
7. Validación de la nota entre 0 y 10.
8. Estados de carga, error y vacío.
9. Tema claro y oscuro.
10. Navegación entre inicio y detalle.
11. Diseño usable en teléfono y tablet.
12. Al menos cinco tests de UI Compose.

### Criterios de evaluación sugeridos

| Criterio | Peso |
|---|---:|
| Uso correcto de composables y Kotlin | 20 % |
| Estado y flujo unidireccional | 20 % |
| Material 3 y coherencia visual | 15 % |
| Entrada, validación y feedback | 15 % |
| Listas, navegación y estados de carga | 15 % |
| Accesibilidad y adaptación | 10 % |
| Pruebas, limpieza y documentación | 5 % |

### Checklist de entrega

- [ ] No hay layouts XML ni acceso a `View` tradicional.
- [ ] Los componentes reciben datos y callbacks.
- [ ] No hay trabajo costoso en el cuerpo de un composable.
- [ ] Las listas utilizan claves estables.
- [ ] Los botones tienen textos o descripciones claras.
- [ ] Se contemplan carga, error y contenido.
- [ ] La interfaz funciona con tema oscuro.
- [ ] Se ha probado una fuente grande.
- [ ] Las previews muestran los estados importantes.
- [ ] Los tests comprueban comportamiento, no detalles internos de implementación.

---

## 23. Errores frecuentes

### “Cambio una variable y la pantalla no se actualiza”

La variable no es estado observable:

```kotlin
// Incorrecto
var contador = 0
```

Usa:

```kotlin
var contador by remember { mutableIntStateOf(0) }
```

o eleva el estado a un propietario superior.

### “La lista pierde el contenido de un campo”

Faltan claves estables o el estado está colocado fuera del elemento correcto:

```kotlin
items(alumnos, key = { it.id }) { alumno ->
    // estado del elemento
}
```

### “La petición se ejecuta muchas veces”

No ejecutes una petición directamente en el cuerpo del composable. Usa un `ViewModel` o un efecto con una clave adecuada.

### “La interfaz se corta”

Un `Column` normal no desplaza automáticamente su contenido. Para contenido largo, utiliza `LazyColumn` o `verticalScroll`.

### “El botón ocupa toda la fila”

Revisa dónde has puesto `fillMaxWidth` y el orden de los modificadores. El tamaño es consecuencia del padre y de la cadena de modificadores.

### “La pantalla tiene demasiados colores”

Centraliza la apariencia en `MaterialTheme`. Usa roles semánticos como `primary`, `surface`, `error` y sus colores `on...`.

### “He usado `Toast` para todos los errores”

Los errores relacionados con un campo deben aparecer junto al campo. Para eventos globales, usa `Snackbar`. Reserva diálogos para decisiones que requieren atención.

### “Un icono no funciona con TalkBack”

Añade `contentDescription` o usa `null` solo si el icono es puramente decorativo. El control que contiene la acción debe tener una descripción.

---

## 24. Comprobación de conocimientos

1. ¿Qué significa que Compose sea declarativo?
2. ¿Qué provoca una recomposición?
3. ¿Qué diferencia hay entre `remember` y `rememberSaveable`?
4. ¿Cuándo usarías `Column`, `Row` y `Box`?
5. ¿Qué sustituye a `layout_weight`?
6. ¿Por qué `LazyColumn` es preferible a una `Column` para listas largas?
7. ¿Qué es elevar el estado?
8. ¿Qué diferencia hay entre un `Checkbox`, un `RadioButton` y un `Switch`?
9. ¿Por qué no se debe iniciar una petición de red en el cuerpo de un composable?
10. ¿Qué problema resuelve `Snackbar`?
11. ¿Cómo se implementa un estado de carga?
12. ¿Qué información debe incluir la descripción de un icono accionable?
13. ¿Cómo harías una UI adaptable para una tablet?
14. ¿Qué ventajas tiene separar una pantalla conectada de un contenido puro?
15. ¿Qué son las claves estables en una lista?

### Actividad de razonamiento

Una pantalla muestra una lista vacía mientras carga, desaparece al terminar la petición y enseña un error si falla. Explica qué estado representarías y dibuja el `when` que decide el contenido. Después, indica qué código iría en un `ViewModel` y qué código permanecería en el composable.

---

## 25. Documentación y videotutoriales

### Documentación oficial

- [Jetpack Compose: documentación principal](https://developer.android.com/develop/ui/compose)
- [Introducción a Jetpack Compose](https://developer.android.com/develop/ui/compose/documentation)
- [Tutorial oficial de Jetpack Compose](https://developer.android.com/develop/ui/compose/tutorial)
- [Curso oficial Android Basics with Compose](https://developer.android.com/courses/android-basics-compose/course)
- [Ruta de aprendizaje de Compose](https://developer.android.com/courses/pathways/compose)
- [Layouts en Compose](https://developer.android.com/develop/ui/compose/layouts)
- [Estado en Compose](https://developer.android.com/develop/ui/compose/state)
- [Gestión de estado y eventos](https://developer.android.com/develop/ui/compose/state-hoisting)
- [Listas y grids](https://developer.android.com/develop/ui/compose/lists)
- [Material 3 para Compose](https://developer.android.com/develop/ui/compose/designsystems/material3)
- [Componentes Material 3](https://developer.android.com/develop/ui/compose/components)
- [Animaciones](https://developer.android.com/develop/ui/compose/animation/introduction)
- [Accesibilidad en Compose](https://developer.android.com/develop/ui/compose/accessibility)
- [Navegación con Compose](https://developer.android.com/develop/ui/compose/navigation)
- [Pruebas de UI en Compose](https://developer.android.com/develop/ui/compose/testing)
- [Adaptación a distintos tamaños de ventana](https://developer.android.com/develop/ui/compose/layouts/adaptive)
- [Referencia de la API Compose](https://developer.android.com/reference/kotlin/androidx/compose)
- [Kotlin: documentación oficial](https://kotlinlang.org/docs/home.html)

### Videotutoriales y vídeos recomendados

Los siguientes enlaces apuntan a vídeos o listas concretas de YouTube. Se recomienda revisar la fecha y las versiones de Android Studio antes de seguir una instalación, ya que las APIs evolucionan.

- [Jetpack Compose Crash Course - Philipp Lackner](https://www.youtube.com/watch?v=6_wK_Ud8--0): recorrido práctico por composables, layouts, estado y Material.
- [Jetpack Compose Tutorial for Beginners - Coding in Flow](https://www.youtube.com/watch?v=U5BwfqBpiWU): construcción guiada de una interfaz Compose desde cero.
- [Jetpack Compose: Talks and Code-alongs - Android Developers](https://www.youtube.com/playlist?list=PLWz5rJ2EKKc_5xkQgCf0YkOMIcbnDGgZm): lista oficial sobre estado, layouts, Material, accesibilidad y testing.
- [Android Developers en español](https://www.youtube.com/@AndroidDevelopersES): canal oficial con contenido y novedades para la comunidad hispanohablante.
- [Jetpack Compose playlist - Philipp Lackner](https://www.youtube.com/playlist?list=PLQkwcJG4YTCQ6wLhZ6YqN7uLhJ6u4n8mR): colección práctica de Kotlin y Compose.
- [Android Basics with Compose - curso en vídeo de Android Developers](https://www.youtube.com/playlist?list=PLWz5rJ2EKKc9Ty3Zl1hvMVUsXfkn93NRk): vídeos complementarios al itinerario oficial.

### Cómo utilizar los vídeos en clase

1. Ver únicamente el fragmento indicado por el profesor.
2. Pausar antes de cada cambio de estado y predecir qué se recompondrá.
3. Reescribir el ejemplo sin copiar nombres ni colores.
4. Añadir un estado de error y una preview de tema oscuro.
5. Explicar qué parte del ejemplo pertenece a UI y qué parte pertenece al estado o al dominio.

> La referencia principal siempre debe ser la documentación oficial. Un vídeo puede utilizar APIs antiguas; si existe una diferencia, se debe seguir la API documentada y estable del proyecto.