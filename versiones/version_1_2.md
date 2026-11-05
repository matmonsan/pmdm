---
title: Versión 1.2
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al enunciado del proyecto](../enunciado_proyecto.md)

# **FECHA DE ENTREGA:** 15/11/2026

# Versión 1.2 — CRUD en memoria y repositorios

## Objetivo

Ampliar la lista y el detalle de la Versión 1.1 para que el usuario pueda gestionar sus elementos mediante un CRUD completo en memoria, usando diálogos para los formularios y las confirmaciones, y una separación básica entre la interfaz y el acceso a los datos.

## Enunciado

Partid de las pantallas de lista y detalle de la Versión 1.1 y ampliadlas para que el usuario pueda gestionar sus elementos:

1. **Pantalla «Mis elementos» (`PantallaPrincipal.kt`):** muestra los elementos del usuario y un botón visible (por ejemplo, un `FloatingActionButton`) para añadir uno nuevo.
2. **Pantalla de detalle (`PantallaDetalle.kt`):** muestra todos los datos de un elemento y ofrece acciones para volver, editarlo o eliminarlo.
3. **Diálogo de creación y edición:** presenta el formulario en un `AlertDialog`; puede ser el mismo diálogo para ambas operaciones, cambiando su título y sus valores iniciales.
4. **Diálogo de confirmación de borrado:** pide confirmación antes de eliminar un elemento.

Adaptad el modelo y los formularios a la temática de la aplicación. Los datos deben gestionarse mediante un repositorio en memoria, sin base de datos ni servicio remoto en esta versión.

No se pide ninguna pantalla nueva: se mantienen la lista y el detalle de la Versión 1.1, y el cambio entre ambas se hace con estado de Compose. Las altas y las ediciones se hacen en diálogos sobre la pantalla actual. La navegación formal con `NavHost` llegará en la Versión 1.4.

Material de referencia en la UD3:

- [Apartado 5](../ud3.md#5-navegación-entre-pantallas-con-estado): navegación entre pantallas con estado, con un archivo por pantalla.
- [Apartado 8.5](../ud3.md#85-lista-y-detalle-editar-y-borrar-desde-el-detalle): ejemplo completo de lista y detalle con repositorio, ViewModel, diálogos de alta, edición y borrado, y `Snackbar`. Sirve de referencia para esta versión; el uso del ViewModel es opcional (ver más abajo).

Estructura orientativa del proyecto (adaptad los nombres a vuestra temática):

```text
├── Elemento.kt              ← data class del modelo
├── ElementoRepository.kt    ← repositorio en memoria con el CRUD
├── PantallaPrincipal.kt     ← lista y botón para añadir
├── PantallaDetalle.kt       ← datos del elemento y botones Volver, Editar y Eliminar
├── Dialogos.kt              ← formulario y confirmación de borrado
├── AppPantallas.kt          ← decide qué pantalla se ve y muestra los diálogos
└── MainActivity.kt          ← setContent { AppPantallas() }
```

El uso de un `ViewModel` con `UiState` (UD3, apartado 6) es recomendable, pero no obligatorio en esta versión: se exigirá en la Versión 1.6.

## Requisitos

- Definid una `data class` para representar los elementos administrados por el usuario, con atributos apropiados a la temática.
- Implementad un repositorio en memoria que proporcione los elementos y permita añadirlos, actualizarlos y eliminarlos.
- Mostrad los elementos del repositorio en la pantalla «Mis elementos» y reflejad inmediatamente en la lista los cambios realizados durante la ejecución.
- Permitid abrir el detalle de cada elemento desde la lista y volver a ella. El detalle debe ofrecer acciones para editar y eliminar ese elemento.
- Gestionad el cambio entre la lista y el detalle con estado de Compose, sin `NavHost`: una variable de estado (por ejemplo, el `id` del elemento seleccionado) decide qué pantalla se muestra. Las pantallas deben recibir los datos que necesitan y avisar con eventos (`onItemClick`, `onVolver`, `onEditClick`, `onDeleteClick`...) en lugar de cambiar ellas mismas esa variable.
- Organizad cada pantalla en su propio archivo `.kt` (`PantallaPrincipal.kt` y `PantallaDetalle.kt`) y la elección de la pantalla visible en otro archivo (`AppPantallas.kt`), al que llama `MainActivity`. Las pantallas no deben conocerse entre sí: solo `AppPantallas` decide cuál se muestra.
- Desde el detalle se debe poder volver a la lista tanto con una acción visible en la pantalla como con el botón o gesto Atrás del sistema (`BackHandler`), sin que se cierre la aplicación.
- Añadid un diálogo de creación accesible desde la lista y un diálogo de edición accesible desde el detalle. El formulario debe permitir introducir o modificar los datos principales definidos en el modelo.
- Tras editar, el detalle debe mostrar los datos actualizados.
- Validad los campos obligatorios antes de guardar y mostrad mensajes comprensibles junto a los campos cuando falten datos o sean incorrectos. Al cancelar, cerrad el diálogo sin aplicar cambios.
- Pedid confirmación antes de eliminar. Al confirmar, eliminad el elemento y regresad a la lista actualizada; al cancelar, conservadlo.
- Mantened la interfaz construida con Jetpack Compose y adaptada al prototipo del proyecto.
- No es necesario conservar los datos al cerrar la aplicación; Room, Firebase y la API se incorporarán en versiones posteriores.

## Criterios de finalización

- Desde la lista se abre el detalle de cada elemento y se puede volver a la lista, también con el botón Atrás del sistema, sin que se cierre la aplicación.
- El cambio de pantalla se gestiona con estado de Compose, sin `NavHost`, y cada pantalla está en su propio archivo `.kt`.
- Se pueden crear elementos desde la lista y verlos en ella; desde el detalle se pueden editar o eliminar.
- Las operaciones de creación, modificación y borrado se reflejan en el repositorio y en las pantallas sin reiniciar la aplicación.
- Los formularios validan la entrada, cancelar un diálogo no guarda cambios y el borrado requiere confirmación.
- El acceso a los datos está separado de la interfaz mediante un repositorio.
- La aplicación compila y las operaciones se pueden demostrar en un emulador o dispositivo.

## Entrega

Entregad el código actualizado en el repositorio Git del proyecto. Añadid un PDF con capturas de la lista, el detalle y los diálogos (creación, edición con un error de validación y confirmación de borrado), y una descripción de los cambios; adjuntadlo a la actividad correspondiente de Moodle. Incluid también la URL del repositorio y un vídeo breve que muestre las operaciones de crear, consultar, editar y eliminar un elemento. Consultad los requisitos comunes de entrega en el [enunciado general](../enunciado_proyecto.md).
