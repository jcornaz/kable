# Kable
[![LGPLv3](https://img.shields.io/badge/license-LGPLv3-blue.svg)](https://raw.githubusercontent.com/slimaku/kable/master/LICENSE)
[![JitPack](https://jitpack.io/v/slimaku/kable.svg)](https://jitpack.io/#slimaku/kable)
[![Build Status](https://travis-ci.org/slimaku/kable.svg?branch=master)](https://travis-ci.org/slimaku/kable)
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
### Create tables
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
You need to use a jdk 8 or newer.

You can get the artifacts from [Jitpack](https://jitpack.io/#slimaku/kable) by adding the dependency in your build file.

Here is an example with Gradle :

```gradle
repositories {
    mavenCentral()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.slimaku:kable:v1.1.0'
}
```

You can also [use maven, sbt or leiningen](https://jitpack.io/#slimaku/kable).

## API Reference
[KDoc](https://slimaku.github.io/kable/doc/1.1/kable/kable/index.html)

## Test
Tests are written in [src/test/kable](https://github.com/slimaku/kable/tree/master/src/test/kotlin/kable)

To run them, you only need to clone the project and launch the gradle task `test` :
```bash
git clone git@github.com:slimaku/kable.git
cd kable
./gradlew test
```

## License
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

A copy of the GNU General Public License is in the file "LICENSE" at the root of the project.
If you don't find it, see <http://www.gnu.org/licenses/>.
