# Kable
[![LGPLv3](https://img.shields.io/badge/license-LGPL--3.0-blue.svg)](LICENSE)
[![Status](https://img.shields.io/badge/status-discontinued-lightgray.svg)](https://gist.githubusercontent.com/jcornaz/46736c3d1f21b4c929bd97549b7406b2/raw/ProjectStatusFlow)
[![JitPack](https://jitpack.io/v/jcornaz/kable.svg)](https://jitpack.io/#jcornaz/kable)
[![Build Status](https://travis-ci.org/jcornaz/kable.svg?branch=master)](https://travis-ci.org/jcornaz/kable)

Bi dimensional maps for kotlin

## Status
This project is discontinued. It is no longer maintained and no support will be provided.

The reason, is that it easy to deal with table data structure with plain kotlin, without the need of a third party library. Eventually one could simply create type aliases and extensions function for `Map` and `MutableMap`.

Example:
```kotlin
typealias Table<R, C, V> = Map<R, Map<C, V>>
typealias MutableTable<R, C, V> = MutableMap<R, MutableMap<C, V>>

operator fun <R, C, V> Table<in R, in C, V>.get(row: R, column: C): V? =
    get(row)?.get(column)

operator fun <R, C, V> MutableTable<in R, in C, in V>.set(row: R, column: C, value: V) =
    getOrPut(row) { mutableMapOf() }.put(column, value)

fun <R, C, V> Table<in R, C, V>.getRow(row: R): Map<C, V> =
    get(row).orEmpty()

fun <R, C, V> Table<R, in C, V>.getColumn(column: C): Map<R, V> {
  val result = mutableMapOf<R, V>()

  forEach { (rowKey, row) ->
    row[column]?.let { result[rowKey] = it }
  }

  return result
}
```

And even this code doesn't really belongs to a third party library, because in practice, the type aliases and the extension functions might have business oriented naming, making the code even clearer when used.

## Synopsis
The goal is to provide a "table" data structure. A table is almost like a map in java where each entry would have two keys the "row" and the "column".

It's exactly the same concept as [guava](https://github.com/google/guava) [table](https://github.com/google/guava/wiki/NewCollectionTypesExplained#table). But :

* You don't have to include the whole guava library
* This project provide a distinct separation between immutable and mutable tables in the same way as kotlin provide mutable and immutable collections
* This project is written in kotlin and provide useful functions, extension functions and operators that let you write nicer code

## Code Examples
### Create tables
```kotlin
// Create an empty table
val empty = emptyTable<Char, Int, String>()

// Create a table for a given set of entries
val table = tableOf(
    entry('A', 1, "hello"),
    entry('A', 2, "world")
)

// Create a table for given row list
val tableByRow = tableOf(
    columns('A', 'B', 'C'),
    row(1, "a1", "b1", "c1"),
    row(2, "a2", "b2", "c2"),
    row(3, "a3", "b3", "c3")
)

// Use a mutable table
val mutableTable = mutableTableOf<Char, Int, String>()
table['A', 1] = "this is a1"
table['B', 5] = "this is b5"
```

### Get content
```kotlin
val value = table['A', 1]
val row = table.getRow('A')
val column = table.getColumn(1)
```

### Add / Remove entries
```kotlin
// Add an entry
val newTable1 = table + entry('B', 1, "test")

// Remove an entry
val newTable2 = table - ('A' to 1)

// Remove a whole row
val newTable3 = table.minusRow('A')

// Remove a whole column
val newTable4 = table.minusColumn('A')

// if the table is mutable
mutableTable['A', 1] = "test"
mutableTable.remove('A', 1)
mutableTable.removeColumn(2)
mutableTable.clear()
```

### Iterate
```kotlin
for ((row, column, value) in table)
    println("($row, $column) = $value")
```

### Use map-like extensions functions
```kotlin
table.filterColumns { it.rem(2) == 0 }.forEach { (row, column, value) -> println("($row, $column) = $value") }
```
See all the available functions [here](https://jcornaz.github.io/kable/doc/2.0/kable/com.github.jcornaz.kable.util/) 

## Add Kable to your project
You need to use a jdk 8 or newer.

You can get the artifacts from [Jitpack](https://jitpack.io/#jcornaz/kable) by adding the dependency in your build file.

Here is an example with Gradle :

```gradle
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.jcornaz:kable:v2.0.0'
}
```

You can also [use maven, sbt or leiningen](https://jitpack.io/#jcornaz/kable/v2.0.0).

## API Reference
[KDoc](https://jcornaz.github.io/kable/doc/2.0/kable/)

## Test
Tests are written in [src/test](src/test/kotlin/com/github/jcornaz/kable)

You can run them from the root directory with : `./gradlew test`
