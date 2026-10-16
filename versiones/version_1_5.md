---
title: Versión 1.5
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al enunciado del proyecto](../enunciado_proyecto.md)

# **FECHA DE ENTREGA:** 15/12/2026

# Versión 1.5 — Lista global y datos compartidos

## Objetivo

Añadir una sección de consulta con elementos comunes para todos los usuarios, diferenciándola de la lista de elementos propios que cada usuario puede gestionar.

## Enunciado

Ampliad la navegación de la Versión 1.4 con dos áreas claramente diferenciadas:

1. **Mis elementos:** elementos propios del usuario con las acciones CRUD implementadas en la Versión 1.2.
2. **Elementos comunes:** una lista de consulta con contenido compartido y pertinente a la temática de la aplicación.

Para esta versión, los elementos comunes pueden ser datos de ejemplo incluidos en la aplicación o suministrados por el repositorio local. No se requiere conexión a un servidor ni una API REST.

## Requisitos

- Añadid un destino para la lista de elementos comunes y hacedlo accesible desde la navegación principal.
- Mostrad los elementos comunes en una lista adecuada, con tarjetas y datos legibles y coherentes con la temática.
- Mantened una separación visible entre los elementos comunes y los elementos propios del usuario.
- Permitid consultar los elementos comunes; no deben poder crearse, editarse ni eliminarse desde la aplicación.
- Conservad las acciones CRUD únicamente para los elementos propios y mantened el acceso protegido por autenticación.
- Aseguraos de que la barra inferior, el menú lateral y el botón Atrás funcionan correctamente con los nuevos destinos.

## Criterios de finalización

- El usuario autenticado puede abrir tanto «Mis elementos» como «Elementos comunes».
- Los elementos comunes se muestran correctamente y no ofrecen acciones de edición o borrado.
- El CRUD de los elementos propios, la autenticación y el cierre de sesión continúan funcionando.
- La nueva sección utiliza el modelo visual del proyecto y no requiere una conexión de red.

## Entrega

Entregad el código actualizado en el repositorio Git del proyecto. Añadid un PDF con capturas de ambas listas y una descripción de cómo se distinguen, y adjuntadlo a la actividad correspondiente de Moodle. Incluid también la URL del repositorio y un vídeo breve que muestre la navegación entre ambas listas y las acciones CRUD sobre un elemento propio. Consultad los requisitos comunes de entrega en el [enunciado general](../enunciado_proyecto.md).
