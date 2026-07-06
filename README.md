# Evaluación de estructuras de datos: Set y Map

Explicación técnica de la solución implementada en `PersonaController`.

---

## Método A – `filtrarYOrdenar`

### Qué implementación de Set se utilizó

Se utilizó un **`TreeSet<Persona>`** con un `Comparator` personalizado definido mediante una expresión lambda, en lugar de un `HashSet` o `LinkedHashSet`.

### Por qué se eligió esa implementación

El enunciado exige que el resultado quede **ordenado** según reglas específicas (edad descendente y, en caso de empate, nombre ascendente) y que, al mismo tiempo, se garantice **unicidad lógica** basada en nombre y edad. Ninguna de estas dos condiciones puede resolverse con:

- `HashSet`: no mantiene ningún orden y basa la unicidad en `equals()`/`hashCode()`, los cuales no pueden modificarse porque el modelo `Persona` no debe alterarse.
- `LinkedHashSet`: solo conserva el orden de inserción, no permite ordenar por criterios personalizados ni admite un `Comparator`.

`TreeSet` es la única estructura estándar que permite **ordenar automáticamente** los elementos según un `Comparator` propio, sin necesidad de tocar la clase `Persona`.

### Cómo se garantiza la unicidad de los datos

En un `TreeSet`, dos elementos se consideran duplicados cuando el `Comparator` retorna `0` al compararlos. El comparador implementado evalúa primero la edad y luego el nombre:

```java
int compE = Integer.compare(c2.getEdad(), c1.getEdad());
if (compE != 0) {
    return compE;
}
return c1.getNombre().compareToIgnoreCase(c2.getNombre());
```

Si dos personas tienen la **misma edad** y el **mismo nombre** (ignorando mayúsculas mediante `compareToIgnoreCase`), ambas comparaciones retornan `0`, por lo que el `TreeSet` las trata como iguales y descarta la segunda inserción automáticamente. Esto coincide exactamente con el criterio de duplicado pedido en el enunciado (mismo nombre case-insensitive + misma edad).

### Cómo se conserva o define el orden de los resultados

El mismo `Comparator` que resuelve la unicidad define el orden final, ya que en este método ambos criterios (orden y duplicado) coinciden:

- **Edad descendente**: se invierten los operandos en `Integer.compare(c2.getEdad(), c1.getEdad())` en lugar de `c1` primero, logrando que las edades mayores aparezcan antes.
- **Nombre ascendente, ignorando mayúsculas**: se usa como criterio de desempate cuando dos personas comparten la misma edad, mediante `compareToIgnoreCase`.

### Cómo funciona la lógica aplicada

1. Se filtra la lista original con un `for`, agregando al `TreeSet` únicamente las personas cuya edad sea mayor o igual al `edadUmbral` recibido como parámetro.
2. Cada inserción pasa por el `Comparator`, que decide su posición en el orden y, de paso, descarta automáticamente cualquier duplicado según las reglas explicadas arriba.
3. El resultado final es un conjunto ordenado y sin duplicados lógicos, listo para ser recorrido.

---

## Método B – `agruparPorRangoEdad`

### Qué implementación de Map se utilizó

Se utilizó un **`TreeMap<String, List<String>>`**.

### Por qué se eligió esa implementación

El enunciado exige que el mapa quede **ordenado alfabéticamente por clave** ("ADULTO", "JOVEN", "MAYOR"). Un `HashMap` no garantiza ningún orden de iteración, y un `LinkedHashMap` solo respetaría el orden de inserción de las claves, no un orden alfabético. `TreeMap` ordena automáticamente sus claves según su orden natural (alfabético para `String`), cumpliendo el requisito sin necesidad de ordenar manualmente después.

### Cómo se garantiza la unicidad de los datos

La unicidad se controla en dos niveles:

**Nivel 1 – Claves del mapa:** por definición, un `Map` no permite claves repetidas, así que "JOVEN", "ADULTO" y "MAYOR" existen una sola vez cada una de forma natural.

**Nivel 2 – Nombres dentro de cada grupo:** antes de agregar un nombre a la lista de un rango, se recorre la lista ya existente de ese grupo comparando cada nombre guardado contra el nuevo mediante `equalsIgnoreCase`:

```java
boolean yaExiste = false;
for (String n : nombresDelGrupo) {
    if (n.equalsIgnoreCase(primerNombre)) {
        yaExiste = true;
        break;
    }
}
if (!yaExiste) {
    nombresDelGrupo.add(primerNombre);
}
```

Si ya existe un nombre igual (ignorando mayúsculas) dentro de ese rango específico, no se vuelve a agregar. Esto permite que un mismo primer nombre pueda repetirse en rangos distintos (por ejemplo, "Juan" en JOVEN y "Juan" en ADULTO), ya que la unicidad se evalúa **por grupo**, no de forma global.

### Cómo se conserva o define el orden de los resultados

- **Orden de las claves:** lo resuelve automáticamente `TreeMap`, sin código adicional.
- **Orden de los nombres dentro de cada lista:** se respeta el **orden de aparición en la lista original**, ya que se recorre `personas` con un único `for` de principio a fin, y cada nombre se agrega a su grupo en el momento en que se procesa, sin reordenar después.

### Cómo funciona la lógica aplicada

1. Se recorre la lista de personas una sola vez.
2. Por cada persona, se calcula su rango etario ("JOVEN", "ADULTO" o "MAYOR") según su edad, usando una cadena de `if / else if / else` que cubre todos los casos posibles.
3. Se extrae el primer nombre de la persona con `nombre.split(" ")[0]`.
4. Se verifica si ese primer nombre ya existe dentro de la lista correspondiente a ese rango (case-insensitive).
5. Si no existe, se agrega al final de la lista de ese grupo, conservando el orden de aparición original.
6. Al finalizar el recorrido, se retorna el `Map` completo, ya ordenado por clave gracias a `TreeMap`.
