---
title: Versión 1.1
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al enunciado del proyecto](../enunciado_proyecto.md)

# **FECHA DE ENTREGA:** 29/10/2026

# Versión 1.1 — Interfaz inicial en Jetpack Compose

## Objetivo

Convertir la propuesta de la Versión 1.0 en una primera experiencia funcional con Jetpack Compose: una pantalla con una lista de elementos temporales de ejemplo y una pantalla para consultar individualmente cada elemento.

## Enunciado

Implementad, adaptadas a la temática y al prototipo de vuestra aplicación, estas dos pantallas:

1. **Pantalla de lista:** presenta el nombre de la aplicación y varios elementos temporales relacionados con su temática.
2. **Pantalla de detalle:** al seleccionar un elemento de la lista, muestra individualmente su información, incluyendo más datos que los visibles en la lista, y ofrece una acción para volver.

Las pantallas deben respetar el prototipo en lo esencial. Podéis ajustar la distribución para que funcione correctamente en un dispositivo móvil, manteniendo la identidad visual y la organización propuestas. En esta versión los datos son temporales: no hace falta crear, modificar, eliminar ni persistir elementos. Para pasar de la lista al detalle basta con gestionar el estado de la interfaz en Compose; no se pide todavía la estructura de navegación formal de la Versión 1.4.

## Requisitos

- Construid la interfaz con funciones `@Composable`; no uséis layouts XML.
- Utilizad layouts y modificadores de Compose para organizar y espaciar los componentes.
- Implementad con funciones `@Composable` una pantalla de lista y otra de detalle.
- Mostrad los elementos en `LazyColumn` o en otro componente de lista adecuado.
- Presentad cada elemento mediante una tarjeta de Material (`Card`, `ElevatedCard` u `OutlinedCard`) o un componente equivalente, y permitid seleccionarlo para abrir su detalle.
- En el detalle mostrad información adicional del elemento seleccionado e incluid una acción para volver a la lista.
- Incluid varios elementos temporales con datos coherentes con la temática.
- No hace falta implementar `NavHost`, menús ni navegación formal entre las áreas de la aplicación; estos se abordarán en la Versión 1.4.
- Las pantallas deben seguir siendo legibles en distintos tamaños habituales de móvil.

## Criterios de finalización

- El proyecto compila y se puede ejecutar en un emulador o dispositivo.
- La pantalla de lista muestra varios elementos de ejemplo propios de la aplicación y cada uno abre su detalle correspondiente.
- Desde el detalle se puede volver a la lista.
- La interfaz usa componentes de Compose, refleja la propuesta visual de la Versión 1.0 y no presenta textos o controles cortados.

## Entrega

Entregad el código actualizado en el repositorio Git del proyecto. Añadid un PDF con capturas de la lista y del detalle, y una descripción de los cambios; adjuntadlo a la actividad correspondiente de Moodle. Incluid también la URL del repositorio y un vídeo breve que muestre cómo se abre el detalle de un elemento y se vuelve a la lista. Consultad los requisitos comunes de entrega en el [enunciado general](../enunciado_proyecto.md).
