---
title: Versión 1.1
description: "<strong>Módulo:</strong> Programación Multimedia y Dispositivos Móviles <br> <strong>Profesor:</strong> Matías Montávez Sánchez"
---
[⌂ Volver al enunciado del proyecto](../enunciado_proyecto.md)

# **FECHA DE ENTREGA:** 29/10/2026

# Versión 1.1 — Interfaz inicial en Jetpack Compose

## Objetivo

Convertir la propuesta de la Versión 1.0 en una pequeña aplicación funcional con varias pantallas construidas en Jetpack Compose. El usuario podrá recorrer una pantalla de inicio, una lista de ejemplo y el detalle de un elemento.

## Enunciado

Implementad el primer recorrido de vuestra aplicación, adaptado a la temática y al prototipo. Debe incluir:

1. **Pantalla de inicio:** presenta el nombre, la identidad visual y una breve introducción a la aplicación, con una acción para abrir el contenido.
2. **Pantalla de lista:** muestra varios elementos de ejemplo relacionados con la temática.
3. **Pantalla de detalle:** al seleccionar un elemento, presenta más información sobre él y una acción para volver a la lista.

Las pantallas deben respetar el prototipo en lo esencial. Podéis ajustar la distribución para que funcione correctamente en un dispositivo móvil, manteniendo la identidad visual y la organización propuestas. En esta versión los datos son de ejemplo y no se pueden crear ni modificar.

## Requisitos

- Construid la interfaz con funciones `@Composable`; no uséis layouts XML.
- Utilizad layouts y modificadores de Compose para organizar y espaciar los componentes.
- Implementad las pantallas de inicio, lista y detalle como partes diferenciadas de la interfaz.
- Permitid pasar de inicio a lista, seleccionar un elemento para abrir su detalle y volver a la lista o al inicio.
- Para cambiar entre estas pantallas, basta con gestionar el estado de la interfaz en Compose; la navegación formal con `NavHost` se incorporará en la Versión 1.4.
- Mostrad los elementos en `LazyColumn` o en otro componente de lista adecuado.
- Presentad cada elemento mediante una tarjeta de Material (`Card`, `ElevatedCard` u `OutlinedCard`) o un componente equivalente.
- Incluid varios elementos de ejemplo con datos coherentes con la temática. La pantalla de detalle debe mostrar información adicional respecto a la tarjeta de la lista.
- No hace falta crear, editar ni eliminar elementos ni persistir información.
- Las pantallas deben seguir siendo legibles en distintos tamaños habituales de móvil.

## Criterios de finalización

- El proyecto compila y se puede ejecutar en un emulador o dispositivo.
- Se puede recorrer el flujo inicio → lista → detalle y regresar sin quedar en una pantalla bloqueada.
- La lista muestra varios elementos propios de la aplicación y cada uno abre su información detallada correspondiente.
- La interfaz usa componentes de Compose, refleja la propuesta visual de la Versión 1.0 y no presenta textos o controles cortados.

## Entrega

Entregad el código actualizado en el repositorio Git del proyecto. Añadid un PDF con capturas de las tres pantallas y una descripción de los cambios, y adjuntadlo a la actividad correspondiente de Moodle. Incluid también la URL del repositorio y un vídeo breve que muestre el recorrido entre inicio, lista y detalle. Consultad los requisitos comunes de entrega en el [enunciado general](../enunciado_proyecto.md).
