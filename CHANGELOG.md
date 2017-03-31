# Change log
## 1.1-SNAPSHOT [Unreleased]
### Added
* Map-like extension functions for Table (#3)
    * `forEach`
    * `all` / `any` / `none`
    * `count`
    * `maxBy` / `maxWith` / `minBy` / `minWith`
    * filters (`filter`, `filterRows`, `filterColumns`, `filterValues`, etc.)
    * maps (`map`, `mapRows`, `mapColumns`, `mapValues`, etc.)
* `SingletonTable` class to handle more efficiently tables with only one entry
* `EmptyTable` singleton to handle more efficiently empty tables

### Changed
* Now `tableOf()` return an instance of `SingletonTable` or `EmptyTable` if possible

## 1.0.0 (2017-01-14)
### Added
* Interface Table
* `emptyTable()` and `tableOf()` functions to create tables
* `plus`, `minus` and `contains` operators (as extensions functions)