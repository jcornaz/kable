# Kable
[![LGPLv3](https://img.shields.io/badge/license-LGPLv3-blue.svg)](https://raw.githubusercontent.com/slimaku/kable/master/LICENSE)
[![JitPack](https://jitpack.io/v/slimaku/kable.svg)](https://jitpack.io/#slimaku/kable)
[![Build Status](https://travis-ci.org/slimaku/kable.svg?branch=master)](https://travis-ci.org/slimaku/kable)
[![Code coverage](https://codecov.io/gh/slimaku/kable/branch/master/graph/badge.svg)](https://codecov.io/gh/slimaku/kable)
[![Code quality](https://codebeat.co/badges/5c6f587d-8348-42c0-9bb0-7067e548841b)](https://codebeat.co/projects/github-com-slimaku-kable)
[![Issues](https://img.shields.io/github/issues/slimaku/kable.svg)](https://github.com/slimaku/kable/issues)
[![Pull requests](https://img.shields.io/github/issues-pr/slimaku/kable.svg)](https://github.com/slimaku/kable/pulls)

Bi dimensional maps for kotlin (and java)

## Synopsis
The goal is to provide a "table" data structure. A table is almost like a map in java. But with a table, values does not have a "key". Instead they have a "row" and a "column".

It's exactly the same concept as [guava](https://github.com/google/guava) [table](https://github.com/google/guava/wiki/NewCollectionTypesExplained#table). But :

* You don't have to include the whole guava library
* Tables provided by this project are immutable and even don't provide mutable method (guava immutable tables throw an exception when a mutable method is called)
* This project is written in kotlin and provide useful functions, extension functions and operators that let you write nicer code

## Code Examples
```kotlin
// Create an empty table
val empty = emptyTable<Char, Int, String>()

// Create a table for a given set of entries
val table = tableOf(
    entry('A', 1, "hello"),
    entry('A', 2, "world")
)
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
```

### Iterate
```kotlin
for ((row, column, value) in table)
    println("($row, $column) = $value")
```

## Add Kable to your project
You need to use a jdk 6 or newer.

You can get the artifacts from [Jitpack](https://jitpack.io/#slimaku/kable). You only need to add the dependency in your build file.

Here is an example with Gradle :

```gradle
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.slimaku:kable:master-SNAPSHOT'
}
```

You can also [use maven, sbt or leiningen](https://jitpack.io/#slimaku/kable).
