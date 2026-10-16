---
title: Proyecto Individual de Desarrollo Android
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al inicio](index.md)

A lo largo del curso desarrollaréis una aplicación Android individual. Podéis elegir libremente la temática: viajes, recetas, deporte, música, mascotas, comercio o cualquier otra propuesta que os interese.

El proyecto será evolutivo. No tendréis que construirlo todo desde el primer día: cada entrega añadirá una funcionalidad nueva sobre la anterior. Al final, la aplicación tendrá pantallas en Jetpack Compose, datos propios de cada usuario, un CRUD completo, cámara, multimedia, ubicación y conexión con una API REST.

La interfaz se construirá siempre con funciones `@Composable` y componentes de Jetpack Compose. Se utilizarán layouts como `Column`, `Row` y `Box`, modificadores, temas y componentes de Material como `Card`, `ElevatedCard` u `OutlinedCard`. No se utilizarán diseños XML para construir las pantallas.

En clase se explicarán las tecnologías necesarias y se proporcionará código de apoyo. Ese código deberá adaptarse a la temática y a las decisiones de cada proyecto.

> Importante: las funcionalidades se incorporarán de forma gradual. En cada versión solo será necesario implementar los objetivos indicados para ese momento; las funcionalidades más avanzadas se añadirán en las versiones posteriores.

---

## ¿Qué aplicación hay que construir?

La aplicación tendrá dos partes que se desarrollarán progresivamente:

1. Aplicación Android: será la parte que utiliza el usuario. Mostrará las pantallas, listas, formularios, imágenes, botones y mapas.
2. Servidor y base de datos: será la parte que guarda los datos y permite que la aplicación se comunique con una API REST.

La temática es libre, pero la aplicación final debe cumplir los requisitos que se detallan a continuación.

### 1. Aplicación Android con Jetpack Compose

#### Datos de la aplicación

La temática y el tipo de información serán libres. Cada estudiante podrá elegir los nombres y las características que mejor encajen con su propuesta: libros, recetas, viajes, eventos, productos, mascotas, lugares u otras opciones.

Como requisitos mínimos:

- La información principal de la aplicación se organizará mediante una o varias `data class` de Kotlin.
- El modelo de datos tendrá varios atributos adecuados a la temática. Como referencia, los elementos administrados por el usuario incluirán al menos cuatro datos y una imagen o fotografía, además de una descripción detallada.
- La aplicación distinguirá entre información común o pública y la información que cada usuario puede administrar. La forma de presentar estas dos partes podrá adaptarse a la temática elegida.
- El usuario podrá crear, consultar, modificar y eliminar los elementos que le correspondan. Este conjunto de acciones forma el CRUD.

#### Pantallas y navegación

La organización de las pantallas podrá variar según la propuesta, pero la aplicación final deberá contemplar un flujo completo de acceso y navegación:

- Una pantalla que permitirá registrarse o iniciar sesión.
- Después del acceso se mostrará una pantalla principal adaptada a la temática del proyecto.
- La aplicación incluirá un menú lateral (`ModalNavigationDrawer`) con la información básica del usuario, como su fotografía, nombre y correo.
- También tendrá una navegación inferior (`NavigationBar`) o una solución equivalente de Compose para alternar entre las principales áreas de la aplicación, incluyendo la información común y la información administrada por el usuario.
- Al seleccionar un elemento se abrirá una pantalla de detalle con la información que corresponda.
- La navegación se realizará con Jetpack Navigation Compose (`NavHost`) y permitirá volver a la pantalla anterior.

#### Componentes y diseño

Los componentes concretos y la distribución visual se elegirán según la temática, respetando estos mínimos:

- Los elementos que se muestren en listas utilizarán tarjetas de Material (`Card`, `ElevatedCard` u `OutlinedCard`) o un componente equivalente de Compose.
- Las listas utilizarán componentes eficientes de Compose, como `LazyColumn` o `LazyVerticalGrid`, cuando el número de elementos lo requiera.
- La aplicación tendrá una pantalla inicial o *splash* con un vídeo o audio de presentación y una opción para saltarlo.
- En la pantalla de creación o edición se podrá utilizar la cámara para capturar una imagen relacionada con el elemento.
- La aplicación incluirá al menos un audio o vídeo que el usuario pueda reproducir y detener mediante controles de *Play* y *Stop*. El contenido multimedia será de consulta y no editable.
- Los elementos podrán incluir una posición GPS cuando tenga sentido para la temática. En ese caso, la pantalla de detalle mostrará su ubicación en un mapa.

#### Organización interna

La aplicación se organizará progresivamente con:

- MVVM, separando los datos, la lógica y las pantallas.
- Clean Architecture, separando las responsabilidades principales del proyecto.
- `StateFlow` y `UiState`, para representar de forma reactiva el estado de la interfaz.
- Hilt, para facilitar la inyección de dependencias.
- Room, para guardar los datos localmente.
- Preferences DataStore o `SharedPreferences`, para conservar la sesión iniciada.

### 2. Servidor y API REST

En la última parte del proyecto la aplicación dejará de trabajar solo con datos locales y se conectará a un servidor propio.

- La base de datos será MariaDB.
- El servidor podrá desarrollarse con PHP o Ktor.
- La API tendrá, como mínimo, estos grupos de operaciones:
  - `/auth`: iniciar sesión.
  - `/register`: registrar usuarios.
  - `/<item_usuario>`: consultar, crear, modificar y eliminar elementos del usuario mediante `GET`, `POST`, `PUT` y `DELETE`.
  - `/<item_comun>`: consultar los elementos comunes mediante `GET`.
