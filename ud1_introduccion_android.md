---
title: UD 1.2. Introducción a Android
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al inicio](index.md)

## Índice
1. [Evolución de las Tecnologías Móviles](#1-evolución-de-las-tecnologías-móviles)
2. [Historia y Versiones del Sistema Operativo Android](#2-historia-y-versiones-del-sistema-operativo-android)
3. [Glosario de Conceptos Fundamentales de Android](#3-glosario-de-conceptos-fundamentales-de-android)
4. [Componentes Principales de una Aplicación Android](#4-componentes-principales-de-una-aplicación-android)
5. [Entorno de Desarrollo: Android Studio y AVD](#5-entorno-de-desarrollo-android-studio-y-avd)
6. [Estructura de un Proyecto en Android y AndroidManifest.xml](#6-estructura-de-un-proyecto-en-android-y-androidmanifestxml)
7. [El Ciclo de Vida de una Activity](#7-el-ciclo-de-vida-de-una-activity)
8. [Gestión de Eventos e Interacción en la Interfaz](#8-gestión-de-eventos-e-interacción-en-la-interfaz)
9. [Intents (Navegación) y Gestión de Permisos](#9-intents-navegación-y-gestión-de-permisos)
10. [Introducción al Desarrollo Moderno con Jetpack Compose](#10-introducción-al-desarrollo-moderno-con-jetpack-compose)

---

## 1. EVOLUCIÓN DE LAS TECNOLOGÍAS MÓVILES

El desarrollo de software móvil ha ido de la mano de la evolución de las redes de telefonía inalámbrica. Cada generación tecnológica introdujo capacidades de red que condicionaron el tipo de aplicaciones que los desarrolladores podían crear.

![Figura 1. Línea temporal de la evolución de las tecnologías de redes móviles (1G a 5G)](/images/figura_1_evolucion_redes.png)  
*Descripción de la Figura 1: Esquema cronológico que muestra desde la telefonía analógica 1G hasta la llegada de la quinta generación (5G) enfocada en ultrabaja latencia e Internet de las Cosas (IoT).*

### 1.1 Primera Generación (1G)
* **Tecnología:** Analógica (TACS, NMT, AMPS).
* **Características:** Solo permitía la transmisión de voz. La calidad del sonido era baja, no existía encriptación (seguridad nula) y el roaming entre países no era posible.
* **Impacto en desarrollo:** Inexistente a nivel de consumidor; los teléfonos no ejecutaban aplicaciones.

### 1.2 Segunda Generación (2G)
* **Tecnología:** Digital (GSM, GPRS - 2.5G, EDGE - 2.75G).
* **Características:** Introdujo la voz digital, el encriptado y los mensajes SMS/MMS. Con GPRS y EDGE aparecieron las primeras conexiones a datos a velocidades muy reducidas (kbps).
* **Impacto en desarrollo:** Nacimiento del desarrollo móvil sencillo mediante J2ME (Java 2 Micro Edition) y páginas WAP básicas.

### 1.3 Tercera Generación (3G)
* **Tecnología:** UMTS, HSDPA, HSUPA (H+).
* **Características:** Velocidades de transmisión desde 384 kbps hasta varios Mbps. Se posibilitó el uso fluido del correo electrónico, la navegación web real y la transmisión de vídeo.
* **Impacto en desarrollo:** **Eclosión de los Smartphones**. Nacen iOS y Android. Aparecen las tiendas de aplicaciones (*App Store*, *Android Market*) y el modelo de negocio actual de desarrollo software.

### 1.4 Cuarta Generación (4G)
* **Tecnología:** LTE y LTE-Advanced.
* **Características:** Basada 100% en el protocolo IP. Altas velocidades de descarga (hasta 1 Gbps) y bajas latencias. Soporte para vídeo en alta definición (HD/4K), llamadas de voz sobre IP (VoIP) y juegos en tiempo real.
* **Impacto en desarrollo:** Auge de aplicaciones complejas en la nube, streaming, redes sociales ricas en contenido interactivo y geolocalización avanzada.

### 1.5 Quinta Generación (5G)
* **Tecnología:** 5G NR (New Radio).
* **Características:** Latencias extremadamente bajas (< 1 ms), velocidades teóricas de hasta 10-20 Gbps y capacidad de conectar millones de dispositivos por $\text{km}^2$.
* **Impacto en desarrollo:** Desarrollo orientado a IoT (Internet de las Cosas), Realidad Aumentada (AR), Realidad Virtual (VR), conducción autónoma e integración de Modelos de Inteligencia Artificial ejecutable en la nube/dispositivo.

---

## 2. HISTORIA Y VERSIONES DEL SISTEMA OPERATIVO ANDROID

Android es un sistema operativo móvil basado en el Kernel de Linux, creado por Android Inc. (fundada por Andy Rubin en 2003) y posteriormente adquirido por Google en octubre de 2005.

### 2.1 Filosofía del Proyecto
Google lanzó la **Open Handset Alliance (OHA)** en 2007, una alianza de empresas de hardware, software y telecomunicaciones dedicada a impulsar un estándar abierto en dispositivos móviles. El código fuente principal de Android se distribuye bajo el proyecto **AOSP (Android Open Source Project)** con licenciamiento de código abierto.

![Figura 2. Logotipo de Android y evolución estética del sistema](/images/figura_2_android_history.png)  
*Descripción de la Figura 2: Evolución gráfica del logotipo e identidad visual de Android a lo largo de las distintas versiones.*

### 2.2 Cuadro Histórico de Versiones y Nivel de API (API Level)

En Android, la compatibilidad del código no solo se gestiona por el nombre de la versión comercial, sino fundamentalmente a través del **API Level** (número entero que identifica la revisión de la plataforma).

| Nombre en Clave | Versión de Android | API Level | Hito / Novedad Destacada |
| :--- | :--- | :--- | :--- |
| *(Sin nombre dulce)* | 1.0 / 1.1 | 1 / 2 | Primera versión comercial, integración con servicios Google. |
| **Cupcake** | 1.5 | 3 | Teclado en pantalla, widgets, soporte para vídeos. |
| **Donut** | 1.6 | 4 | Búsqueda por voz, soporte para diferentes resoluciones de pantalla. |
| **Eclair** | 2.0 / 2.1 | 5 - 7 | Navegación GPS paso a paso (Google Maps), fondos animados. |
| **Froyo** | 2.2 | 8 | Compilador JIT en Dalvik, tethering por Wi-Fi. |
| **Gingerbread** | 2.3 | 9 - 10 | Soporte NFC, interfaz optimizada, gestión de batería mejorada. |
| **Honeycomb** | 3.0 - 3.2 | 11 - 13 | Exclusivo para tablets, introducción de la ActionBar y Fragments. |
| **Ice Cream Sandwich**| 4.0 | 14 - 15 | Unificación de móviles y tablets, diseño *Holo*, desbloqueo facial. |
| **Jelly Bean** | 4.1 - 4.3 | 16 - 18 | *Project Butter* (60 fps), Google Now, notificaciones enriquecidas. |
| **KitKat** | 4.4 | 19 - 20 | Optimización de memoria RAM (512 MB), modo inmersivo. |
| **Lollipop** | 5.0 - 5.1 | 21 - 22 | **Material Design**, introducción de ART (Android Runtime) definitivo. |
| **Marshmallow** | 6.0 | 23 | **Permisos en tiempo de ejecución**, modo *Doze* de ahorro energético. |
| **Nougat** | 7.0 - 7.1 | 24 - 25 | Multiventana nativa, respuesta rápida en notificaciones, Vulkan API. |
| **Oreo** | 8.0 - 8.1 | 26 - 27 | Project Treble, modo Picture-in-Picture (PiP), límites en segundo plano. |
| **Pie** | 9.0 | 28 | Navegación por gestos, Batería Inteligente, recorte de pantalla (notch). |
| **Android 10** | 10.0 | 29 | Tema oscuro global, fin de nombres de postres públicos, permisos de ubicación mejorados. |
| **Android 11** | 11.0 | 30 | Burbujas de conversación, grabación de pantalla nativa, permisos de un solo uso. |
| **Android 12 / 12L**| 12.0 | 31 - 32 | **Material You** (colores dinámicos), panel de privacidad (*Privacy Dashboard*). |
| **Android 13** | 13.0 | 33 | Idiomas por aplicación, selector de fotos independiente, permiso de notificaciones. |
| **Android 14** | 14.0 | 34 | Personalización de pantalla de bloqueo, mejoras en accesibilidad y eficiencia. |
| **Android 15** | 15.0 | 35 | Espacio privado (*Private Space*), edge-to-edge por defecto, optimizaciones para la IA. |

---

## 3. GLOSARIO DE CONCEPTOS FUNDAMENTALES DE ANDROID

Para dominar el desarrollo móvil con Android es imprescindible entender los conceptos de la infraestructura y el kit de herramientas del sistema:

### 3.1 SDK (Software Development Kit)
Conjunto de bibliotecas, herramientas de compilación, emuladores y documentación técnica necesarios para escribir, depurar y empaquetar aplicaciones Android.

### 3.2 API Level
Número entero único que representa la revisión de la API de framework proporcionada por la plataforma. Permite al desarrollador definir la compatibilidad mínima (`minSdk`), objetivo (`targetSdk`) y de compilación (`compileSdk`).

### 3.3 Kernel de Linux
Es la capa base sobre la que se asienta Android. Se encarga de la gestión de memoria de bajo nivel, gestión de procesos, controladores de hardware (cámara, Wi-Fi, bluetooth) y seguridad/aislamiento de aplicaciones.

![Figura 3. Arquitectura del sistema Android](/images/figura_3_arquitectura_android.png)  
*Descripción de la Figura 3: Diagrama por capas que muestra desde el Kernel de Linux en la base, HAL, Librerías nativas/Android Runtime, Framework de aplicaciones Java/Kotlin y Aplicaciones en la capa superior.*

### 3.4 Entorno de Ejecución: Dalvik vs ART
* **Dalvik (Legacy):** Ejecutaba código bytecode Dalvik (`.dex`). Utilizaba compilación **JIT (Just-In-Time)**, traduciendo el código a instrucciones de máquina en tiempo real durante la ejecución, lo que consumía más batería y CPU.
* **ART (Android Runtime - Actual):** Utiliza compilación **AOT (Ahead-Of-Time)** combinada con JIT e IA. El código se compila a lenguaje máquina nativo durante la instalación de la app, mejorando sustancialmente el rendimiento y reduciendo el consumo energético.

### 3.5 APK vs AAB (Android App Bundle)
* **APK (Android Package):** Fichero comprimido ejecutable distribuido a los dispositivos finales.
* **AAB (Android App Bundle):** Formato oficial de publicación en Google Play Store. El paquete incluye todos los recursos y código de la app, pero la Play Store compila y entrega dinámicamente solo el APK optimizado para el procesador, idioma y densidad de pantalla del dispositivo que lo descarga.

### 3.6 Gradle
Herramienta de automatización de compilación (*build system*) utilizada por Android Studio. Permite gestionar dependencias externas, configurar variantes de compilación (*build types*, *flavors*) y empaquetar la app. En proyectos modernos se escribe en **Kotlin DSL** (`build.gradle.kts`).

---

## 4. COMPONENTES PRINCIPALES DE UNA APLICACIÓN ANDROID

Cualquier aplicación Android se construye combinando cuatro bloques fundamentales del sistema, junto con otros elementos auxiliares de interfaz y lógica.

![Figura 4. Componentes fundamentales del ecosistema Android](/images/figura_4_componentes_android.png)  
*Descripción de la Figura 4: Relación estructural entre Activities, Services, Broadcast Receivers y Content Providers.*

### 4.1 Componentes de Aplicación (Core Components)

1. **Activities (Actividades):**
   * Representan una única pantalla con la que el usuario puede interactuar.
   * Contienen la interfaz de usuario (View/Layout) y la lógica de negocio asociada.
2. **Services (Servicios):**
   * Componentes que se ejecutan en segundo plano sin ofrecer una interfaz gráfica de usuario.
   * Ejemplos: Descarga de ficheros pesados, reproducción de música en segundo plano.
3. **Broadcast Receivers (Receptores de Emisiones):**
   * Escuchan y responden a anuncios o eventos globales emitidos por el sistema o por otras aplicaciones.
   * Ejemplos: Evento de batería baja, cambio a modo avión, recepción de un SMS.
4. **Content Providers (Proveedores de Contenido):**
   * Administran un conjunto compartido de datos de la aplicación para exponerlos a otras apps de forma segura (mediante una interfaz estándar tipo URI).
   * Ejemplos: Acceso a la agenda de contactos del sistema o a la galería de imágenes.

### 4.2 Componentes Auxiliares
* **Intents:** Mensajes o peticiones que comunican componentes entre sí (por ejemplo, iniciar una Activity o enviar un evento).
* **Fragments:** Porciones modulares y reutilizables de interfaz gráfica dentro de una misma Activity.
* **Views y Layouts:** Elementos visuales gráficos (Botones, Textos) y contenedores de estructura (Columnas, Filas, ConstraintLayout).

---

## 5. ENTORNO DE DESARROLLO: ANDROID STUDIO Y AVD

Android Studio es el IDE oficial para el desarrollo de aplicaciones Android, basado en **IntelliJ IDEA** de JetBrains.

![Figura 5. Interfaz principal del entorno Android Studio](/images/figura_5_android_studio_ide.png)  
*Descripción de la Figura 5: Captura de pantalla de la ventana principal de Android Studio en la que se distingue el panel de proyecto a la izquierda, editor central y herramientas inferiores como Logcat.*

### 5.1 Características Principales de Android Studio
* Integración nativa con Gradle.
* Editor de interfaz gráfico avanzado para diseños basados en XML o vistas previas en tiempo real para Jetpack Compose (`@Preview`).
* **Logcat:** Consola centralizada para depuración, filtrado de logs y volcado de errores del sistema (`Log.d()`, `Log.e()`, etc.).
* **Profiler:** Herramienta para monitorizar el rendimiento del CPU, memoria RAM, batería y red en tiempo real.

### 5.2 Configuración del Emulador (AVD - Android Virtual Device)
El AVD Manager permite crear dispositivos virtuales simulando hardware real (smartphones, tablets, Android TV, Wear OS).

#### Optimización de Aceleración por Hardware:
Para que los emuladores funcionen a velocidad nativa, es imprescindible activar la virtualización en el procesador y configurar los motores de aceleración:
* **Procesadores Intel:** HAXM (*Hardware Accelerated Execution Manager*) o Intel Virtualization Technology (VT-x).
* **Procesadores AMD / Windows moderno:** Hyper-V o *Windows Hypervisor Platform* (WHPX).

### 5.3 Depuración en Dispositivo Físico Real
Para ejecutar y probar aplicaciones en un móvil físico, es necesario seguir los siguientes pasos:
1. Ir a **Ajustes > Información del teléfono** en el dispositivo.
2. Pulsar **7 veces consecutivas sobre el "Número de compilación"** para activar las **Opciones de desarrollo**.
3. En Opciones de desarrollo, activar la casilla **Depuración por USB**.
4. Conectar el móvil al PC mediante cable de datos y autorizar la clave RSA en la pantalla del teléfono.

---

## 6. ESTRUCTURA DE UN PROYECTO EN ANDROID Y ANDROIDMANIFEST.XML

Al crear un nuevo proyecto en Android Studio, la estructura de directorios se organiza de una forma lógica para diferenciar el código fuente, los recursos estáticos y los scripts de compilación.

![Figura 6. Estructura de carpetas en vista "Android" dentro de Android Studio](/images/figura_6_estructura_proyecto.png)  
*Descripción de la Figura 6: Vista lógica del árbol del proyecto mostrando las carpetas manifests, java y res.*

### 6.1 Directorios Principales
* `manifests/`: Contiene el archivo clave **`AndroidManifest.xml`**.
* `java/` (o `kotlin/`): Contiene los paquetes de código fuente de la aplicación (`.kt` o `.java`), además de las carpetas para pruebas unitarias (`test/`) e instrumentadas (`androidTest/`).
* `res/`: Recursos estáticos de la aplicación:
  * `drawable/`: Imágenes (PNG, JPG, Vector XML).
  * `layout/`: Diseños visuales definidos en XML (modo clásico).
  * `mipmap/`: Iconos de la aplicación en distintas densidades ($mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi$).
  * `values/`: Constantes globales como cadenas (`strings.xml`), colores (`colors.xml`) y temas (`themes.xml`).
* `Gradle Scripts/`: Ficheros de configuración del sistema de construcción (`build.gradle.kts` a nivel de proyecto y a nivel de módulo `app`).

---

### 6.2 El Archivo AndroidManifest.xml
Es el fichero fundamental de configuración de cualquier aplicación Android. Describe la estructura de la app al sistema operativo antes de ejecutarse.

#### Ejemplo Exhaustivo de un `AndroidManifest.xml`:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="es.santiagorodenas.miaplicacion">

    <!-- Declaración de Permisos Requeridos por la Aplicación -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.CALL_PHONE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MiAplicacion"
        tools:targetApi="34">

        <!-- Declaración de la Actividad Principal y Filtro de Lanzamiento -->
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:label="@string/app_name"
            android:theme="@style/Theme.MiAplicacion">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- Declaración de Actividades Secundarias -->
        <activity 
            android:name=".DetailActivity"
            android:exported="false" />

    </application>

</manifest>
```

---

## 7. EL CICLO DE VIDA DE UNA ACTIVITY

Una **Activity** no permanece abierta indefinidamente ni controla de forma absoluta su propio destino. El sistema operativo Android gestiona la memoria destruyendo o pausando actividades según las necesidades del dispositivo (llamadas entrantes, falta de RAM, rotación de pantalla).

![Figura 7. Diagrama oficial del Ciclo de Vida de una Activity](/images/figura_7_ciclo_vida_activity.png)  
*Descripción de la Figura 7: Diagrama de estados y funciones callback correspondientes: onCreate(), onStart(), onResume(), onPause(), onStop(), onDestroy() y onRestart().*

### 7.1 Métodos Callback del Ciclo de Vida

1. **`onCreate(SavedInstanceState: Bundle?)`**
   * **Cuándo ocurre:** Al crear por primera vez la actividad.
   * **Uso:** Configuración inicial, inflar la interfaz (`setContentView` o Compose), inicializar variables y vincular datos.
2. **`onStart()`**
   * **Cuándo ocurre:** La Activity pasa a ser visible para el usuario.
   * **Uso:** Preparar elementos visuales que deben actualizarse en pantalla.
3. **`onResume()`**
   * **Cuándo ocurre:** La Activity pasa al primer plano y obtiene el foco interactivo (el usuario puede interactuar con ella).
   * **Uso:** Iniciar animaciones, encender la cámara, capturar sensores.
4. **`onPause()`**
   * **Cuándo ocurre:** Otra actividad pasa al frente, dejando a la actual visible pero parcialmente cubierta o perdiendo el foco (ej. emergente o llamada).
   * **Uso:** Pausar animaciones, detener acciones de CPU pesadas, guardar cambios no persistidos.
5. **`onStop()`**
   * **Cuándo ocurre:** La Activity ya no es totalmente visible para el usuario.
   * **Uso:** Liberar recursos pesados, guardar datos en la base de datos local.
6. **`onDestroy()`**
   * **Cuándo ocurre:** La Activity va a ser destruida por completo (al pulsar atrás o por falta de memoria del sistema).
   * **Uso:** Limpieza total de recursos y subprocesos.
7. **`onRestart()`**
   * **Cuándo ocurre:** La Activity pasa de estar en estado *Stopped* a reactivarse de nuevo antes de volver a llamar a `onStart()`.

---

### 7.2 Conservación del Estado de la Interfaz (`Bundle`)
Cuando ocurre un cambio de configuración (como **rotar la pantalla**), Android destruye y vuelve a crear la Activity por defecto. Para evitar la pérdida de datos introducidos por el usuario:

```kotlin
class MainActivity : AppCompatActivity() {

    private var contador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Restaurar estado si viene de una recreación
        if (savedInstanceState != null) {
            contador = savedInstanceState.getInt("KEY_CONTADOR", 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar el dato clave-valor antes de la destrucción de la Activity
        outState.putInt("KEY_CONTADOR", contador)
    }
}
```

---

## 8. GESTIÓN DE EVENTOS E INTERACCIÓN EN LA INTERFAZ

Para reaccionar a los toques del usuario sobre los botones u otros componentes visuales, el sistema ofrece varios enfoques de control de eventos.

![Figura 8. Interacción de eventos mediante Listener en Kotlin](/images/figura_8_gestion_eventos.png)  
*Descripción de la Figura 8: Diagrama del flujo entre la interacción del usuario con un View (Button) y la captura del evento vía Listener.*

### 8.1 Métodos de Implementación de Escuchadores (`OnClickListener`)

#### Opción A: Expresión Lambda en Kotlin (Recomendada y Moderna)
Es la forma más limpia y estándar en el desarrollo actual.

```kotlin
val btnAceptar = findViewById<Button>(R.id.btnAceptar)
btnAceptar.setOnClickListener { 
    Toast.makeText(this, "¡Botón pulsado correctamente!", Toast.LENGTH_SHORT).show()
}
```

#### Opción B: Implementar la Interfaz en la Activity

```kotlin
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnUno = findViewById<Button>(R.id.btnUno)
        val btnDos = findViewById<Button>(R.id.btnDos)

        btnUno.setOnClickListener(this)
        btnDos.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnUno -> { /* Acción Botón 1 */ }
            R.id.btnDos -> { /* Acción Botón 2 */ }
        }
    }
}
```

---

## 9. INTENTS (NAVEGACIÓN) Y GESTIÓN DE PERMISOS

Un **Intent** es el mecanismo fundamental de comunicación de Android para solicitar acciones a otros componentes.

![Figura 9. Diferencia conceptual entre Intents Explícitos e Implícitos](/images/figura_9_intents.png)  
*Descripción de la Figura 9: Diagrama comparativo donde un Intent explícito apunta directamente a un componente concreto de la app, mientras que el implícito consulta al sistema operativo para encontrar aplicaciones capaces de realizar la acción.*

### 9.1 Intents Explícitos
Se utilizan para navegar entre pantallas (Activities) dentro de la **misma aplicación**.

#### Código para Navegar y Enviar Datos:
```kotlin
// Desde MainActivity.kt
val intent = Intent(this, DetailActivity::class.java).apply {
    putExtra("EXTRA_USUARIO", "Santiago Rodenas")
    putExtra("EXTRA_EDAD", 25)
}
startActivity(intent)
```

#### Código para Recibir los Datos:
```kotlin
// En DetailActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)

    val nombre = intent.getStringExtra("EXTRA_USUARIO")
    val edad = intent.getIntExtra("EXTRA_EDAD", 0)
}
```

---

### 9.2 Intents Implícitos
No especifican la clase exacta de destino; en su lugar, declaran una **Acción General** (`ACTION_VIEW`, `ACTION_DIAL`, etc.) para que el sistema busque qué aplicaciones instaladas pueden responder a esa petición.

```kotlin
// Ejemplo: Abrir una página web en el navegador del dispositivo
val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
startActivity(urlIntent)

// Ejemplo: Abrir el marcador telefónico con un número preparado
val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:961234567"))
startActivity(dialIntent)
```

---

### 9.3 Permisos en Tiempo de Ejecución (Runtime Permissions)
Desde **Android 6.0 (API Level 23 - Marshmallow)**, los permisos considerados "peligrosos" (acceso a cámara, ubicación, llamadas, almacenamiento) no solo deben declararse en el `AndroidManifest.xml`, sino que **deben ser solicitados explícitamente al usuario en tiempo de ejecución**.

#### Flujo Completo para Realizar una Llamada Telefónica Directa (`ACTION_CALL`):

1. **Declaración en `AndroidManifest.xml`:**
   ```xml
   <uses-permission android:name="android.permission.CALL_PHONE" />
   ```

2. **Verificación y Solicitud en la Activity (`MainActivity.kt`):**
   ```kotlin
   import android.Manifest
   import android.content.Intent
   import android.content.pm.PackageManager
   import android.net.Uri
   import android.os.Bundle
   import android.widget.Button
   import android.widget.Toast
   import androidx.appcompat.app.AppCompatActivity
   import androidx.core.app.ActivityCompat
   import androidx.core.content.ContextCompat

   class MainActivity : AppCompatActivity() {

       private val CALL_PERMISSION_CODE = 101

       override fun onCreate(savedInstanceState: Bundle?) {
           super.onCreate(savedInstanceState)
           setContentView(R.layout.activity_main)

           val btnLlamar = findViewById<Button>(R.id.btnLlamar)
           btnLlamar.setOnClickListener {
               hacerLlamadaDirecta("961234567")
           }
       }

       private fun hacerLlamadaDirecta(numero: String) {
           // 1. Comprobar si el permiso ya está concedido
           if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
               != PackageManager.PERMISSION_GRANTED) {

               // 2. Si no está concedido, solicitarlo al usuario mediante diálogo del sistema
               ActivityCompat.requestPermissions(
                   this,
                   arrayOf(Manifest.permission.CALL_PHONE),
                   CALL_PERMISSION_CODE
               )
           } else {
               // 3. Si ya tiene el permiso, ejecutar el Intent directo
               val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$numero"))
               startActivity(intent)
           }
       }

       // 4. Capturar la respuesta del usuario al diálogo de permisos
       override fun onRequestPermissionsResult(
           requestCode: Int,
           permissions: Array<out String>,
           grantResults: IntArray
       ) {
           super.onRequestPermissionsResult(requestCode, permissions, grantResults)

           if (requestCode == CALL_PERMISSION_CODE) {
               if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   Toast.makeText(this, "Permiso concedido. Realizando llamada...", Toast.LENGTH_SHORT).show()
                   hacerLlamadaDirecta("961234567")
               } else {
                   Toast.makeText(this, "Permiso denegado. No se puede realizar la llamada.", Toast.LENGTH_SHORT).show()
               }
           }
       }
   }
   ```

---

## 10. INTRODUCCIÓN AL DESARROLLO MODERNO CON JETPACK COMPOSE

**Jetpack Compose** es el kit de herramientas moderno recomendado por Google para construir interfaces de usuario nativas en Android. Sustituye el modelo clásico basado en XML por un paradigma **Declarativo**.

![Figura 10. Comparativa entre el modelo Imperativo (XML) y el Declarativo (Jetpack Compose)](/images/figura_10_compose_vs_xml.png)  
*Descripción de la Figura 10: Esquema ilustrativo que compara la manipulación explícita del árbol de vistas en XML frente a la emisión automática de la interfaz mediante funciones Composable según el Estado.*

### 10.1 Conceptos Clave de Jetpack Compose
* **Paradigma Declarativo:** La interfaz describe *cómo debe verse* la pantalla para un estado determinado, en lugar de instruir paso a paso cómo modificar las vistas.
* **Funciones `@Composable`:** Funciones de Kotlin anotadas con `@Composable` que emiten elementos de interfaz gráfica.
* **Estado (`State`):** Los datos que determinan lo que muestra la UI. Cuando el estado cambia, Compose ejecuta una **Recomposición** automáticamente para redibujar solo las partes de la UI afectadas.

---

### 10.2 Contenedores Básicos de Estructura
* **`Column`:** Modificador de diseño equivalente a un `LinearLayout` vertical.
* **`Row`:** Modificador de diseño equivalente a un `LinearLayout` horizontal.
* **`Box`:** Apila elementos uno encima de otro (similar a un `FrameLayout`).

---

### 10.3 Ejemplo Completo de Interfaz Interactiva en Jetpack Compose

```kotlin
package es.santiagorodenas.miaplicacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Sustituye a setContentView(R.layout...)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaContador()
                }
            }
        }
    }
}

