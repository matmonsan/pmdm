---
title: Extra UD2. Interfaz de usuario con XML
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al inicio](index.md)

> **Material extra.** Este documento explica los contenidos de la [UD2](u2_interfaz.md) con el sistema de vistas clásico de Android: layouts en XML y código Kotlin que los modifica. No se pide en el proyecto, que se hace con Jetpack Compose. Sirve para entender aplicaciones y documentación anteriores a Compose, que son la mayoría de las que encontrarás en internet.

## Índice

0. [Resumen de términos](#0-resumen-de-términos)
1. [Cómo es una pantalla con XML](#1-cómo-es-una-pantalla-con-xml)
2. [Preparación del proyecto](#2-preparación-del-proyecto)
3. [Escribir un layout](#3-escribir-un-layout)
4. [Medidas, márgenes y relleno](#4-medidas-márgenes-y-relleno)
5. [Layouts: LinearLayout, FrameLayout y ConstraintLayout](#5-layouts-linearlayout-framelayout-y-constraintlayout)
6. [Texto, imágenes e iconos](#6-texto-imágenes-e-iconos)
7. [Acceder a las vistas desde Kotlin: View Binding](#7-acceder-a-las-vistas-desde-kotlin-view-binding)
8. [Botones y eventos](#8-botones-y-eventos)
9. [Entrada de texto](#9-entrada-de-texto)
10. [Controles de selección](#10-controles-de-selección)
11. [Tarjetas y tablas](#11-tarjetas-y-tablas)
12. [Temas, colores y modo oscuro](#12-temas-colores-y-modo-oscuro)
13. [Estado y giro de pantalla](#13-estado-y-giro-de-pantalla)
14. [Varias pantallas con Activities](#14-varias-pantallas-con-activities)
15. [Visibilidad y progreso](#15-visibilidad-y-progreso)
16. [Equivalencias con Compose](#16-equivalencias-con-compose)

---

## 0. Resumen de términos

| Término | Qué significa |
|---|---|
| Vista (`View`) | Cada elemento de la pantalla: un texto, un botón, una imagen. Es un objeto que existe mientras se ve la pantalla. |
| Grupo de vistas (`ViewGroup`) | Vista que contiene otras y decide dónde se colocan. Los layouts son grupos de vistas. |
| Layout XML | Archivo de `res/layout` que describe las vistas de una pantalla y cómo se colocan. |
| Atributo | Propiedad de una vista escrita en el XML: `android:text="Hola"`. |
| `id` | Nombre de una vista para encontrarla desde Kotlin: `android:id="@+id/btnEntrar"`. |
| Recurso | Valor guardado en `res` (texto, color, medida, imagen) y usado con `@string/...`, `@color/...`. |
| `setContentView` | Función de la `Activity` que dibuja un layout como pantalla. |
| View Binding | Clase generada a partir de un layout que da acceso a sus vistas por su `id`. |
| `setOnClickListener` | Función que indica qué hacer al pulsar una vista. |
| `match_parent` / `wrap_content` | Medidas: ocupar todo el espacio del padre / ocupar lo que necesite el contenido. |
| `ConstraintLayout` | Layout que coloca cada vista con restricciones respecto a otras vistas o al padre. |
| Tema (`themes.xml`) | Estilo de toda la aplicación: colores, tipografía y forma de los componentes. |
| `Intent` | Objeto que pide a Android abrir otra `Activity`, con datos opcionales (*extras*). |

---

## 1. Cómo es una pantalla con XML

En Compose, una pantalla es una función que describe lo que se ve a partir de unos datos; cuando los datos cambian, la función se vuelve a ejecutar y la pantalla se redibuja.

Con el sistema de vistas, una pantalla se reparte en dos archivos:

| Archivo | Qué contiene |
|---|---|
| `res/layout/activity_main.xml` | Qué vistas hay y cómo se colocan, escrito en XML |
| `MainActivity.kt` | El código que carga ese layout, busca sus vistas y las modifica cuando ocurre algo |

Al abrir la pantalla, Android lee el XML y crea un objeto por cada vista: un `TextView`, un `Button`... Esos objetos existen mientras se ve la pantalla, y el código Kotlin los cambia uno a uno:

```kotlin
binding.txtSaludo.text = "Hola, Ana"      // cambia el texto de una vista
binding.btnEntrar.isEnabled = false        // desactiva un botón
```

Esta es la diferencia principal con Compose. En Compose se cambia un dato y Compose redibuja lo que depende de él. Con vistas, el código tiene que cambiar cada vista afectada: si el nombre aparece en tres sitios, hay que actualizar los tres.

---

## 2. Preparación del proyecto

### 2.1 Crear el proyecto

En Android Studio: **File → New → New Project...** y elige la plantilla **Empty Views Activity** (no **Empty Activity**, que es la de Compose). Lenguaje: Kotlin.

La plantilla crea dos archivos importantes:

```text
app/src/main/
├── java/com/ejemplo/vistas/MainActivity.kt
└── res/layout/activity_main.xml
```

`MainActivity.kt` tiene este contenido:

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

`setContentView(R.layout.activity_main)` le dice a la `Activity` que su pantalla es el layout `activity_main.xml`. `R.layout.activity_main` es el identificador que Android genera para ese archivo: cada archivo de `res` tiene uno en la clase `R`.

La `Activity` hereda de `AppCompatActivity`, que añade compatibilidad con versiones antiguas de Android y la barra superior del tema.

### 2.2 El editor de layouts

Al abrir `activity_main.xml`, arriba a la derecha hay tres botones:

| Vista | Qué muestra |
|---|---|
| **Code** | Solo el XML |
| **Split** | El XML y, al lado, cómo queda la pantalla |
| **Design** | Un editor visual donde se arrastran vistas desde la paleta |

En este documento se escribe el XML directamente, con la vista **Split** para ver el resultado. El editor visual escribe el mismo XML, pero añade atributos que a veces no hacen falta.

---

## 3. Escribir un layout

Un layout XML es un árbol de elementos. Cada elemento es una vista, y los elementos que contienen otros son grupos de vistas:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <TextView
        android:id="@+id/txtTitulo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/titulo"
        android:textSize="24sp" />

    <Button
        android:id="@+id/btnEntrar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/entrar" />

</LinearLayout>
```

- **El elemento raíz** (`LinearLayout`) lleva `xmlns:android="..."`, que declara el prefijo `android:` usado en todos los atributos. Solo se escribe una vez, en la raíz.
- **`android:layout_width` y `android:layout_height`** son obligatorios en todas las vistas. Pueden valer `match_parent` (ocupar todo lo que deja el padre), `wrap_content` (ocupar lo que necesite el contenido) o una medida fija (`120dp`).
- **`android:id="@+id/txtTitulo"`** da un nombre a la vista. El `+` indica que el nombre se crea aquí. Sin `id` no se puede acceder a la vista desde Kotlin.
- **Una vista sin hijos se cierra con `/>`**; un grupo de vistas tiene etiqueta de apertura y de cierre (`<LinearLayout>` ... `</LinearLayout>`).

### 3.1 Los textos en recursos

`android:text="@string/titulo"` no escribe el texto directamente: lo busca en `res/values/strings.xml`:

```xml
<resources>
    <string name="app_name">Vistas</string>
    <string name="titulo">Bienvenido</string>
    <string name="entrar">Entrar</string>
</resources>
```

Se puede escribir `android:text="Bienvenido"`, pero Android Studio lo marca con un aviso. Con los textos en `strings.xml`, traducir la aplicación consiste en crear otro archivo `values-en/strings.xml` con los mismos nombres; los layouts no cambian.

Lo mismo se hace con los colores (`res/values/colors.xml`, usados con `@color/nombre`) y las medidas que se repiten (`res/values/dimens.xml`, con `@dimen/nombre`).

---

## 4. Medidas, márgenes y relleno

Las unidades son las mismas que en Compose:

| Unidad | Para qué |
|---|---|
| `dp` | Tamaños y espacios. Ocupa lo mismo en todas las pantallas, sea cual sea su densidad |
| `sp` | Tamaño de texto. Como `dp`, pero también crece si el usuario aumenta el tamaño de letra del sistema |
| `px` | Píxeles reales. No se usa, porque cambia de tamaño según la pantalla |

Para los espacios hay dos atributos:

```xml
<TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_margin="16dp"
    android:padding="8dp"
    android:background="@color/amarillo"
    android:text="Con margen y relleno" />
```

- **`android:layout_margin`** es el espacio fuera de la vista, entre su borde y lo que la rodea. El fondo no lo cubre.
- **`android:padding`** es el espacio dentro de la vista, entre su borde y su contenido. El fondo sí lo cubre.

Se pueden indicar por lados: `layout_marginTop`, `layout_marginStart`, `paddingHorizontal`, `paddingBottom`... Se usa `Start` y `End` en lugar de `Left` y `Right`, para que en idiomas que se escriben de derecha a izquierda se inviertan solos.

En Compose no hay margen: se consigue con un `padding` antes del `background` en la cadena de modificadores (UD2, apartado 6.2). En XML son dos atributos separados y el orden en que se escriben no importa.

---

## 5. Layouts: LinearLayout, FrameLayout y ConstraintLayout

### 5.1 LinearLayout

`LinearLayout` coloca sus hijos uno detrás de otro. Con `android:orientation="vertical"` es el equivalente a `Column`; con `"horizontal"`, a `Row`:

```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:gravity="center_vertical">

    <TextView
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Nombre" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Editar" />

</LinearLayout>
```

- **`android:gravity`** coloca el contenido dentro del `LinearLayout`: `center_vertical` centra los hijos en vertical, como `verticalAlignment = Alignment.CenterVertically` en una `Row`.
- **`android:layout_gravity`**, en un hijo, coloca solo ese hijo.
- **`android:layout_weight="1"`** reparte el espacio sobrante, como `Modifier.weight(1f)`. La medida en la dirección del reparto se pone a `0dp`: el ancho lo decide el peso, no el contenido.

En el ejemplo, el botón ocupa lo que necesita y el texto ocupa todo el resto de la fila.

### 5.2 FrameLayout

`FrameLayout` coloca sus hijos unos encima de otros, como `Box`. Cada hijo se coloca con `android:layout_gravity`:

```xml
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="200dp">

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scaleType="centerCrop"
        android:src="@drawable/paisaje" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|start"
        android:layout_margin="12dp"
        android:text="Título sobre la imagen"
        android:textColor="@android:color/white" />

</FrameLayout>
```

El último hijo se dibuja encima. `bottom|start` combina dos posiciones con `|`: abajo y al principio.

### 5.3 ConstraintLayout

`ConstraintLayout` es el layout que crea la plantilla. Cada vista se coloca con restricciones: «mi lado izquierdo pegado al lado izquierdo del padre», «mi parte de arriba debajo de esa otra vista». Permite pantallas complejas sin anidar un layout dentro de otro.

```xml
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <TextView
        android:id="@+id/txtTitulo"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Bienvenido"
        android:textSize="24sp"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

    <Button
        android:id="@+id/btnEntrar"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="24dp"
        android:text="Entrar"
        app:layout_constraintTop_toBottomOf="@id/txtTitulo"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

- **`xmlns:app`** declara el prefijo `app:`, que usan los atributos de librerías (como las restricciones). Se escribe en la raíz, junto a `xmlns:android`.
- **`layout_constraintTop_toTopOf="parent"`**: la parte de arriba de la vista se pega a la parte de arriba del padre.
- **Restringir a la vez `Start` y `End` al padre** centra la vista en horizontal.
- **`layout_constraintTop_toBottomOf="@id/txtTitulo"`**: el botón empieza debajo del título. Aquí se escribe `@id` sin `+`, porque el `id` ya existe.
- **`android:layout_width="0dp"`** en un `ConstraintLayout` significa «ocupa todo el espacio entre tus restricciones»: el botón va de borde a borde.

Cada vista necesita al menos una restricción horizontal y una vertical. Si le falta alguna, en el editor se ve bien pero al ejecutar aparece en la esquina superior izquierda.

| Compose | XML |
|---|---|
| `Column` | `LinearLayout` con `orientation="vertical"` |
| `Row` | `LinearLayout` con `orientation="horizontal"` |
| `Box` | `FrameLayout` |
| `Modifier.weight(1f)` | `layout_weight="1"` con la medida a `0dp` |
| `Arrangement.spacedBy(8.dp)` | `layout_marginTop="8dp"` en cada hijo, o un `Space` entre ellos |
| Posiciones relativas entre elementos | `ConstraintLayout` |

---

## 6. Texto, imágenes e iconos

### 6.1 TextView

```xml
<TextView
    android:id="@+id/txtDescripcion"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@string/descripcion"
    android:textAppearance="?attr/textAppearanceBodyLarge"
    android:textStyle="bold"
    android:maxLines="2"
    android:ellipsize="end" />
```

- **`android:textAppearance="?attr/textAppearanceBodyLarge"`** aplica un estilo de texto del tema Material 3, como `MaterialTheme.typography.bodyLarge`. El `?attr/` indica que el valor se toma del tema actual.
- **`android:maxLines` y `android:ellipsize="end"`** cortan el texto a dos líneas y ponen puntos suspensivos al final, como `maxLines` y `overflow = TextOverflow.Ellipsis`.

### 6.2 ImageView

Las imágenes se guardan en `res/drawable`, igual que en Compose:

```xml
<ImageView
    android:id="@+id/imgFoto"
    android:layout_width="120dp"
    android:layout_height="120dp"
    android:src="@drawable/foto_perfil"
    android:scaleType="centerCrop"
    android:contentDescription="@string/foto_de_perfil" />
```

- **`android:scaleType`** es el equivalente a `contentScale`: `centerCrop` llena el espacio recortando lo que sobra (`ContentScale.Crop`); `fitCenter` muestra la imagen entera (`ContentScale.Fit`).
- **`android:contentDescription`** es lo que lee el lector de pantalla. Si la imagen es decorativa, se pone `@null`.

### 6.3 Iconos

En XML los iconos de Material no vienen en una librería como `Icons.Default`: se añaden al proyecto como imágenes vectoriales. Clic derecho sobre `res/drawable` → **New → Vector Asset** → **Clip Art**, elige el icono y ponle nombre (`ic_casa`). Android Studio crea `res/drawable/ic_casa.xml`, que se usa como cualquier imagen:

```xml
<ImageView
    android:layout_width="24dp"
    android:layout_height="24dp"
    android:src="@drawable/ic_casa"
    app:tint="?attr/colorPrimary"
    android:contentDescription="@string/inicio" />
```

`app:tint` pinta el icono de un color. `?attr/colorPrimary` es el color primario del tema.

---

## 7. Acceder a las vistas desde Kotlin: View Binding

Para cambiar una vista desde Kotlin hay que obtener su objeto. La forma recomendada es View Binding: Android Studio genera, por cada layout, una clase con una propiedad por cada vista que tiene `id`.

### 7.1 Activarlo

En `build.gradle.kts (Module :app)`, dentro del bloque `android`, añade:

```kotlin
android {
    // ... lo que ya había
    buildFeatures {
        viewBinding = true
    }
}
```

Pulsa **Sync Now**. A partir de ahora, `activity_main.xml` genera la clase `ActivityMainBinding`: el nombre del archivo en formato `CamelCase` y terminado en `Binding`.

### 7.2 Usarlo en la Activity

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtTitulo.text = "Bienvenida, Ana"
    }
}
```

- **`ActivityMainBinding.inflate(layoutInflater)`** lee el XML y crea los objetos de todas las vistas. *Inflar* es el nombre que se da a convertir un layout XML en objetos.
- **`setContentView(binding.root)`** muestra la vista raíz del layout. Sustituye a `setContentView(R.layout.activity_main)`.
- **`binding.txtTitulo`** es el `TextView` con `android:id="@+id/txtTitulo"`. Los `id` con guiones bajos se convierten a `camelCase`: `txt_titulo` sería `binding.txtTitulo`.
- **`lateinit var`** porque el `binding` no se puede crear en la declaración: necesita `layoutInflater`, que solo está disponible desde `onCreate`.

> **`findViewById`.** En código antiguo verás `val txt = findViewById<TextView>(R.id.txtTitulo)`. Hace lo mismo que View Binding, pero si el `id` no existe en el layout o el tipo no coincide, la aplicación se cierra al ejecutar. Con View Binding, ese error aparece al compilar.

---

## 8. Botones y eventos

```xml
<com.google.android.material.button.MaterialButton
    android:id="@+id/btnContar"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Contar"
    app:icon="@drawable/ic_mas" />
```

Con un tema Material 3, un `<Button>` normal ya se dibuja como `MaterialButton`. Se escribe el nombre completo cuando se quieren sus atributos propios, como `app:icon`. Los estilos de los otros tipos de botón se aplican con `style`:

| Compose | XML |
|---|---|
| `Button` | `<Button>` |
| `OutlinedButton` | `<Button style="@style/Widget.Material3.Button.OutlinedButton">` |
| `TextButton` | `<Button style="@style/Widget.Material3.Button.TextButton">` |
| `IconButton` | `<ImageButton>` con `android:background="?attr/selectableItemBackgroundBorderless"` |

En Kotlin, el evento se programa con `setOnClickListener`:

```kotlin
var contador = 0

binding.btnContar.setOnClickListener {
    contador++
    binding.txtContador.text = "Pulsado $contador veces"
}
```

Dentro de las llaves va lo que se hace al pulsar. A diferencia de Compose, cambiar `contador` no actualiza nada por sí solo: la línea `binding.txtContador.text = ...` es necesaria, porque es la que cambia la vista.

Para desactivar un botón: `binding.btnContar.isEnabled = false`.

---

## 9. Entrada de texto

El campo de texto de Material se escribe con dos vistas: `TextInputLayout` (la caja con la etiqueta, el error y el contador) y, dentro, `TextInputEditText` (donde se escribe):

```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/tilEmail"
    style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Correo electrónico">

    <com.google.android.material.textfield.TextInputEditText
        android:id="@+id/etEmail"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="textEmailAddress" />

</com.google.android.material.textfield.TextInputLayout>
```

- **`style="...OutlinedBox"`** dibuja el campo con borde, como `OutlinedTextField`. Sin estilo, se dibuja relleno, como `TextField`.
- **`android:hint`** va en el `TextInputLayout`: es la etiqueta (`label`).
- **`android:inputType`** elige el teclado y el comportamiento: `textEmailAddress`, `textPassword` (puntos en lugar de letras), `number`, `textMultiLine`...

Para una contraseña con el icono de mostrarla, se añade al `TextInputLayout` `app:endIconMode="password_toggle"`. Para un contador de caracteres, `app:counterEnabled="true"` y `app:counterMaxLength="200"`.

### 9.1 Leer el texto y validar

```kotlin
binding.btnEntrar.setOnClickListener {
    val email = binding.etEmail.text.toString().trim()
    if (!email.contains("@")) {
        binding.tilEmail.error = "Introduce un correo válido"
    } else {
        binding.tilEmail.error = null
        // continuar
    }
}
```

- **`binding.etEmail.text.toString()`** obtiene lo escrito. En el sistema de vistas, el campo guarda su propio texto: no hace falta un estado como en Compose.
- **`binding.tilEmail.error = "..."`** pone el campo en rojo con el mensaje debajo, como `isError` y `supportingText`. Con `null` se quita.

Para reaccionar a cada letra que se escribe (por ejemplo, para quitar el error en cuanto el usuario corrige):

```kotlin
binding.etEmail.doAfterTextChanged { texto ->
    if (texto.toString().contains("@")) binding.tilEmail.error = null
}
```

---

## 10. Controles de selección

### 10.1 CheckBox y Switch

```xml
<CheckBox
    android:id="@+id/chkTerminos"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Acepto las condiciones" />

<com.google.android.material.materialswitch.MaterialSwitch
    android:id="@+id/swNotificaciones"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Notificaciones" />
```

En los dos, el texto forma parte del control: pulsar el texto también lo marca. En Kotlin se lee `isChecked` y se reacciona a los cambios con `setOnCheckedChangeListener`:

```kotlin
binding.chkTerminos.setOnCheckedChangeListener { _, marcado ->
    binding.btnEntrar.isEnabled = marcado
}
```

El primer parámetro de la lambda es la propia vista; como no se usa, se escribe `_`.

### 10.2 RadioButton

Los `RadioButton` se agrupan en un `RadioGroup`, que se encarga de que solo uno esté marcado:

```xml
<RadioGroup
    android:id="@+id/rgTamano"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <RadioButton android:id="@+id/rbPequeno" android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="Pequeño" />
    <RadioButton android:id="@+id/rbMediano" android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="Mediano"
        android:checked="true" />
    <RadioButton android:id="@+id/rbGrande" android:layout_width="wrap_content"
        android:layout_height="wrap_content" android:text="Grande" />

</RadioGroup>
```

```kotlin
binding.rgTamano.setOnCheckedChangeListener { _, idMarcado ->
    val tamano = when (idMarcado) {
        R.id.rbPequeno -> "Pequeño"
        R.id.rbGrande -> "Grande"
        else -> "Mediano"
    }
    binding.txtResumen.text = "Tamaño: $tamano"
}
```

En Compose hacía falta un estado con la opción elegida y un `RadioButton` por opción con `selected = ...`. Aquí el `RadioGroup` guarda cuál está marcado y avisa con su `id`.

### 10.3 Slider

```xml
<com.google.android.material.slider.Slider
    android:id="@+id/sliderVolumen"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:valueFrom="0"
    android:valueTo="100"
    android:stepSize="1"
    android:value="50" />
```

```kotlin
binding.sliderVolumen.addOnChangeListener { _, valor, _ ->
    binding.txtVolumen.text = "Volumen: ${valor.toInt()}"
}
```

### 10.4 Menú desplegable

El desplegable de Material se hace con un `TextInputLayout` con estilo `ExposedDropdownMenu` y un `AutoCompleteTextView` dentro:

```xml
<com.google.android.material.textfield.TextInputLayout
    style="@style/Widget.Material3.TextInputLayout.OutlinedBox.ExposedDropdownMenu"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Provincia">

    <AutoCompleteTextView
        android:id="@+id/actProvincia"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:inputType="none" />

</com.google.android.material.textfield.TextInputLayout>
```

Las opciones se cargan desde Kotlin con un `ArrayAdapter`, que convierte una lista en filas del desplegable:

```kotlin
val provincias = listOf("Albacete", "Ciudad Real", "Cuenca", "Guadalajara", "Toledo")
val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, provincias)
binding.actProvincia.setAdapter(adapter)

binding.actProvincia.setOnItemClickListener { _, _, posicion, _ ->
    val elegida = provincias[posicion]
}
```

`android.R.layout.simple_list_item_1` es un layout de Android con un solo texto, que se usa para cada fila. `android:inputType="none"` impide escribir en el campo: solo se puede elegir.

---

## 11. Tarjetas y tablas

### 11.1 MaterialCardView

```xml
<com.google.android.material.card.MaterialCardView
    style="@style/Widget.Material3.CardView.Elevated"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="8dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Hotel Peñalta"
            android:textAppearance="?attr/textAppearanceTitleMedium" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Bogarra (Albacete)" />

    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

Los estilos `Widget.Material3.CardView.Elevated`, `.Filled` y `.Outlined` corresponden a `ElevatedCard`, `Card` y `OutlinedCard`. Una tarjeta solo admite un hijo directo; para varios elementos se pone un layout dentro.

Las listas con muchas tarjetas no se escriben a mano en el XML: se hacen con `RecyclerView`, que se explica en el [extra de la UD3](xml_ud3.md).

### 11.2 TableLayout

```xml
<TableLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:stretchColumns="1">

    <TableRow>
        <TextView android:text="Ciudad" android:textStyle="bold" android:padding="8dp" />
        <TextView android:text="Albacete" android:padding="8dp" />
    </TableRow>

    <TableRow>
        <TextView android:text="Teléfono" android:textStyle="bold" android:padding="8dp" />
        <TextView android:text="967 98 89 98" android:padding="8dp" />
    </TableRow>

</TableLayout>
```

Cada `TableRow` es una fila y cada vista dentro de ella, una celda. Dentro de un `TableLayout` no hace falta escribir `layout_width` ni `layout_height`. `android:stretchColumns="1"` hace que la segunda columna (se cuenta desde 0) ocupe el ancho que sobra.

---

## 12. Temas, colores y modo oscuro

### 12.1 Dónde está el tema

El tema está en `res/values/themes.xml`:

```xml
<resources>
    <style name="Theme.Vistas" parent="Theme.Material3.DayNight.NoActionBar">
        <item name="colorPrimary">@color/azul</item>
        <item name="colorOnPrimary">@color/blanco</item>
        <item name="colorPrimaryContainer">@color/azul_claro</item>
        <item name="colorSecondary">@color/verde</item>
    </style>
</resources>
```

- **`parent="Theme.Material3.DayNight..."`**: el tema hereda todos los valores de Material 3 y cambia solo los que se escriben dentro.
- **`DayNight`** hace que el tema tenga versión clara y oscura, según la configuración del móvil.
- **`NoActionBar`** quita la barra superior automática. Se usa cuando la pantalla pone su propia barra (`MaterialToolbar`, en el [extra de la UD4](xml_ud4.md)).
- **Cada `<item>`** asigna un color del esquema de Material 3: los mismos nombres que `MaterialTheme.colorScheme` (`primary`, `onPrimary`, `primaryContainer`...) con el prefijo `color`.

El tema se aplica a toda la aplicación en `AndroidManifest.xml`, con `android:theme="@style/Theme.Vistas"` en `<application>`.

Los colores se definen en `res/values/colors.xml`:

```xml
<resources>
    <color name="azul">#3F51B5</color>
    <color name="azul_claro">#C5CAE9</color>
    <color name="verde">#4CAF50</color>
    <color name="blanco">#FFFFFF</color>
</resources>
```

Para usar un color del tema en un layout, se escribe `?attr/colorPrimary`; para un color concreto, `@color/azul`. Lo recomendable es usar los del tema, para que cambien solos en modo oscuro.

### 12.2 Modo oscuro

Para el modo oscuro se crea la carpeta `res/values-night` con otro `themes.xml` que tenga los colores oscuros. Android elige `values-night` cuando el móvil está en modo oscuro y `values` en el resto de casos. En Android Studio, clic derecho en `res` → **New → Android Resource File**, tipo **Values**, y en los calificadores elige **Night Mode → Night**.

---

## 13. Estado y giro de pantalla

En Compose, el estado es una variable con `remember` o `rememberSaveable`, y la pantalla se redibuja cuando cambia. Con vistas, cada vista guarda su propio estado: un `EditText` guarda su texto, un `CheckBox` si está marcado. Por eso en los apartados anteriores no hacía falta ninguna variable de estado.

Al girar el móvil, Android destruye la `Activity` y crea otra. Las vistas que tienen `id` recuperan solas su estado (el texto escrito, la casilla marcada). Lo que no se recupera son las variables de la `Activity`, como el `contador` del apartado 8: vuelve a 0.

Para conservarlo se guarda en el `Bundle` del estado:

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var contador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contador = savedInstanceState?.getInt("contador") ?: 0
        mostrarContador()

        binding.btnContar.setOnClickListener {
            contador++
            mostrarContador()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("contador", contador)
    }

    private fun mostrarContador() {
        binding.txtContador.text = "Pulsado $contador veces"
    }
}
```

- **`onSaveInstanceState`** se ejecuta antes de destruir la `Activity`. Ahí se guarda el valor con una clave.
- **`savedInstanceState`** es `null` la primera vez que se abre la pantalla, y tiene el `Bundle` guardado cuando se recrea tras el giro.
- **`mostrarContador()`** se llama al crear la pantalla y en cada clic: con vistas, hay que actualizar la pantalla a mano cada vez que cambia el dato.

Es lo que hace `rememberSaveable` en Compose. Para estados más complejos se usa un ViewModel, que se ve en el [extra de la UD3](xml_ud3.md).

---

## 14. Varias pantallas con Activities

### 14.1 Crear la segunda pantalla

Clic derecho sobre el paquete → **New → Activity → Empty Views Activity**, con el nombre `DetalleActivity`. Android Studio crea `DetalleActivity.kt`, `res/layout/activity_detalle.xml` y la registra en `AndroidManifest.xml`:

```xml
<activity
    android:name=".DetalleActivity"
    android:exported="false" />
```

`android:exported="false"` indica que solo la puede abrir esta aplicación. La `Activity` principal tiene `exported="true"` y el `intent-filter` con `MAIN` y `LAUNCHER`, que la convierten en la que se abre desde el icono.

### 14.2 Abrirla y pasarle datos

Desde la primera pantalla, se crea un `Intent` con la `Activity` de destino y los datos como *extras*:

```kotlin
binding.btnVerDetalle.setOnClickListener {
    val intent = Intent(this, DetalleActivity::class.java)
    intent.putExtra("nombre", "Hotel Peñalta")
    intent.putExtra("estrellas", 4)
    startActivity(intent)
}
```

En `DetalleActivity` se leen en `onCreate`:

```kotlin
class DetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre") ?: ""
        val estrellas = intent.getIntExtra("estrellas", 0)
        binding.txtNombre.text = nombre
        binding.txtEstrellas.text = "$estrellas estrellas"

        binding.btnVolver.setOnClickListener { finish() }
    }
}
```

- **`intent`** dentro de la `Activity` es el `Intent` con el que se abrió.
- **`getIntExtra("estrellas", 0)`** necesita un valor por defecto para cuando el extra no existe.
- **`finish()`** cierra esta `Activity` y se vuelve a la anterior, igual que el botón Atrás.

Las claves (`"nombre"`, `"estrellas"`) se escriben igual en los dos archivos. Para no equivocarse, se suelen guardar en constantes.

---

## 15. Visibilidad y progreso

### 15.1 Mostrar y ocultar

Cada vista tiene `visibility`, con tres valores:

| Valor | Se ve | Ocupa su espacio |
|---|---|---|
| `View.VISIBLE` | Sí | Sí |
| `View.INVISIBLE` | No | Sí |
| `View.GONE` | No | No |

```kotlin
binding.txtError.visibility = View.GONE
binding.txtError.isVisible = true      // equivale a View.VISIBLE; con false, View.GONE
```

`isVisible` es una extensión de la librería `core-ktx` (incluida en el proyecto) que simplifica el caso más común. En Compose, ocultar algo es no llamar al composable (`if (mostrar) { ... }`).

### 15.2 Indicadores de progreso

```xml
<com.google.android.material.progressindicator.CircularProgressIndicator
    android:id="@+id/progreso"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:indeterminate="true"
    android:visibility="gone" />
```

`android:indeterminate="true"` dibuja la animación continua, sin porcentaje. `LinearProgressIndicator` es la versión en barra. Se muestran y ocultan con `isVisible`.

---

## 16. Equivalencias con Compose

| Compose (UD2) | Vistas y XML |
|---|---|
| Función `@Composable` | Layout XML + código en la `Activity` |
| `setContent { ... }` | `setContentView(binding.root)` |
| `Column` / `Row` / `Box` | `LinearLayout` vertical / horizontal / `FrameLayout` |
| `Modifier.padding` | `android:padding` y `android:layout_margin` |
| `Modifier.weight(1f)` | `android:layout_weight="1"` con medida `0dp` |
| `Text` | `TextView` |
| `Image` + `painterResource` | `ImageView` con `android:src="@drawable/..."` |
| `Icon(Icons.Default.Home)` | **Vector Asset** en `res/drawable` + `ImageView` |
| `Button(onClick = { ... })` | `Button` + `setOnClickListener { ... }` |
| `OutlinedTextField` | `TextInputLayout` (estilo `OutlinedBox`) + `TextInputEditText` |
| `isError` + `supportingText` | `textInputLayout.error = "..."` |
| `Checkbox`, `Switch`, `Slider` | `CheckBox`, `MaterialSwitch`, `Slider` |
| `RadioButton` + estado | `RadioGroup` + `RadioButton` |
| `ExposedDropdownMenuBox` | `TextInputLayout` (estilo `ExposedDropdownMenu`) + `AutoCompleteTextView` |
| `ElevatedCard` / `Card` / `OutlinedCard` | `MaterialCardView` con estilo `Elevated` / `Filled` / `Outlined` |
| `MaterialTheme` en `Theme.kt` | `themes.xml` y `colors.xml` |
| `remember { mutableStateOf(...) }` | Cada vista guarda su estado; variables de la `Activity` |
| `rememberSaveable` | `onSaveInstanceState` + `savedInstanceState` |
| `if (mostrar) { ... }` | `vista.isVisible = mostrar` |
| Otra `Activity` con `Intent` | Igual: `Intent` + `putExtra` + `startActivity` |
