# ISTE 422 Group Project

Team nkt's project can be found here: https://github.com/kjf5787/ISTE422GroupProject

## Tests

### Classes

| Classes Tested        | Test Author   |
| --------------------- | ------------- |
| `CreateDDLMySQL`        | Nahom Ashagrea|
| `EdgeConvertCreateDDL`  | Gavin Miyagawa|
| `EdgeField`             | Kayla Fennell |
| `EdgeTable`             | Thea Arias    |

### How to Compile & Run

Tests can be compiled with gradle using the `gradle test` command via shell terminal.
When tests execute, you will see some output on the console, specifically from `logger.info`.
The output should show which tests were run and whether the build was successful or if it failed.
Additionally, you will be able to see the number of tests that succeeded or failed.
A full report of the results can be found here: `build/reports/tests/test/index.html`.

The versions used to test these instructions: 
- OpenJDK version: `18.0.2.1`
- Gradle version: `8.3`