@Composable
fun PantallaContador() {
    // Declaración del Estado persistente ante recomposiciones
    var contador by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Has pulsado: $contador veces",
            fontSize = 22.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { contador++ }
        ) {
            Text(text = "Incrementar Contador")
        }
    }
}

// Vista previa en tiempo real en Android Studio sin necesidad de emulador
@Preview(showBackground = true)
@Composable
fun PreviewPantallaContador() {
    MaterialTheme {
        PantallaContador()
    }
}
```

---

## RESUMEN DE LA UNIDAD
* Android ha evolucionado impulsado por el avance de las redes móviles (de 1G a 5G) hasta convertirse en un sistema moderno basado en Linux y el entorno **ART**.
* Toda aplicación se estructura mediante 4 componentes principales: **Activities**, **Services**, **Broadcast Receivers** y **Content Providers**, declarados en el archivo central **`AndroidManifest.xml`**.
* Las Activities se rigen por un **Ciclo de Vida** controlado por el SO (`onCreate`, `onResume`, `onPause`, etc.), siendo crucial gestionar la persistencia de datos mediante `Bundle`.
* La navegación se implementa con **Intents** (Explícitos para pantallas propias e Implícitos para acciones del sistema), recordando solicitar **Permisos en tiempo de ejecución** desde API 23+.
* La tendencia moderna sustituye los diseños clásicos XML por **Jetpack Compose**, basado en programación declarativa orientada a estados y funciones `@Composable`.