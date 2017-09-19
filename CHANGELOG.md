# Change log
## 2.0-SNAPSHOT (unreleased)
### Add
* Get operator for rows : `table[row]`. It also makes possible to retrieve a value with `table[row][col]` which is equivalent to `table[row, col]`.
* Mutable tables (#21)
* Table creation by row (#22)

### Improve
* The extensions function to create tables are now on `Iterable` instead of `Collection` (#24)
* Many operations get improved performances with the usage of sequences and iterable

### Breaking changes
* Remove `tableOf(Collection)`. Use `Iterable.toTable()` instead
* Packages renamed 
    * Root api : `com.github.jcornaz.kable` (previously of `kable`)
    * Implementations class : `com.github.jcornaz.kable.impl` (previously of `kable`)
    * Utilities functions : `com.github.jcornaz.kable.util` (previously of `kable`)

## 1.1.0 (2017-03-31)
### Added
* Map-like extension functions for Table (#3)
    * `forEach`
    * `all` / `any` / `none`
    * `count`
    * `maxBy` / `maxWith` / `minBy` / `minWith`
    * filters (`filter`, `filterRows`, `filterColumns`, `filterValues`, etc.)
    * maps (`map`, `flatMap`, `mapRows`, `mapColumns`, `mapValues`, etc.)
* `SingletonTable` class to handle more efficiently tables with only one entry
* `EmptyTable` singleton to handle more efficiently empty tables
* `toTable()` extension function on `Collection<Table.Entry<*, *, *>>` and `Map<Pair<*, *>, *>`
* Ability to create tables from collections with `Collection.groupTableBy` and `Collection.associateTableBy`

### Changed
* Now `tableOf()` returns an instance of `SingletonTable` or `EmptyTable` if possible
* Now `Table.toString()` returns more comprehensive strings

## 1.0.0 (2017-01-14)
### Added
* Interface Table
* `emptyTable()` and `tableOf()` functions to create tables
* `plus`, `minus` and `contains` operators (as extensions functions)
