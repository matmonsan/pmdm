---
title: Versión 1.3
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al enunciado del proyecto](../enunciado_proyecto.md)

# **FECHA DE ENTREGA:** 29/11/2026

# Versión 1.3 — Autenticación con Firebase

<span class="etiqueta-investigacion">Investigación</span>

## Objetivo

Incorporar la gestión de cuentas con Firebase Authentication y permitir que el usuario se registre, inicie sesión y recupere el acceso a su cuenta.

## Versión de investigación

Firebase Authentication no se explica en clase. Cada estudiante debe investigar cómo funciona a partir de la documentación oficial y aplicarlo a su proyecto. En clase se resolverán dudas concretas, pero no se proporcionará el código.

Punto de partida:

- Las pantallas siguen el mismo esquema de las unidades anteriores: reciben datos y avisan con eventos. El acceso a Firebase queda dentro de un repositorio.
- La navegación entre las pantallas de acceso y la pantalla principal es libre: podéis hacerla como consideréis (con estado de Compose, como en la Versión 1.2, o con `NavHost`). En la Versión 1.4 será obligatorio que toda la navegación de la aplicación esté hecha con `NavHost`.

Documentación oficial recomendada:

- [Añadir Firebase a un proyecto Android](https://firebase.google.com/docs/android/setup)
- [Primeros pasos con Firebase Authentication en Android](https://firebase.google.com/docs/auth/android/start)
- [Autenticación con correo electrónico y contraseña](https://firebase.google.com/docs/auth/android/password-auth)
- [Gestionar usuarios: envío del correo de recuperación de contraseña](https://firebase.google.com/docs/auth/android/manage-users)

## Enunciado

Integrad Firebase Authentication en la aplicación. Añadid las pantallas necesarias para el registro, el inicio de sesión y la recuperación de contraseña, manteniendo el estilo visual del proyecto. Tras iniciar sesión correctamente, el usuario debe llegar a la pantalla principal existente; si cierra sesión, debe volver al acceso.

## Requisitos

- Configurad Firebase para el proyecto Android siguiendo la documentación oficial.
- Implementad el registro con correo electrónico y contraseña, el inicio de sesión y el envío de recuperación de contraseña.
- Validad los campos y mostrad estados de carga, confirmaciones y errores de autenticación con mensajes claros.
- Evitad que se pueda acceder a la pantalla principal como usuario autenticado si no hay una sesión válida.
- Añadid una acción para cerrar sesión.
- Conservad las pantallas y el CRUD de la versión anterior. En esta entrega no es necesario guardar los elementos en Firebase ni vincularlos a una cuenta: el almacenamiento continúa siendo en memoria.
- No incluyáis claves privadas, contraseñas ni otros secretos en el repositorio.

## Criterios de finalización

- Un usuario puede registrarse y acceder con sus credenciales.
- Se puede solicitar la recuperación de contraseña y se informa del resultado.
- Los errores habituales (por ejemplo, credenciales incorrectas o campos incompletos) se comunican sin bloquear la aplicación.
- El cierre de sesión devuelve al usuario a la pantalla de acceso y la navegación no deja abierta la pantalla protegida.
- El CRUD de la versión anterior sigue funcionando después del acceso.
- El PDF incluye la sección de investigación descrita en el apartado Entrega.

## Entrega

Entregad el código actualizado en el repositorio Git del proyecto. Añadid un PDF con capturas de registro, inicio de sesión y recuperación de contraseña, y adjuntadlo a la actividad correspondiente de Moodle. Incluid también la URL del repositorio y un vídeo breve que demuestre los flujos de acceso y cierre de sesión. Consultad los requisitos comunes de entrega en el [enunciado general](../enunciado_proyecto.md).

Por ser una versión de investigación, el PDF debe incluir además una sección de investigación con:

- Qué es Firebase Authentication y qué parte de la aplicación resuelve.
- Los pasos seguidos para configurar Firebase en el proyecto, con capturas de la consola de Firebase.
- Las funciones de Firebase utilizadas para registrar, iniciar sesión, recuperar la contraseña y cerrar sesión, y en qué archivo del proyecto se llaman.
- Los problemas encontrados y cómo se han resuelto.
- Las fuentes consultadas (documentación, artículos, vídeos), con su enlace.
