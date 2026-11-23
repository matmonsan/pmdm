---
title: Extra UD4. Navegación y menús con XML
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al inicio](index.md)

> **Material extra.** Este documento explica los contenidos de la [UD4](ud4.md) con el sistema de vistas clásico: *fragments*, un grafo de navegación en XML y menús definidos en `res/menu`. No se pide en el proyecto, que se hace con Jetpack Compose. Antes conviene leer los extras de la [UD2](xml_ud2.md) y la [UD3](xml_ud3.md).

## Índice

0. [Resumen de términos](#0-resumen-de-términos)
1. [El ejemplo](#1-el-ejemplo)
2. [Dependencias](#2-dependencias)
3. [Fragments: una pantalla dentro de la Activity](#3-fragments-una-pantalla-dentro-de-la-activity)
4. [El grafo de navegación](#4-el-grafo-de-navegación)
5. [El NavHostFragment y la navegación](#5-el-navhostfragment-y-la-navegación)
6. [La pila: popUpTo y launchSingleTop](#6-la-pila-popupto-y-launchsingletop)
7. [Animaciones entre pantallas](#7-animaciones-entre-pantallas)
8. [Paso de argumentos](#8-paso-de-argumentos)
9. [La estructura de la Activity](#9-la-estructura-de-la-activity)
10. [Barra superior con menú de opciones](#10-barra-superior-con-menú-de-opciones)
11. [Destinos de nivel superior y botón de volver](#11-destinos-de-nivel-superior-y-botón-de-volver)
12. [Menú lateral con NavigationView](#12-menú-lateral-con-navigationview)
13. [Barra inferior con BottomNavigationView](#13-barra-inferior-con-bottomnavigationview)
14. [La MainActivity completa](#14-la-mainactivity-completa)
15. [Flujo de acceso con un grafo anidado](#15-flujo-de-acceso-con-un-grafo-anidado)
16. [Equivalencias con Compose](#16-equivalencias-con-compose)

---

## 0. Resumen de términos

| Término | Qué significa |
|---|---|
| Fragment | Parte de una pantalla con su propio layout y su propio código, que vive dentro de una `Activity`. |
| Grafo de navegación | Archivo `res/navigation/nav_graph.xml` con los destinos (*fragments*) y las acciones entre ellos. |
| Destino | Cada *fragment* del grafo, con su `id`. |
| Acción | Camino de un destino a otro, con su `id` y sus opciones (`popUpTo`, animaciones). |
| `NavHostFragment` | Contenedor que muestra el *fragment* actual del grafo. |
| `findNavController()` | Obtiene el `NavController` desde un *fragment*. |
| `AppBarConfiguration` | Indica qué destinos son de nivel superior y qué menú lateral abre el icono ≡. |
| `setupActionBarWithNavController` | Hace que la barra superior muestre el título de cada destino y el icono ≡ o ←. |
| `DrawerLayout` | Layout que contiene el contenido de la pantalla y el menú lateral. |
| `NavigationView` | El panel del menú lateral, con su cabecera y sus opciones. |
| `BottomNavigationView` | La barra inferior. |
| `setupWithNavController` | Conecta un menú lateral o una barra inferior con el `NavController`: navega y marca la opción actual. |
| `res/menu` | Carpeta con los archivos XML de los menús: opciones con `id`, texto e icono. |

---

## 1. El ejemplo

Se construye la misma app de colores de la UD4: Inicio, A, B, C y Acerca de, con barra superior y menú de opciones, menú lateral y barra inferior.

| Pantalla | Color | Botones | Nivel |
|---|---|---|---|
| Inicio | Amarillo | «Ir a la pantalla A», «Ir a la pantalla B» | Superior |
| A | Verde | «Atrás» | Superior |
| B | Azul | «Atrás», «Siguiente» | Superior |
| C | Rojo | «Atrás», «Inicio» | Inferior |
| Acerca de | Gris | Ninguno | Superior |

En Compose, cada pantalla es un composable y todas viven en la misma `Activity`. Con vistas, cada pantalla es un *fragment*, y todos viven dentro de una única `MainActivity`, que tiene las barras y el menú lateral.

```text
app/src/main/java/com/ejemplo/navegacion/
├── MainActivity.kt
├── InicioFragment.kt
├── AFragment.kt
├── BFragment.kt
├── CFragment.kt
└── AcercaDeFragment.kt

app/src/main/res/
├── layout/
│   ├── activity_main.xml        ← DrawerLayout, barras y contenedor de fragments
│   ├── nav_header.xml           ← cabecera del menú lateral
│   ├── fragment_inicio.xml
│   ├── fragment_a.xml ... fragment_acerca_de.xml
├── menu/
│   ├── toolbar_menu.xml         ← opciones de la barra superior
│   ├── nav_menu.xml             ← opciones del menú lateral
│   └── bottom_menu.xml          ← botones de la barra inferior
└── navigation/
    └── nav_graph.xml            ← el grafo de navegación
```

---

## 2. Dependencias

Crea el proyecto con la plantilla **Empty Views Activity**, activa View Binding (extra de la UD2, apartado 7.1) y añade en `build.gradle.kts (Module :app)`:

```kotlin
dependencies {
    // ... las dependencias que ya había
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.0")
}
```

- **`navigation-fragment-ktx`** trae el `NavHostFragment` y la navegación entre *fragments*.
- **`navigation-ui-ktx`** trae las funciones que conectan la navegación con la barra superior, el menú lateral y la barra inferior (`setupWithNavController`...).

Si Android Studio propone una versión más reciente, acéptala para las dos.

---

## 3. Fragments: una pantalla dentro de la Activity

Un *fragment* es una parte de la pantalla con su propio layout y su propio código. La `Activity` se queda con lo común (las barras, el menú lateral) y cada *fragment* pone el contenido de una pantalla.

### 3.1 El layout del fragment

`res/layout/fragment_inicio.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#FFEB3B"
    android:gravity="center_horizontal"
    android:orientation="vertical"
    android:padding="24dp">

    <Button
        android:id="@+id/btnIrA"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Ir a la pantalla A" />

    <Button
        android:id="@+id/btnIrB"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:text="Ir a la pantalla B" />

</LinearLayout>
```

Los layouts de A, B, C y Acerca de siguen el mismo esquema que las pantallas de la UD4 (apartado 3.3): fondo de color, botones arriba y una letra grande en el centro.

### 3.2 La clase del fragment

`InicioFragment.kt`:

```kotlin
class InicioFragment : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnIrA.setOnClickListener { /* apartado 5 */ }
        binding.btnIrB.setOnClickListener { /* apartado 5 */ }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

Un *fragment* tiene tres funciones que se escriben siempre:

- **`onCreateView`** crea las vistas del *fragment* a partir de su layout y las devuelve. Es el equivalente a `setContentView` en una `Activity`.
- **`onViewCreated`** se ejecuta cuando las vistas ya existen. Aquí se programan los botones.
- **`onDestroyView`** se ejecuta cuando se destruyen las vistas, por ejemplo al navegar a otra pantalla. El *fragment* puede seguir existiendo en la pila sin vistas, y el *binding* se pone a `null` para no guardar referencias a vistas que ya no existen.

Por eso el *binding* se guarda en dos propiedades: `_binding`, que puede ser `null`, y `binding`, que se usa en el resto del código y da por hecho que no lo es. `get() = _binding!!` hace que `binding` devuelva el valor de `_binding` cada vez que se lee.

En Compose no existe nada de esto: un composable no tiene vistas que crear ni destruir.

---

## 4. El grafo de navegación

En Compose, el grafo se escribe en Kotlin dentro del `NavHost` (UD4, apartado 3.4). Con vistas, es un archivo XML. Clic derecho en `res` → **New → Android Resource File**, nombre `nav_graph`, tipo **Navigation**. Android Studio crea `res/navigation/nav_graph.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph"
    app:startDestination="@id/inicioFragment">

    <fragment
        android:id="@+id/inicioFragment"
        android:name="com.ejemplo.navegacion.InicioFragment"
        android:label="Inicio"
        tools:layout="@layout/fragment_inicio">
        <action
            android:id="@+id/action_inicio_to_a"
            app:destination="@id/aFragment" />
        <action
            android:id="@+id/action_inicio_to_b"
            app:destination="@id/bFragment" />
    </fragment>

    <fragment
        android:id="@+id/aFragment"
        android:name="com.ejemplo.navegacion.AFragment"
        android:label="Pantalla A"
        tools:layout="@layout/fragment_a" />

    <fragment
        android:id="@+id/bFragment"
        android:name="com.ejemplo.navegacion.BFragment"
        android:label="Pantalla B"
        tools:layout="@layout/fragment_b">
        <action
            android:id="@+id/action_b_to_c"
            app:destination="@id/cFragment" />
    </fragment>

    <fragment
        android:id="@+id/cFragment"
        android:name="com.ejemplo.navegacion.CFragment"
        android:label="Pantalla C"
        tools:layout="@layout/fragment_c">
        <action
            android:id="@+id/action_c_to_inicio"
            app:popUpTo="@id/inicioFragment" />
    </fragment>

    <fragment
        android:id="@+id/acercaDeFragment"
        android:name="com.ejemplo.navegacion.AcercaDeFragment"
        android:label="Acerca de"
        tools:layout="@layout/fragment_acerca_de" />

</navigation>
```

- **`app:startDestination`** es la primera pantalla, como `startDestination = Rutas.INICIO`.
- **Cada `<fragment>`** es un destino. `android:id` es su identificador (el equivalente a la ruta), `android:name` la clase del *fragment* y `android:label` el título que mostrará la barra superior (apartado 11).
- **`tools:layout`** solo sirve para que el editor muestre una vista previa del destino; no afecta a la aplicación.
- **Cada `<action>`** es un camino desde ese destino a otro, con su propio `id`. Se navega por la acción, no por el destino (apartado 5).

Al abrir el archivo con la vista **Design**, Android Studio dibuja el grafo: cada destino como una miniatura y cada acción como una flecha. Las acciones se pueden crear arrastrando de un destino a otro.

---

## 5. El NavHostFragment y la navegación

### 5.1 El contenedor

Los *fragments* se muestran dentro de un `NavHostFragment`, que se pone en el layout de la `Activity`. Es el equivalente al `NavHost` de Compose:

```xml
<androidx.fragment.app.FragmentContainerView
    android:id="@+id/navHostFragment"
    android:name="androidx.navigation.fragment.NavHostFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:defaultNavHost="true"
    app:navGraph="@navigation/nav_graph" />
```

- **`android:name="...NavHostFragment"`** indica qué *fragment* va en el contenedor: el de la navegación.
- **`app:navGraph`** le dice qué grafo usar.
- **`app:defaultNavHost="true"`** hace que el botón Atrás del móvil quite la pantalla de arriba de la pila. Sin él, Atrás cerraría la `Activity` directamente.

En el apartado 9 se ve dónde va dentro de `activity_main.xml`, junto a las barras.

### 5.2 Navegar desde un fragment

Desde un *fragment*, el `NavController` se obtiene con `findNavController()` y se navega con el `id` de una acción:

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.btnIrA.setOnClickListener {
        findNavController().navigate(R.id.action_inicio_to_a)
    }
    binding.btnIrB.setOnClickListener {
        findNavController().navigate(R.id.action_inicio_to_b)
    }
}
```

`R.id.action_inicio_to_a` es el `id` de la acción del grafo. Si se navega con una acción que no sale del destino actual, la aplicación se cierra con un error: las acciones sirven para que el grafo diga qué caminos existen.

Para volver atrás, igual que en Compose:

```kotlin
binding.btnAtras.setOnClickListener { findNavController().popBackStack() }
```

---

## 6. La pila: popUpTo y launchSingleTop

La pila funciona igual que en Compose (UD4, apartado 3.6). Las opciones al navegar se escriben como atributos de la acción, en el grafo. La acción de C a Inicio:

```xml
<action
    android:id="@+id/action_c_to_inicio"
    app:popUpTo="@id/inicioFragment"
    app:launchSingleTop="true" />
```

- **`app:popUpTo="@id/inicioFragment"`** quita de la pila todo lo que hay encima de Inicio. Como esta acción no tiene `app:destination`, solo hace eso: vuelve a Inicio.
- **`app:popUpToInclusive="true"`** quitaría también Inicio. Se usa, por ejemplo, para quitar el login de la pila al entrar (apartado 15).
- **`app:launchSingleTop="true"`** evita apilar dos veces el mismo destino seguido.

En `CFragment`:

```kotlin
binding.btnInicio.setOnClickListener {
    findNavController().navigate(R.id.action_c_to_inicio)
}
```

La ventaja de escribir las opciones en el grafo es que se ven en un solo sitio; el inconveniente es que, para saber qué hace un botón, hay que mirar el código y el XML.

---

## 7. Animaciones entre pantallas

Las animaciones también son atributos de la acción, con cuatro valores, uno por situación (los mismos cuatro de la UD4, apartado 3.8):

```xml
<action
    android:id="@+id/action_b_to_c"
    app:destination="@id/cFragment"
    app:enterAnim="@anim/entrar_derecha"
    app:exitAnim="@anim/salir_izquierda"
    app:popEnterAnim="@anim/entrar_izquierda"
    app:popExitAnim="@anim/salir_derecha" />
```

Cada animación es un archivo de `res/anim` (clic derecho en `res` → **New → Android Resource File**, tipo **Animation**). `res/anim/entrar_derecha.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<translate xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="300"
    android:fromXDelta="100%"
    android:toXDelta="0%" />
```

`translate` desplaza la pantalla: aquí, desde el 100 % de su ancho a la derecha (fuera de la pantalla) hasta su posición. Las otras tres cambian los valores: `salir_izquierda` va de `0%` a `-100%`, `entrar_izquierda` de `-100%` a `0%` y `salir_derecha` de `0%` a `100%`.

---

## 8. Paso de argumentos

### 8.1 Declarar el argumento en el grafo

El argumento se declara dentro del destino que lo recibe:

```xml
<fragment
    android:id="@+id/bFragment"
    android:name="com.ejemplo.navegacion.BFragment"
    android:label="Pantalla B">
    <argument
        android:name="nombre"
        app:argType="string"
        android:defaultValue="" />
    <action
        android:id="@+id/action_b_to_c"
        app:destination="@id/cFragment" />
</fragment>
```

`app:argType` es el tipo: `string`, `integer`, `boolean`, `float`... Es lo que en Compose se declaraba con `navArgument("nombre") { type = NavType.StringType }`.

### 8.2 Enviar y leer el argumento

Al navegar, los argumentos se pasan en un `Bundle` creado con `bundleOf`:

```kotlin
binding.btnIrB.setOnClickListener {
    val nombre = binding.etNombre.text.toString()
    findNavController().navigate(R.id.action_inicio_to_b, bundleOf("nombre" to nombre))
}
```

`bundleOf("nombre" to nombre)` crea un `Bundle` con el valor guardado bajo la clave `"nombre"`. A diferencia de las rutas de Compose, el texto no viaja dentro de una dirección, así que no hace falta codificarlo con `Uri.encode`.

En `BFragment`, se lee de `arguments`:

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val nombre = arguments?.getString("nombre") ?: ""
    binding.txtNombre.text = nombre
}
```

> **Safe Args.** Con el *plugin* `androidx.navigation.safeargs.kotlin`, Android Studio genera clases a partir del grafo para no escribir las claves a mano: se navega con `findNavController().navigate(InicioFragmentDirections.actionInicioToB(nombre))` y se lee con `private val args: BFragmentArgs by navArgs()` y `args.nombre`. Si la clave o el tipo no coinciden, el error aparece al compilar.

---

## 9. La estructura de la Activity

Las barras y el menú lateral están en la `Activity`, no en los *fragments*: así no se repiten en cada pantalla. Este es `activity_main.xml` completo; los apartados siguientes explican cada parte:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/drawerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true">

    <!-- 1. El contenido: barra superior, fragments, barra inferior y botón flotante -->
    <androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <com.google.android.material.appbar.AppBarLayout
                android:layout_width="match_parent"
                android:layout_height="wrap_content">

                <com.google.android.material.appbar.MaterialToolbar
                    android:id="@+id/toolbar"
                    android:layout_width="match_parent"
                    android:layout_height="?attr/actionBarSize" />

            </com.google.android.material.appbar.AppBarLayout>

            <androidx.fragment.app.FragmentContainerView
                android:id="@+id/navHostFragment"
                android:name="androidx.navigation.fragment.NavHostFragment"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_weight="1"
                app:defaultNavHost="true"
                app:navGraph="@navigation/nav_graph" />

            <com.google.android.material.bottomnavigation.BottomNavigationView
                android:id="@+id/bottomNav"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:menu="@menu/bottom_menu" />

        </LinearLayout>

        <com.google.android.material.floatingactionbutton.FloatingActionButton
            android:id="@+id/fabSiguiente"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom|end"
            android:layout_marginEnd="16dp"
            android:layout_marginBottom="96dp"
            android:contentDescription="Ir a la pantalla C"
            app:srcCompat="@drawable/ic_flecha_adelante" />

    </androidx.coordinatorlayout.widget.CoordinatorLayout>

    <!-- 2. El menú lateral -->
    <com.google.android.material.navigation.NavigationView
        android:id="@+id/navView"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        app:headerLayout="@layout/nav_header"
        app:menu="@menu/nav_menu" />

</androidx.drawerlayout.widget.DrawerLayout>
```

- **`DrawerLayout`** es la raíz y tiene dos hijos: el contenido de la pantalla y el menú lateral. Es el equivalente a `ModalNavigationDrawer`.
- **El primer hijo** es todo lo que se ve con el menú cerrado. Un `LinearLayout` vertical apila la barra superior, el `NavHostFragment` (que ocupa todo el espacio que sobra con `layout_weight="1"`) y la barra inferior. Es el reparto que hacía el `Scaffold`.
- **El botón flotante** va en el `CoordinatorLayout`, encima de todo, colocado abajo a la derecha. `layout_marginBottom="96dp"` lo sube por encima de la barra inferior (80dp de barra más 16dp de margen).
- **`NavigationView` con `layout_gravity="start"`** es el panel que aparece desde la izquierda. Su cabecera y sus opciones vienen de otros dos archivos (apartado 12).

Para que la `MaterialToolbar` sea la barra de la aplicación, el tema tiene que ser `Theme.Material3.DayNight.NoActionBar` (extra de la UD2, apartado 12.1).

---

## 10. Barra superior con menú de opciones

### 10.1 El archivo del menú

Las acciones de la barra se escriben en un archivo de menú. Clic derecho en `res` → **New → Android Resource File**, tipo **Menu**, nombre `toolbar_menu`. `res/menu/toolbar_menu.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item
        android:id="@+id/inicioFragment"
        android:icon="@drawable/ic_casa"
        android:title="Inicio"
        app:showAsAction="ifRoom" />

    <item
        android:id="@+id/aFragment"
        android:title="Pantalla A"
        app:showAsAction="never" />

    <item
        android:id="@+id/bFragment"
        android:title="Pantalla B"
        app:showAsAction="never" />

</menu>
```

- **`app:showAsAction`** decide dónde aparece cada opción: `ifRoom` como icono en la barra si cabe (el `IconButton` de `actions` en Compose); `never` dentro del menú ⋮ (el `DropdownMenuItem`); `always` siempre como icono.
- **Los `id` de las opciones son los de los destinos del grafo** (`@+id/inicioFragment`...). No es obligatorio, pero permite que la navegación se haga sola (apartado 10.2).

### 10.2 Mostrar el menú y responder

En `MainActivity`:

```kotlin
override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.toolbar_menu, menu)
    return true
}

override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
}
```

- **`onCreateOptionsMenu`** infla el archivo de menú en la barra. Android la llama al crear la `Activity`.
- **`onOptionsItemSelected`** se llama al pulsar una opción. `item.onNavDestinationSelected(navController)` busca en el grafo un destino con el mismo `id` que la opción y navega a él, quitando de la pila lo que hay encima de la primera pantalla. Devuelve `false` si no hay ningún destino con ese `id`; entonces se deja la opción a `super`.

Si una opción no corresponde a un destino (por ejemplo, «Compartir»), se responde con un `when`:

```kotlin
override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
        R.id.compartir -> { compartir(); true }
        else -> item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}
```

---

## 11. Destinos de nivel superior y botón de volver

En la UD4 (apartado 6), la barra calculaba con el destino actual qué título poner y si mostrar ≡ o ←. Con vistas, eso lo hacen `AppBarConfiguration` y `setupActionBarWithNavController`, en `onCreate` de `MainActivity`:

```kotlin
setSupportActionBar(binding.toolbar)

val navHostFragment =
    supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
navController = navHostFragment.navController

appBarConfiguration = AppBarConfiguration(
    setOf(R.id.inicioFragment, R.id.aFragment, R.id.bFragment, R.id.acercaDeFragment),
    binding.drawerLayout
)
setupActionBarWithNavController(navController, appBarConfiguration)
```

- **`setSupportActionBar(binding.toolbar)`** convierte la `MaterialToolbar` en la barra de la aplicación.
- **El `NavController` de la `Activity`** se obtiene del `NavHostFragment`. Desde la `Activity` no se puede usar `findNavController()` como en los *fragments*, porque el contenedor todavía no existe del todo cuando se ejecuta `onCreate`.
- **`AppBarConfiguration(setOf(...), binding.drawerLayout)`**: el conjunto son los destinos de nivel superior, como la lista `seccionesMenu` de la UD4. El segundo parámetro es el menú lateral que debe abrir el icono ≡.
- **`setupActionBarWithNavController`** conecta la barra con la navegación. A partir de aquí, al navegar, la barra pone como título el `android:label` del destino, muestra ≡ en los de nivel superior y ← en el resto.

La flecha ← necesita una función más en `MainActivity`, que Android llama al pulsarla:

```kotlin
override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}
```

`navigateUp(appBarConfiguration)` vuelve atrás en la pila o, si el destino es de nivel superior, abre el menú lateral. Es lo que hace el botón de la izquierda de la barra en los dos casos, ≡ y ←.

---

## 12. Menú lateral con NavigationView

### 12.1 La cabecera

`res/layout/nav_header.xml` es un layout normal con la foto, el nombre y el correo:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="?attr/colorPrimaryContainer"
    android:gravity="center_vertical"
    android:orientation="horizontal"
    android:paddingHorizontal="16dp"
    android:paddingVertical="24dp">

    <com.google.android.material.imageview.ShapeableImageView
        android:id="@+id/imgFoto"
        android:layout_width="64dp"
        android:layout_height="64dp"
        android:scaleType="centerCrop"
        android:src="@drawable/foto_perfil"
        app:shapeAppearanceOverlay="@style/ImagenRedonda" />

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:orientation="vertical">

        <TextView
            android:id="@+id/txtNombre"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Ana García"
            android:textAppearance="?attr/textAppearanceTitleMedium"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/txtEmail"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="ana@ejemplo.com"
            android:textAppearance="?attr/textAppearanceBodyMedium" />

    </LinearLayout>
</LinearLayout>
```

`ShapeableImageView` es una `ImageView` de Material que admite formas. La forma redonda se define como estilo en `res/values/themes.xml`, junto al tema:

```xml
<style name="ImagenRedonda">
    <item name="cornerSize">50%</item>
</style>
```

`cornerSize` al 50 % redondea las esquinas hasta formar un círculo, como `clip(CircleShape)`.

### 12.2 Las opciones

`res/menu/nav_menu.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <group android:checkableBehavior="single">
        <item android:id="@+id/inicioFragment" android:icon="@drawable/ic_casa" android:title="Inicio" />
        <item android:id="@+id/aFragment" android:icon="@drawable/ic_corazon" android:title="Pantalla A" />
        <item android:id="@+id/bFragment" android:icon="@drawable/ic_estrella" android:title="Pantalla B" />
        <item android:id="@+id/acercaDeFragment" android:icon="@drawable/ic_info" android:title="Acerca de" />
    </group>

    <group android:id="@+id/grupoSesion">
        <item android:id="@+id/cerrarSesion" android:icon="@drawable/ic_salir" android:title="Cerrar sesión" />
    </group>

</menu>
```

- **`<group android:checkableBehavior="single">`** hace que solo una opción del grupo pueda estar marcada a la vez: la de la pantalla actual. Es el `selected` de cada `NavigationDrawerItem`.
- **Los `id` de las opciones son los de los destinos.** Es obligatorio para el apartado siguiente: un error de un carácter hace que esa opción no navegue.
- **Dos grupos** se separan con una línea en el menú, como el `HorizontalDivider` de la UD4.

### 12.3 Conectarlo con la navegación

En `onCreate` de `MainActivity`, una línea:

```kotlin
binding.navView.setupWithNavController(navController)
```

`setupWithNavController` hace tres cosas a la vez: al pulsar una opción, navega al destino con el mismo `id` (con las opciones de `irASeccion` de la UD4: `popUpTo` a la primera pantalla, `saveState` y `restoreState`); cierra el menú; y al cambiar de pantalla por cualquier camino, marca la opción del destino actual.

La opción «Cerrar sesión» no tiene un destino con su `id`, así que hay que programarla aparte:

```kotlin
binding.navView.menu.findItem(R.id.cerrarSesion).setOnMenuItemClickListener {
    binding.drawerLayout.closeDrawer(GravityCompat.START)
    Toast.makeText(this, "Cerrar sesión: apartado 15", Toast.LENGTH_SHORT).show()
    true
}
```

`GravityCompat.START` indica el menú que se abre desde el principio de la pantalla (la izquierda). El `true` del final indica que el clic ya está atendido.

---

## 13. Barra inferior con BottomNavigationView

### 13.1 Las opciones

`res/menu/bottom_menu.xml` tiene las tres secciones de la barra, con los mismos `id` de los destinos:

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/inicioFragment" android:icon="@drawable/ic_casa" android:title="Inicio" />
    <item android:id="@+id/aFragment" android:icon="@drawable/ic_corazon" android:title="Pantalla A" />
    <item android:id="@+id/bFragment" android:icon="@drawable/ic_estrella" android:title="Pantalla B" />
</menu>
```

### 13.2 Conectarla con la navegación

En `onCreate`:

```kotlin
binding.bottomNav.setupWithNavController(navController)
```

Hace lo mismo que con el menú lateral: navega al pulsar y marca el botón de la pantalla actual. Como los dos usan los mismos `id`, siempre marcan la misma sección.

### 13.3 Contador sobre un icono

```kotlin
binding.bottomNav.getOrCreateBadge(R.id.bFragment).number = 3
```

`getOrCreateBadge` crea el contador sobre el botón con ese `id`, como el `BadgedBox` de Compose. Para quitarlo: `binding.bottomNav.removeBadge(R.id.bFragment)`.

### 13.4 Ocultar la barra y el botón flotante según la pantalla

En la UD4, `bottomBar` y `floatingActionButton` tenían un `if` con el destino actual. Con vistas, se escucha cada cambio de destino y se muestran u ocultan las vistas:

```kotlin
navController.addOnDestinationChangedListener { _, destino, _ ->
    val esNivelSuperior = destino.id in appBarConfiguration.topLevelDestinations
    binding.bottomNav.isVisible = esNivelSuperior
    binding.fabSiguiente.isVisible = destino.id == R.id.bFragment
    binding.drawerLayout.setDrawerLockMode(
        if (esNivelSuperior) DrawerLayout.LOCK_MODE_UNLOCKED
        else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
    )
}

binding.fabSiguiente.setOnClickListener {
    navController.navigate(R.id.action_b_to_c)
}
```

- **`addOnDestinationChangedListener`** ejecuta la lambda cada vez que cambia la pantalla, con el destino nuevo.
- **`appBarConfiguration.topLevelDestinations`** es el conjunto de destinos de nivel superior del apartado 11.
- **`setDrawerLockMode`** permite o impide abrir el menú deslizando el dedo, como `gesturesEnabled`. `LOCK_MODE_LOCKED_CLOSED` lo deja cerrado en C.

---

## 14. La MainActivity completa

```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        // Navegación (apartado 11)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.inicioFragment, R.id.aFragment, R.id.bFragment, R.id.acercaDeFragment),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Menú lateral y barra inferior (apartados 12 y 13)
        binding.navView.setupWithNavController(navController)
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.getOrCreateBadge(R.id.bFragment).number = 3

        binding.navView.menu.findItem(R.id.cerrarSesion).setOnMenuItemClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Cerrar sesión: apartado 15", Toast.LENGTH_SHORT).show()
            true
        }

        // Qué se ve en cada pantalla (apartado 13.4)
        navController.addOnDestinationChangedListener { _, destino, _ ->
            val esNivelSuperior = destino.id in appBarConfiguration.topLevelDestinations
            binding.bottomNav.isVisible = esNivelSuperior
            binding.fabSiguiente.isVisible = destino.id == R.id.bFragment
            binding.drawerLayout.setDrawerLockMode(
                if (esNivelSuperior) DrawerLayout.LOCK_MODE_UNLOCKED
                else DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            )
        }

        binding.fabSiguiente.setOnClickListener {
            navController.navigate(R.id.action_b_to_c)
        }
    }

    // Menú de opciones (apartado 10)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    // Flecha ← e icono ≡ (apartado 11)
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
```

Comparada con `AppNavegacion` de la UD4, hay mucho menos código de navegación: el título, el icono ≡ o ←, marcar la opción actual y cerrar el menú al elegir los hacen las funciones `setup...WithNavController`. A cambio, todo depende de que los `id` de los menús coincidan con los del grafo, y lo que no encaja en ese esquema (cerrar sesión, ocultar la barra) se programa aparte.

---

## 15. Flujo de acceso con un grafo anidado

Las pantallas de acceso se agrupan en un grafo anidado, igual que con `navigation(...)` en Compose (UD4, apartado 9.1). En `nav_graph.xml`, se cambia el destino inicial y se añade el grupo:

```xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/nav_graph"
    app:startDestination="@id/auth_graph">

    <navigation
        android:id="@+id/auth_graph"
        app:startDestination="@id/loginFragment">

        <fragment
            android:id="@+id/loginFragment"
            android:name="com.ejemplo.navegacion.LoginFragment"
            android:label="Iniciar sesión">
            <action
                android:id="@+id/action_login_to_registro"
                app:destination="@id/registroFragment" />
            <action
                android:id="@+id/action_login_to_inicio"
                app:destination="@id/inicioFragment"
                app:popUpTo="@id/auth_graph"
                app:popUpToInclusive="true" />
        </fragment>

        <fragment
            android:id="@+id/registroFragment"
            android:name="com.ejemplo.navegacion.RegistroFragment"
            android:label="Crear cuenta" />

    </navigation>

    <!-- ... los destinos de los apartados anteriores: inicioFragment, aFragment... -->

    <action
        android:id="@+id/action_global_logout"
        app:destination="@id/auth_graph"
        app:popUpTo="@id/nav_graph"
        app:popUpToInclusive="true" />

</navigation>
```

- **`<navigation android:id="@+id/auth_graph">`** es el grupo de acceso, con su propio destino inicial.
- **`action_login_to_inicio`** va a Inicio y quita de la pila todo el grupo de acceso (`popUpTo` al grupo, con `popUpToInclusive`). Atrás en Inicio cierra la aplicación en lugar de volver al login.
- **`action_global_logout`** está fuera de los destinos, directamente dentro del grafo: es una acción global, que se puede usar desde cualquier pantalla. Va al acceso y quita toda la pila.

La opción «Cerrar sesión» del menú lateral usa la acción global:

```kotlin
binding.navView.menu.findItem(R.id.cerrarSesion).setOnMenuItemClickListener {
    binding.drawerLayout.closeDrawer(GravityCompat.START)
    navController.navigate(R.id.action_global_logout)
    true
}
```

En las pantallas de acceso no deben verse las barras ni el menú. En el `addOnDestinationChangedListener` del apartado 13.4 se añade la comprobación del grupo:

```kotlin
val enAcceso = destino.parent?.id == R.id.auth_graph
binding.toolbar.isVisible = !enAcceso
```

`destino.parent` es el grafo que contiene al destino: para el login y el registro es `auth_graph`. Como `esNivelSuperior` es `false` en el acceso, la barra inferior ya se oculta y el menú lateral ya queda bloqueado con el código del apartado 13.4.

---

## 16. Equivalencias con Compose

| Compose (UD4) | Vistas y XML |
|---|---|
| Composable por pantalla | `Fragment` con su layout |
| `NavHost { composable(ruta) { ... } }` | `res/navigation/nav_graph.xml` con `<fragment>` |
| `rememberNavController()` | `NavHostFragment.navController` / `findNavController()` |
| `NavHost(...)` en el layout | `FragmentContainerView` con `NavHostFragment` |
| `navigate(Rutas.A)` | `navigate(R.id.action_inicio_to_a)` |
| `navigate(...) { popUpTo(...) }` | `app:popUpTo` y `app:popUpToInclusive` en la `<action>` |
| `enterTransition` / `exitTransition` | `app:enterAnim` / `app:exitAnim` y archivos de `res/anim` |
| `navArgument` + `arguments?.getString` | `<argument>` + `bundleOf` + `arguments?.getString`, o Safe Args |
| `TopAppBar` con `actions` y `DropdownMenu` | `MaterialToolbar` + `res/menu/toolbar_menu.xml` + `onCreateOptionsMenu` |
| `esNivelSuperior` y el `if` del `navigationIcon` | `AppBarConfiguration` + `setupActionBarWithNavController` + `onSupportNavigateUp` |
| Título según el destino (`when`) | `android:label` de cada destino |
| `ModalNavigationDrawer` + `ModalDrawerSheet` | `DrawerLayout` + `NavigationView` |
| `CabeceraMenu` | `app:headerLayout="@layout/nav_header"` |
| `NavigationDrawerItem` por sección | `<item>` en `res/menu/nav_menu.xml` |
| `irASeccion` + `selected = destino.esSeccion(...)` | `navView.setupWithNavController(navController)` |
| `NavigationBar` + `NavigationBarItem` | `BottomNavigationView` + `res/menu/bottom_menu.xml` |
| `BadgedBox` | `bottomNav.getOrCreateBadge(id).number = 3` |
| `if (esNivelSuperior)` en `bottomBar` | `addOnDestinationChangedListener` + `isVisible` |
| `gesturesEnabled = esNivelSuperior` | `drawerLayout.setDrawerLockMode(...)` |
| `navigation(route = Rutas.AUTH, ...)` | `<navigation android:id="@+id/auth_graph">` anidado |
