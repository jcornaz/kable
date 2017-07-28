# Change log
## 1.2-SNAPSHOT (unreleased)
### Added
* Get operator for rows : `table[row]`. It also makes possible to retrieve a value with `table[row][col]` which is equivalent to `table[row, col]`.
* Mutable tables

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
