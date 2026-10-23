---
title: Versión 1.1
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al enunciado del proyecto](../enunciado_proyecto.md)

# **FECHA DE ENTREGA:** 29/10/2026

# Versión 1.1 — Interfaz inicial en Jetpack Compose

## Objetivo

Convertir la propuesta de la Versión 1.0 en la primera pantalla funcional de la aplicación, construida en Jetpack Compose, y mostrar en ella una lista con elementos temporales de ejemplo. En esta versión no es necesario implementar varias pantallas ni conectarlas.

## Enunciado

Implementad una primera pantalla de vuestra aplicación, adaptada a la temática y al prototipo. Debe incluir:

1. **Identidad de la aplicación:** presenta su nombre y, si encaja con el prototipo, una breve introducción.
2. **Lista de ejemplo:** muestra varios elementos temporales relacionados con la temática de la aplicación.

La pantalla debe respetar el prototipo en lo esencial. Podéis ajustar la distribución para que funcione correctamente en un dispositivo móvil, manteniendo la identidad visual y la organización propuestas. En esta versión los datos son temporales: no hace falta crear, modificar, eliminar ni persistir elementos. Aunque el prototipo incluya varias pantallas, no es necesario implementarlas ni conectarlas todavía.

## Requisitos

- Construid la interfaz con funciones `@Composable`; no uséis layouts XML.
- Utilizad layouts y modificadores de Compose para organizar y espaciar los componentes.
- Implementad una única primera pantalla funcional con Compose.
- Mostrad los elementos en `LazyColumn` o en otro componente de lista adecuado.
- Presentad cada elemento mediante una tarjeta de Material (`Card`, `ElevatedCard` u `OutlinedCard`) o un componente equivalente.
- Incluid varios elementos temporales con datos coherentes con la temática.
- No hace falta implementar navegación entre pantallas en esta versión. La navegación formal de la aplicación se incorporará en la Versión 1.4.
- La pantalla debe seguir siendo legible en distintos tamaños habituales de móvil.

## Criterios de finalización

- El proyecto compila y se puede ejecutar en un emulador o dispositivo.
- La primera pantalla muestra una lista con varios elementos de ejemplo propios de la aplicación.
- La interfaz usa componentes de Compose, refleja la propuesta visual de la Versión 1.0 y no presenta textos o controles cortados.

## Entrega

Entregad el código actualizado en el repositorio Git del proyecto. Añadid un PDF con una captura de la primera pantalla y una descripción de los cambios, y adjuntadlo a la actividad correspondiente de Moodle. Incluid también la URL del repositorio y, si se solicita un vídeo, mostrad la pantalla y su lista de ejemplo. Consultad los requisitos comunes de entrega en el [enunciado general](../enunciado_proyecto.md).
