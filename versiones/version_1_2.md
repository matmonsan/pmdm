---
title: Versión 1.2
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al enunciado del proyecto](../enunciado_proyecto.md)

# **FECHA DE ENTREGA:** 15/11/2026

# Versión 1.2 — CRUD en memoria y repositorios

## Objetivo

Ampliar el recorrido de la Versión 1.1 con pantallas para consultar y gestionar elementos propios mediante un CRUD completo en memoria y una separación básica entre la interfaz y el acceso a los datos.

## Enunciado

Ampliad la aplicación para que el usuario pueda recorrer y gestionar sus elementos desde varias pantallas:

1. **Pantalla de inicio:** conserva el acceso al área de elementos.
2. **Pantalla «Mis elementos»:** muestra los elementos del usuario y una acción visible para añadir uno nuevo.
3. **Pantalla de detalle:** muestra todos los datos de un elemento y ofrece acciones para editarlo o eliminarlo.
4. **Pantalla de creación y edición:** presenta el formulario correspondiente; puede ser una misma pantalla que cambie según la operación.

Adaptad el modelo y los formularios a la temática de la aplicación. Los datos deben gestionarse mediante un repositorio en memoria, sin base de datos ni servicio remoto en esta versión. El flujo de pantallas puede gestionarse con estado de Compose; la navegación formal con `NavHost` llegará en la Versión 1.4.

## Requisitos

- Definid una `data class` para representar los elementos administrados por el usuario, con atributos apropiados a la temática.
- Implementad un repositorio en memoria que proporcione los elementos y permita añadirlos, actualizarlos y eliminarlos.
- Mostrad los elementos del repositorio en la pantalla «Mis elementos» y reflejad inmediatamente en la lista los cambios realizados durante la ejecución.
- Permitid abrir el detalle de cada elemento desde la lista. El detalle debe ofrecer acciones para editar y eliminar ese elemento.
- Añadid un formulario de creación accesible desde la lista y un formulario de edición accesible desde el detalle. El formulario debe permitir introducir o modificar los datos principales definidos en el modelo.
- Validar los campos obligatorios antes de guardar y mostrar mensajes comprensibles cuando falten datos o sean incorrectos. Al cancelar, regresad a la pantalla desde la que se abrió el formulario sin aplicar cambios.
- Pedid confirmación antes de eliminar. Al confirmar, eliminad el elemento y regresad a la lista actualizada; al cancelar, conservadlo.
- Mantened la interfaz construida con Jetpack Compose y adaptada al prototipo del proyecto.
- No es necesario conservar los datos al cerrar la aplicación; Room, Firebase y la API se incorporarán en versiones posteriores.

## Criterios de finalización

- Se puede navegar entre inicio, lista, detalle y formulario de creación o edición.
- Se pueden crear elementos y verlos en la lista; desde el detalle se pueden editar o eliminar.
- Las operaciones de creación, modificación y borrado se reflejan en el repositorio y en las pantallas sin reiniciar la aplicación.
- Los formularios validan la entrada, cancelar una operación no guarda cambios y el borrado requiere confirmación.
- El acceso a los datos está separado de la interfaz mediante un repositorio.
- La aplicación compila y las operaciones se pueden demostrar en un emulador o dispositivo.

## Entrega

Entregad el código actualizado en el repositorio Git del proyecto. Añadid un PDF con capturas de inicio, lista, detalle y formularios, y una descripción de los cambios; adjuntadlo a la actividad correspondiente de Moodle. Incluid también la URL del repositorio y un vídeo breve que muestre el recorrido por las pantallas y las operaciones de crear, consultar, editar y eliminar un elemento. Consultad los requisitos comunes de entrega en el [enunciado general](../enunciado_proyecto.md).