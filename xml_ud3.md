---
title: Extra UD3. Listas, diálogos y MVVM con XML
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al inicio](index.md)

> **Material extra.** Este documento explica los contenidos de la [UD3](ud3.md) con el sistema de vistas clásico: layouts XML, `RecyclerView`, `LiveData` y diálogos creados desde código. No se pide en el proyecto, que se hace con Jetpack Compose. Antes conviene leer el [extra de la UD2](xml_ud2.md), donde se explican los layouts XML y View Binding.

## Índice

0. [Resumen de términos](#0-resumen-de-términos)
1. [El ejemplo](#1-el-ejemplo)
2. [Mensajes al usuario: Toast, Snackbar y AlertDialog](#2-mensajes-al-usuario-toast-snackbar-y-alertdialog)
3. [Listas con RecyclerView](#3-listas-con-recyclerview)
4. [Imágenes remotas con Coil](#4-imágenes-remotas-con-coil)
5. [Lista y detalle con dos Activities](#5-lista-y-detalle-con-dos-activities)
6. [MVVM con ViewModel y LiveData](#6-mvvm-con-viewmodel-y-livedata)
7. [Diálogos de alta, edición y borrado](#7-diálogos-de-alta-edición-y-borrado)
8. [Barra superior, botón flotante y Snackbar](#8-barra-superior-botón-flotante-y-snackbar)
9. [Equivalencias con Compose](#9-equivalencias-con-compose)

---

## 0. Resumen de términos

| Término | Qué significa |
|---|---|
| `RecyclerView` | Vista de lista que solo crea las filas visibles y las reutiliza al desplazarse. |
| Layout de fila | Archivo XML que describe cómo es una fila de la lista (`item_hotel.xml`). |
| `ViewHolder` | Objeto que guarda las vistas de una fila para no buscarlas cada vez. |
| `Adapter` | Clase que crea las filas y les pone los datos de cada elemento. |
| `ListAdapter` | `Adapter` que recibe la lista completa y calcula solo qué filas han cambiado. |
| `DiffUtil.ItemCallback` | Indica al `ListAdapter` cuándo dos elementos son el mismo y cuándo han cambiado. |
| `LayoutManager` | Decide cómo se colocan las filas: en vertical, en horizontal o en cuadrícula. |
| `LiveData` | Contenedor de un dato que avisa a la pantalla cuando cambia, solo mientras la pantalla está activa. |
| `observe` | Función que indica qué hacer en la pantalla cada vez que cambia un `LiveData`. |
| `by viewModels()` | Obtiene el ViewModel de una `Activity`, el mismo aunque se gire el móvil. |
| `MaterialAlertDialogBuilder` | Clase que construye un diálogo de Material desde el código. |
| `CoordinatorLayout` | Layout que coordina la barra superior, el botón flotante y el `Snackbar`. |

---

## 1. El ejemplo

Se construye la misma aplicación de hospedajes de la UD3: una lista de hoteles en la que se puede consultar el detalle, añadir, editar y borrar. Estos son los archivos:

```text
app/src/main/java/com/ejemplo/hospedajes/
├── Hotel.kt                  ← el modelo (igual que en la UD3)
├── HotelRepository.kt        ← el repositorio (igual que en la UD3)
├── HotelAdapter.kt           ← el adaptador de la lista
├── HospedajeUiState.kt
├── HospedajeViewModel.kt     ← ViewModel de la lista
├── DetalleViewModel.kt       ← ViewModel del detalle
├── DialogoHotel.kt           ← diálogo de alta y edición
├── MainActivity.kt           ← pantalla de la lista
└── DetalleActivity.kt        ← pantalla del detalle

app/src/main/res/layout/
├── activity_main.xml
├── activity_detalle.xml
├── item_hotel.xml            ← una fila de la lista
└── dialog_hotel.xml          ← el formulario del diálogo
```

`Hotel.kt` y `HotelRepository.kt` son exactamente los de la UD3 (apartados 3.2 y 6.2): el modelo y el repositorio no dependen de cómo se dibuje la pantalla.

Activa View Binding en `build.gradle.kts (Module :app)` (extra de la UD2, apartado 7.1) y añade estas dependencias:

```kotlin
dependencies {
    // ... las dependencias que ya había
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.activity:activity-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.0")
    implementation("io.coil-kt:coil:2.7.0")
}
```

Si Android Studio propone versiones más recientes, acéptalas. Añade también en `AndroidManifest.xml` el permiso `<uses-permission android:name="android.permission.INTERNET" />`, para descargar las imágenes.

---

## 2. Mensajes al usuario: Toast, Snackbar y AlertDialog

### 2.1 Toast

El `Toast` es igual que en Compose, porque no depende de cómo se dibuje la pantalla:

```kotlin
Toast.makeText(this, "Hotel guardado", Toast.LENGTH_SHORT).show()
```

El primer parámetro es el contexto: dentro de una `Activity`, `this`.

### 2.2 Snackbar

Con vistas, el `Snackbar` no necesita un `SnackbarHostState` ni un `Scaffold`: se crea desde cualquier vista de la pantalla y se muestra con `show()`:

```kotlin
Snackbar.make(binding.root, "Hotel borrado", Snackbar.LENGTH_SHORT)
    .setAction("Deshacer") { /* restaurar el hotel */ }
    .show()
```

- **El primer parámetro es una vista** de la pantalla. Android busca a partir de ella el sitio donde colocar el mensaje: si hay un `CoordinatorLayout` (apartado 8), lo usa para subir el botón flotante y que no quede tapado.
- **`setAction`** añade un botón al mensaje.

### 2.3 AlertDialog

Los diálogos se construyen con `MaterialAlertDialogBuilder`, que va añadiendo partes y al final los muestra:

```kotlin
MaterialAlertDialogBuilder(this)
    .setTitle("Borrar hotel")
    .setMessage("¿Seguro que quieres borrar «Hotel Peñalta»?")
    .setNegativeButton("Cancelar", null)
    .setPositiveButton("Borrar") { _, _ ->
        // borrar el hotel
    }
    .show()
```

- **`setNegativeButton("Cancelar", null)`**: con `null`, el botón solo cierra el diálogo.
- **`setPositiveButton("Borrar") { _, _ -> ... }`**: el código de las llaves se ejecuta al pulsar, y después el diálogo se cierra solo. Los dos parámetros de la lambda (el diálogo y el botón) no se usan.

La diferencia con Compose es que aquí el diálogo no depende de un estado: se crea y se muestra en el momento, con `show()`. En Compose, el diálogo aparece mientras un dato del estado lo indica (UD3, apartado 7.1).

---

## 3. Listas con RecyclerView

`RecyclerView` es la lista del sistema de vistas, el equivalente a `LazyColumn`. Como `LazyColumn`, solo crea las filas que se ven. La diferencia es que con `LazyColumn` basta con escribir qué se dibuja por cada elemento; con `RecyclerView` hay que escribir tres piezas:

| Pieza | Archivo | Qué hace |
|---|---|---|
| Layout de fila | `item_hotel.xml` | Cómo es una fila |
| `ViewHolder` | Dentro de `HotelAdapter.kt` | Guarda las vistas de una fila |
| `Adapter` | `HotelAdapter.kt` | Crea las filas y les pone los datos de cada hotel |

### 3.1 El layout de la fila

Crea `res/layout/item_hotel.xml` (clic derecho en `layout` → **New → Layout Resource File**):

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    style="@style/Widget.Material3.CardView.Elevated"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginHorizontal="16dp"
    android:layout_marginVertical="6dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="12dp"
        android:gravity="center_vertical">

        <ImageView
            android:id="@+id/imgHotel"
            android:layout_width="72dp"
            android:layout_height="72dp"
            android:scaleType="centerCrop"
            android:contentDescription="@null" />

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:layout_marginStart="12dp"
            android:orientation="vertical">

            <TextView
                android:id="@+id/txtNombre"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textAppearance="?attr/textAppearanceTitleMedium" />

            <TextView
                android:id="@+id/txtUbicacion"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textAppearance="?attr/textAppearanceBodyMedium" />

            <TextView
                android:id="@+id/txtTelefono"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textAppearance="?attr/textAppearanceBodySmall" />

        </LinearLayout>
    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

Es el mismo diseño que la tarjeta de la UD3 (apartado 3.3): imagen a la izquierda y tres textos a la derecha. La raíz tiene `layout_height="wrap_content"`: si fuera `match_parent`, cada fila ocuparía toda la pantalla.

Los textos no tienen `android:text`: los pone el adaptador con los datos de cada hotel.

### 3.2 El adaptador y el ViewHolder

Crea `HotelAdapter.kt`:

```kotlin
class HotelAdapter(
    private val onHotelClick: (Hotel) -> Unit
) : ListAdapter<Hotel, HotelAdapter.HotelViewHolder>(HotelDiff) {

    class HotelViewHolder(val binding: ItemHotelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelViewHolder {
        val binding = ItemHotelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HotelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HotelViewHolder, position: Int) {
        val hotel = getItem(position)
        holder.binding.txtNombre.text = hotel.name
        holder.binding.txtUbicacion.text = "${hotel.city} (${hotel.province})"
        holder.binding.txtTelefono.text = hotel.phone
        holder.binding.imgHotel.load(hotel.image)
        holder.binding.root.setOnClickListener { onHotelClick(hotel) }
    }

    object HotelDiff : DiffUtil.ItemCallback<Hotel>() {
        override fun areItemsTheSame(oldItem: Hotel, newItem: Hotel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Hotel, newItem: Hotel) = oldItem == newItem
    }
}
```

El código se lee por partes.

**El constructor** recibe `onHotelClick`, la lambda que se ejecuta al pulsar una fila. Es el mismo principio que en Compose: el adaptador avisa de qué hotel se ha pulsado y la `Activity` decide qué hacer.

**`ListAdapter<Hotel, HotelAdapter.HotelViewHolder>(HotelDiff)`**: el adaptador hereda de `ListAdapter`, que guarda la lista de hoteles. Los dos tipos indican qué elementos contiene y qué `ViewHolder` usa.

**`HotelViewHolder`** guarda el *binding* de una fila. `RecyclerView` crea unos pocos `ViewHolder` (los que caben en pantalla y alguno más) y los reutiliza: cuando una fila sale por arriba al desplazarse, su `ViewHolder` se usa para la fila que entra por abajo.

**`onCreateViewHolder`** se ejecuta cuando hace falta una fila nueva. Infla `item_hotel.xml` y crea el `ViewHolder`. El tercer parámetro de `inflate`, `false`, indica que la fila no se añade todavía a la lista: lo hará `RecyclerView`.

**`onBindViewHolder`** se ejecuta cada vez que una fila tiene que mostrar un hotel: al aparecer por primera vez y cada vez que se reutiliza. `getItem(position)` da el hotel de esa posición, y el código pone sus datos en las vistas. Por eso las filas no guardan datos: los recibe cada vez.

**`HotelDiff`** le dice al `ListAdapter` cómo comparar hoteles. Cuando llega una lista nueva, el `ListAdapter` la compara con la anterior y solo redibuja las filas que han cambiado, con animación:

- `areItemsTheSame`: ¿son el mismo hotel? Se compara el `id`, como la clave `key` de `LazyColumn` (UD3, apartado 3.4).
- `areContentsTheSame`: si es el mismo hotel, ¿ha cambiado algún dato? Como `Hotel` es una `data class`, `==` compara todos sus campos.

### 3.3 La lista en la pantalla

En `activity_main.xml`, la lista es una vista más:

```xml
<androidx.recyclerview.widget.RecyclerView
    android:id="@+id/rvHoteles"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingVertical="8dp"
    android:clipToPadding="false"
    app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager" />
```

- **`app:layoutManager`** indica cómo se colocan las filas. `LinearLayoutManager` las pone una debajo de otra, como `LazyColumn`. Para una cuadrícula se usa `GridLayoutManager` con `app:spanCount="2"`, como `LazyVerticalGrid`.
- **`android:clipToPadding="false"`** deja que las filas se desplacen por el relleno de arriba y abajo, en lugar de cortarse en él. Es lo que hace `contentPadding` en `LazyColumn`.

En la `Activity`, se crea el adaptador, se asigna a la lista y se le pasa la lista de hoteles:

```kotlin
private val adapter = HotelAdapter(onHotelClick = { hotel -> abrirDetalle(hotel) })

override fun onCreate(savedInstanceState: Bundle?) {
    // ...
    binding.rvHoteles.adapter = adapter
    adapter.submitList(HotelRepository.getHotels())
}
```

`submitList` entrega la lista al adaptador. Cada vez que se llama con una lista nueva, el `ListAdapter` compara y actualiza las filas que han cambiado. En el apartado 6 la lista llegará del ViewModel en lugar del repositorio.

> **`notifyDataSetChanged`.** En código antiguo verás adaptadores que heredan de `RecyclerView.Adapter` y, tras cambiar la lista, llaman a `adapter.notifyDataSetChanged()`. Funciona, pero redibuja todas las filas y sin animación. `ListAdapter` hace la comparación por ti.

---

## 4. Imágenes remotas con Coil

Coil también funciona con vistas. Con la dependencia `io.coil-kt:coil` (sin `-compose`), cada `ImageView` tiene la función `load`:

```kotlin
holder.binding.imgHotel.load(hotel.image) {
    crossfade(true)
    placeholder(R.drawable.ic_imagen)
    error(R.drawable.ic_imagen_rota)
}
```

- **`load(url)`** descarga la imagen sin bloquear la pantalla y la muestra al terminar. El `import` es `coil.load`.
- **`crossfade(true)`** la hace aparecer con un fundido.
- **`placeholder`** se muestra mientras se descarga, y **`error`** si no se puede descargar. Son imágenes de `res/drawable`.

Es lo que hace `AsyncImage` en Compose (UD3, apartado 4). En el adaptador hay que llamar a `load` en cada `onBindViewHolder`: al reutilizar una fila, Coil cancela la descarga anterior y empieza la del hotel nuevo.

---

## 5. Lista y detalle con dos Activities

En la UD3 (apartado 5), la lista y el detalle eran dos composables en la misma `Activity`, y una variable de estado decidía cuál se veía. Con vistas, lo habitual es que cada pantalla sea una `Activity` y se pase de una a otra con un `Intent` (extra de la UD2, apartado 14). Al detalle solo se le pasa el `id` del hotel:

```kotlin
private fun abrirDetalle(hotel: Hotel) {
    val intent = Intent(this, DetalleActivity::class.java)
    intent.putExtra(DetalleActivity.EXTRA_HOTEL_ID, hotel.id)
    startActivity(intent)
}
```

La clave del extra se guarda como constante en `DetalleActivity`:

```kotlin
class DetalleActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_HOTEL_ID = "hotelId"
    }

    // ...
}
```

`companion object` contiene lo que pertenece a la clase y no a cada objeto: se usa como `DetalleActivity.EXTRA_HOTEL_ID` sin crear ninguna `Activity`. Así las dos pantallas usan la misma clave escrita en un solo sitio.

¿Por qué el `id` y no el hotel entero? Por lo mismo que en la UD4 (apartado 4.5): el detalle busca el hotel en el repositorio y siempre muestra sus datos actuales, aunque se haya editado.

El botón Atrás funciona sin código: cierra `DetalleActivity` y vuelve a la lista. No hace falta el `BackHandler` de la UD3.

---

## 6. MVVM con ViewModel y LiveData

La arquitectura es la misma de la UD3 (apartado 6): el repositorio guarda los datos, el ViewModel guarda el estado y la pantalla lo dibuja y avisa de lo que hace el usuario. Cambian dos cosas: cómo el ViewModel ofrece el estado y cómo la pantalla se entera de que ha cambiado.

### 6.1 El estado

```kotlin
data class HospedajeUiState(
    val hotelList: List<Hotel> = emptyList(),
    val userMessage: String? = null
)
```

Es el `UiState` de la UD3 sin los datos de los diálogos (`hotelToEdit`, `hotelToDelete`, `isAddDialogOpen`): con vistas, los diálogos se crean y se muestran en el momento desde la `Activity` (apartado 7), no a partir del estado.

### 6.2 El ViewModel con LiveData

```kotlin
class HospedajeViewModel : ViewModel() {

    private val _uiState = MutableLiveData(HospedajeUiState())
    val uiState: LiveData<HospedajeUiState> = _uiState

    init {
        recargar()
    }

    fun recargar() {
        _uiState.value = _uiState.value?.copy(hotelList = HotelRepository.getHotels())
    }

    fun onInsertHotel(name: String, city: String, province: String, phone: String, image: String) {
        val nuevo = Hotel(name = name, city = city, province = province, phone = phone, image = image)
        HotelRepository.addHotel(nuevo)
        _uiState.value = _uiState.value?.copy(
            hotelList = HotelRepository.getHotels(),
            userMessage = "Hospedaje '${nuevo.name}' añadido correctamente"
        )
    }

    fun onUserMessageShown() {
        _uiState.value = _uiState.value?.copy(userMessage = null)
    }
}
```

- **`MutableLiveData` y `LiveData`**: la misma idea que `MutableStateFlow` y `StateFlow` (UD5, apartado 4). `_uiState` es privado y se puede cambiar; `uiState` es público y de solo lectura.
- **`_uiState.value`** es el valor actual. Es de tipo `HospedajeUiState?` (puede ser `null`), por eso se escribe `_uiState.value?.copy(...)`.
- **Asignar un valor nuevo a `.value`** avisa a la pantalla. Igual que en Compose, hay que asignar un objeto nuevo con `copy`: si se modificara el objeto por dentro, `LiveData` no avisaría.
- **`recargar()`** es pública porque la pantalla la llamará al volver del detalle (apartado 6.4).

### 6.3 Observar el estado desde la Activity

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: HospedajeViewModel by viewModels()
    private val adapter = HotelAdapter(onHotelClick = { hotel -> abrirDetalle(hotel) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.rvHoteles.adapter = adapter
        binding.fabAnadir.setOnClickListener { mostrarDialogoAlta() }

        viewModel.uiState.observe(this) { estado ->
            adapter.submitList(estado.hotelList)
            binding.txtVacio.isVisible = estado.hotelList.isEmpty()

            estado.userMessage?.let { mensaje ->
                Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_SHORT).show()
                viewModel.onUserMessageShown()
            }
        }
    }

    // ... abrirDetalle (apartado 5) y mostrarDialogoAlta (apartado 7)
}
```

- **`by viewModels()`** obtiene el ViewModel de la `Activity`, de la librería `activity-ktx`. Al girar el móvil, la `Activity` nueva recibe el mismo ViewModel, igual que con `viewModel()` en Compose. No se puede crear con `HospedajeViewModel()`, porque entonces se crearía uno nuevo en cada giro.
- **`viewModel.uiState.observe(this) { estado -> ... }`** indica qué hacer cada vez que el estado cambia. El código de las llaves se ejecuta al empezar (con el valor actual) y cada vez que el ViewModel asigna un valor nuevo.
- **`this`** como primer parámetro es la `Activity`, y sirve para que `LiveData` solo avise mientras la pantalla está activa. Si la `Activity` está en segundo plano, no se ejecuta nada; al volver, recibe el último valor.

Dentro de `observe` está lo que en Compose hacía la recomposición: la pantalla se actualiza a mano con los datos nuevos. `submitList` actualiza la lista, `isVisible` muestra u oculta el texto de lista vacía y, si hay un mensaje, se muestra el `Snackbar` y se avisa al ViewModel para que lo borre (como en la UD3, apartado 8.2).

### 6.4 El detalle y la vuelta a la lista

El detalle tiene su propio ViewModel, que recibe el `id` del `Intent`:

```kotlin
class DetalleViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val hotelId: String = savedStateHandle[DetalleActivity.EXTRA_HOTEL_ID] ?: ""

    private val _hotel = MutableLiveData<Hotel?>()
    val hotel: LiveData<Hotel?> = _hotel

    init {
        cargar()
    }

    fun cargar() {
        _hotel.value = HotelRepository.getHotelById(hotelId)
    }

    fun onUpdateHotel(name: String, city: String, province: String, phone: String, image: String) {
        val actual = _hotel.value ?: return
        HotelRepository.updateHotel(
            actual.copy(name = name, city = city, province = province, phone = phone, image = image)
        )
        cargar()
    }

    fun onDeleteHotel() {
        HotelRepository.deleteHotel(hotelId)
    }
}
```

`SavedStateHandle` es un almacén de datos que Android entrega a los ViewModel creados con `by viewModels()`. En una `Activity`, contiene los extras del `Intent` que la abrió: `savedStateHandle["hotelId"]` es el `id` del hotel. Así el ViewModel lee el dato sin que la `Activity` tenga que pasárselo.

Cuando se edita o se borra un hotel en el detalle y se vuelve a la lista, la lista tiene que actualizarse. La lista tiene su propio ViewModel, que no sabe nada del cambio. La forma más directa es recargar al volver a la pantalla, en `onResume`, que Android ejecuta cada vez que la `Activity` pasa a primer plano:

```kotlin
override fun onResume() {
    super.onResume()
    viewModel.recargar()
}
```

Esta función va en `MainActivity`, después de `onCreate`.

---

## 7. Diálogos de alta, edición y borrado

### 7.1 Confirmar el borrado

En `DetalleActivity`, el botón «Eliminar» muestra un diálogo de confirmación:

```kotlin
binding.btnEliminar.setOnClickListener {
    val hotel = viewModel.hotel.value ?: return@setOnClickListener
    MaterialAlertDialogBuilder(this)
        .setTitle("Borrar hospedaje")
        .setMessage("¿Seguro que quieres borrar «${hotel.name}»?")
        .setNegativeButton("Cancelar", null)
        .setPositiveButton("Borrar") { _, _ ->
            viewModel.onDeleteHotel()
            finish()
        }
        .show()
}
```

Al confirmar, se borra y `finish()` cierra el detalle. La lista se actualiza sola al volver, por el `onResume` del apartado anterior. `return@setOnClickListener` sale solo de la lambda del botón si el hotel no existe.

### 7.2 El formulario del diálogo

El formulario de alta y edición es un layout propio, `res/layout/dialog_hotel.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:paddingHorizontal="24dp"
    android:paddingTop="16dp">

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/tilNombre"
        style="@style/Widget.Material3.TextInputLayout.OutlinedBox"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Nombre">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/etNombre"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:inputType="textCapWords" />
    </com.google.android.material.textfield.TextInputLayout>

    <!-- Igual para Ciudad (tilCiudad/etCiudad), Provincia (tilProvincia/etProvincia),
         Teléfono (tilTelefono/etTelefono, inputType="phone") e Imagen (etImagen, inputType="textUri") -->

</LinearLayout>
```

### 7.3 Mostrar el diálogo y validar

El mismo diálogo sirve para añadir (desde la lista) y para editar (desde el detalle). Se escribe una vez, como función, en `DialogoHotel.kt`:

```kotlin
fun mostrarDialogoHotel(
    activity: AppCompatActivity,
    hotel: Hotel?,
    onGuardar: (name: String, city: String, province: String, phone: String, image: String) -> Unit
) {
    val binding = DialogHotelBinding.inflate(activity.layoutInflater)

    // Si se edita, se rellenan los campos con los datos actuales
    if (hotel != null) {
        binding.etNombre.setText(hotel.name)
        binding.etCiudad.setText(hotel.city)
        binding.etProvincia.setText(hotel.province)
        binding.etTelefono.setText(hotel.phone)
        binding.etImagen.setText(hotel.image)
    }

    val dialogo = MaterialAlertDialogBuilder(activity)
        .setTitle(if (hotel == null) "Nuevo hospedaje" else "Editar hospedaje")
        .setView(binding.root)
        .setNegativeButton("Cancelar", null)
        .setPositiveButton("Guardar", null)
        .show()

    // El botón Guardar se programa después de show() para que no cierre el diálogo si hay errores
    dialogo.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
        val nombre = binding.etNombre.text.toString().trim()
        val ciudad = binding.etCiudad.text.toString().trim()

        binding.tilNombre.error = if (nombre.isEmpty()) "El nombre es obligatorio" else null
        binding.tilCiudad.error = if (ciudad.isEmpty()) "La ciudad es obligatoria" else null

        if (binding.tilNombre.error == null && binding.tilCiudad.error == null) {
            onGuardar(
                nombre,
                ciudad,
                binding.etProvincia.text.toString().trim(),
                binding.etTelefono.text.toString().trim(),
                binding.etImagen.text.toString().trim()
            )
            dialogo.dismiss()
        }
    }
}
```

- **`hotel: Hotel?`**: con `null` es un alta y los campos empiezan vacíos; con un hotel, es una edición y se rellenan con `setText`.
- **`setView(binding.root)`** pone el formulario dentro del diálogo, entre el título y los botones.
- **`setPositiveButton("Guardar", null)`** y después `getButton(...).setOnClickListener`: los botones de `setPositiveButton` siempre cierran el diálogo al pulsarlos. Para poder validar y dejarlo abierto si hay errores, el botón se crea sin acción y se programa después de `show()`. Solo cuando no hay errores se llama a `onGuardar` y se cierra con `dismiss()`.
- **La validación** pone el error en cada `TextInputLayout`, como en el extra de la UD2 (apartado 9.1).

Desde la lista se usa para añadir:

```kotlin
private fun mostrarDialogoAlta() {
    mostrarDialogoHotel(this, hotel = null) { name, city, province, phone, image ->
        viewModel.onInsertHotel(name, city, province, phone, image)
    }
}
```

Y desde el detalle, para editar el hotel que se está viendo:

```kotlin
binding.btnEditar.setOnClickListener {
    mostrarDialogoHotel(this, hotel = viewModel.hotel.value) { name, city, province, phone, image ->
        viewModel.onUpdateHotel(name, city, province, phone, image)
    }
}
```

> **Diálogos y giro del móvil.** Un diálogo creado con `MaterialAlertDialogBuilder` desaparece al girar el móvil, con lo escrito en el formulario. Para que se conserve hay que escribirlo como un `DialogFragment`, una clase que Android vuelve a crear tras el giro. En Compose no hace falta, porque el diálogo depende de un dato del ViewModel, que sobrevive al giro.

---

## 8. Barra superior, botón flotante y Snackbar

El `Scaffold` de Compose reserva sitio para la barra superior, el contenido y el botón flotante. Con vistas, eso lo hace un `CoordinatorLayout`. Así queda `activity_main.xml` completo:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true">

    <com.google.android.material.appbar.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:title="Mis hospedajes" />

    </com.google.android.material.appbar.AppBarLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvHoteles"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:paddingTop="8dp"
        android:paddingBottom="88dp"
        android:clipToPadding="false"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        app:layout_behavior="@string/appbar_scrolling_view_behavior" />

    <TextView
        android:id="@+id/txtVacio"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="No hay hospedajes"
        android:visibility="gone" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fabAnadir"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="16dp"
        android:contentDescription="Añadir hospedaje"
        app:srcCompat="@drawable/ic_mas" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

- **`AppBarLayout` + `MaterialToolbar`** es la barra superior, el equivalente a `TopAppBar`. `?attr/actionBarSize` es la altura estándar de una barra.
- **`app:layout_behavior="@string/appbar_scrolling_view_behavior"`** coloca la lista debajo de la barra. Sin este atributo, la lista empezaría arriba del todo, tapada por la barra.
- **`FloatingActionButton` con `layout_gravity="bottom|end"`** lo coloca abajo a la derecha. Como está dentro de un `CoordinatorLayout`, sube solo cuando aparece un `Snackbar`, para no quedar tapado.
- **`android:paddingBottom="88dp"`** en la lista deja sitio para que el último hotel no quede debajo del botón.
- **`android:fitsSystemWindows="true"`** evita que la barra quede debajo de la barra de estado del móvil.

Para usar la `MaterialToolbar` como barra de la aplicación, el tema tiene que ser `NoActionBar` (extra de la UD2, apartado 12.1) y la `Activity` la registra con `setSupportActionBar(binding.toolbar)`, como en el código del apartado 6.3. La barra con flecha de volver y menú de opciones se ve en el [extra de la UD4](xml_ud4.md).

---

## 9. Equivalencias con Compose

| Compose (UD3) | Vistas y XML |
|---|---|
| `LazyColumn { items(lista) { ... } }` | `RecyclerView` + `LinearLayoutManager` + `ListAdapter` + layout de fila |
| `key = { it.id }` | `DiffUtil.ItemCallback` con `areItemsTheSame` |
| `LazyVerticalGrid` | `RecyclerView` + `GridLayoutManager` |
| `AsyncImage(model = url)` | `imageView.load(url)` (Coil para vistas) |
| Navegación con estado (`idSeleccionado`) | Otra `Activity` + `Intent` con el `id` |
| `BackHandler` | No hace falta: Atrás cierra la `Activity` |
| `mutableStateOf` en el ViewModel | `MutableLiveData` privado + `LiveData` público |
| `viewModel()` | `by viewModels()` |
| Leer `viewModel.uiState` (recomposición) | `viewModel.uiState.observe(this) { ... }` y actualizar las vistas a mano |
| `AlertDialog` según el estado | `MaterialAlertDialogBuilder(...).show()` |
| Diálogo que sobrevive al giro | `DialogFragment` |
| `Scaffold(topBar, floatingActionButton)` | `CoordinatorLayout` + `AppBarLayout` + `MaterialToolbar` + `FloatingActionButton` |
| `SnackbarHostState.showSnackbar` | `Snackbar.make(vista, texto, duración).show()` |