- Las imágenes se guardarán físicamente en el servidor. En la base de datos se almacenará la URL de cada imagen.
- Se entregará un archivo `docker-compose.yml` para levantar localmente el servidor web y MariaDB.
- El despliegue en un servicio cloud como VPS, Render o Railway será opcional.
- La sesión final utilizará un token JWT guardado en Preferences DataStore.

---

## Cronología de Versiones y Plan de Entregas

Cada versión añade una pieza al proyecto. No se trata de hacer once aplicaciones distintas: se conserva la misma aplicación y se amplía en cada entrega. Cuando una versión reutiliza una tecnología anterior, se espera que el trabajo anterior siga funcionando.

El orden recomendado es el siguiente:

* [**Versión 1.0 (Diseño y Prototipado UX/UI)**](versiones/version_1_0.md): Definir la idea, las pantallas y el funcionamiento general antes de programar.
* [**Versión 1.1 (Listas en Compose)**](versiones/version_1_1.md): Crear la primera pantalla funcional y mostrar elementos temporales en una lista `LazyColumn`.
* [**Versión 1.2 (CRUD en memoria y repositorios)**](versiones/version_1_2.md): Añadir el alta, la consulta, la edición y el borrado de elementos usando datos en memoria.
* [**Versión 1.3 (Autenticación con Firebase)**](versiones/version_1_3.md): Incorporar registro, inicio de sesión y recuperación de contraseña con Firebase Auth.
* [**Versión 1.4 (Estructura de navegación)**](versiones/version_1_4.md): Conectar las pantallas mediante `ModalNavigationDrawer`, `NavigationBar` y `NavHost`.
* [**Versión 1.5 (Lista global y datos compartidos)**](versiones/version_1_5.md): Añadir una segunda lista con elementos comunes para todos los usuarios.
* [**Versión 1.6 (Arquitectura MVVM + Hilt)**](versiones/version_1_6.md): Organizar el código con MVVM, `StateFlow`, `UiState` y Hilt.
* [**Versión 2.0 (Persistencia local con Room)**](versiones/version_2_0.md): Sustituir los datos temporales por una base de datos local Room.
* [**Versión 3.1 (Cámara y multimedia)**](versiones/version_3_1.md): Añadir la captura de imágenes y un reproductor de audio o vídeo.
* [**Versión 3.2 (Geolocalización)**](versiones/version_3_2.md): Guardar las coordenadas de los elementos y mostrar su ubicación en un mapa.
* [**Versión 4.1 (API REST completa)**](versiones/version_4_1.md): Conectar la aplicación con la API propia, sustituir Firebase Auth por `/auth` y `/register`, usar JWT y sincronizar las imágenes con el servidor.

---

## Calificación y Entregas por Trimestres

* **Primer trimestre:** entrega hasta la **Versión 1.5**.
* **Segundo trimestre:** entrega de la **Versión final 4.1**.

---

## Normativa de Evaluación de las Entregas

### Tareas del proyecto evaluables por unidad didáctica

Las siguientes versiones del proyecto se utilizarán como evidencias prácticas de las unidades didácticas relacionadas. Una misma versión puede servir para evaluar más de una unidad cuando incluya contenidos compartidos.


| Unidad didáctica | Tareas o versiones del proyecto que cuentan para la evaluación |
|---|---|
| **UD2: Layout de usuario** | Versión 1.0, con el prototipo de pantallas, y Versión 1.1, con la primera interfaz funcional en Jetpack Compose, sus layouts y modificadores. |
| **UD3: Fragmentos, diálogos y listado de elementos** | Versión 1.2, con el CRUD en memoria, los formularios y el listado de elementos. |
| **UD4: Navegación y menús** | Versión 1.3, con las pantallas de registro, inicio de sesión y recuperación de contraseña; Versión 1.4, con la navegación entre pantallas, el `Navigation Drawer` y la `NavigationBar`; y Versión 1.5, con las dos listas principales. |
| **UD5: Patrones de diseño** | Versión 1.6, con la organización del proyecto mediante MVVM, repositorios, `UiState`, `StateFlow` e inyección de dependencias con Hilt. |
| **UD6: Almacenamiento local con Room** | Versión 2.0, con la sustitución de los datos en memoria por una base de datos local Room; y Versión 3.2, con la navegación a la pantalla de detalle y la visualización de la ubicación. |
| **UD7: Servicios y conexión API REST mediante Retrofit** | Versión 4.1, con la conexión de la aplicación Android a la API REST, autenticación mediante JWT y sincronización de datos e imágenes. |
| **UD8: Utilización de librerías multimedia** | Versión 3.1, con la incorporación de la cámara y del reproductor de audio o vídeo mediante las librerías correspondientes. |


1. **Control de Versiones (Git):** Es **obligatorio** el uso de Git y la entrega mediante un repositorio remoto (GitHub, GitLab, etc.). Se evaluará el desarrollo incremental del proyecto a través del histórico de *commits* o etiquetas de versión (*tags*).
2. **Documentación PDF:** Junto al código, en cada entrega se adjuntará un documento PDF con capturas de pantalla de la aplicación en ejecución y una descripción detallada de los cambios e innovaciones de dicha versión.
3. **Vídeo Explicativo:** En la tarea habilitada en Moodle, se adjuntará obligatoriamente un enlace a un vídeo explicativo grabado mostrando el funcionamiento de la aplicación en un dispositivo físico o emulador. En la misma entrega se incluirá la URL del repositorio Git del proyecto.
   > Si el código subido al repositorio no coincide con la funcionalidad mostrada en el vídeo, la versión será evaluada automáticamente con una nota de 0.0.
4. **Política de Entregas Continuas:** La no entrega de más de **2 versiones contiguas** implicará el suspenso automático del módulo. No se permiten entregas fuera de plazo.