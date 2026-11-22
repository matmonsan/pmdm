---
title: Versión 1.4
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al enunciado del proyecto](../enunciado_proyecto.md)

# **FECHA DE ENTREGA:** 08/12/2026

# Versión 1.4 — Navegación entre pantallas

## Objetivo

Conectar las pantallas de la aplicación mediante Jetpack Navigation Compose y ofrecer navegación principal con un menú lateral, una barra superior con menú de opciones y una barra inferior.

## Enunciado

Ampliad la aplicación para que el usuario pueda desplazarse de forma clara entre las pantallas ya implementadas. Usad un `NavHost` para definir los destinos y las acciones de navegación. Incorporad un `ModalNavigationDrawer` y una `NavigationBar` para acceder a las áreas principales de la aplicación, y una `TopAppBar` con acciones y un menú de opciones (UD4, apartado 5).

## Requisitos

- Definid destinos para las pantallas de acceso existentes y para las áreas principales disponibles en esta versión.
- Usad `NavHost` y los componentes de Jetpack Navigation Compose para navegar entre pantallas.
- Mostrad un `ModalNavigationDrawer` en el área autenticada, con accesos adecuados a la temática y una opción de cerrar sesión.
- Añadid una `TopAppBar` en el área autenticada que muestre el título de la pantalla actual y un icono (≡) que abra el `ModalNavigationDrawer`. En las pantallas de detalle, el icono será una flecha que vuelva a la pantalla anterior.
- Incluid en la `TopAppBar` al menos una acción directa con `IconButton` en `actions` y un menú de opciones (`DropdownMenu`) que se abra con el icono ⋮ y tenga al menos dos opciones adecuadas a la temática. Cada opción cerrará el menú antes de ejecutar su acción.
- Usad los colores del tema (`MaterialTheme.colorScheme`) en la barra superior, de forma que se adapte al modo oscuro.
- Incorporad una `NavigationBar` para cambiar entre las secciones principales disponibles, como la lista de elementos propios y la pantalla de inicio.
- Evitad que las pantallas de autenticación aparezcan como destinos de la navegación principal una vez iniciada la sesión.
- Tras cerrar sesión, volved a la pantalla de acceso y evitad regresar a una pantalla autenticada con el botón Atrás.
- Conservad los flujos de autenticación y las funciones existentes del CRUD.

## Criterios de finalización

- Se puede navegar entre las secciones principales desde el menú lateral o la barra inferior.
- La barra superior muestra el título de cada pantalla, abre el menú lateral o vuelve atrás según corresponda, y sus acciones y opciones del menú ⋮ funcionan.
- Las acciones del sistema para volver atrás conducen a una pantalla coherente y no permiten saltarse el acceso.
- La sesión, el CRUD y las pantallas anteriores siguen funcionando.
- La navegación se adapta a la temática y al prototipo del proyecto.

## Entrega

Entregad el código actualizado en el repositorio Git del proyecto. Añadid un PDF con capturas de las pantallas y los componentes de navegación (incluido el menú de opciones de la barra superior abierto), y adjuntadlo a la actividad correspondiente de Moodle. Incluid también la URL del repositorio y un vídeo breve que recorra las pantallas e incluya el cierre de sesión. Consultad los requisitos comunes de entrega en el [enunciado general](../enunciado_proyecto.md).