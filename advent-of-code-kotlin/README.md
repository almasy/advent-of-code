# Advent of Code in Kotlin

As mentioned in the [top-level README](../README.md), the AoC daily
puzzles serve as an excuse to develop a "pico-framework" for 
the "Unified AoC solution management". the [2024]() is the very first
AoC year I've approached with this goal in mind.

The "main star" of this project - the custom AoC "pico-framework" (still
searching for some nice name) should provide following features:

- [ ] Comprehensive documentation, so that other developers can use all
  the new & shiny features
- [x] Common API for daily puzzle solutions
- [x] Unified way of running individual day solutions
- [x] A way of running all available (i.e. implemented) solutions for 
the given AoC year
- [ ] Ability to add new day puzzle solution template to the project
- [ ] Ability to add new AoC year module / template to the project
- [ ] Ability to automatically fetch puzzle input data from AoC web
- [ ] Maybe some benchmarking and reporting...?

This "bigger goal" is also the reason, only first few solutions out of the 
total 25 daily puzzles the AoC 2024 offers are available. Though hopefully,
this will improve with time and the complete set will be published here 
before the AoC 2025 starts 🤣.

This project is written in plain [Kotlin](https://kotlinlang.org/).
To that end, it isn't a stand-alone application and, depending
on your computer's software equipment, it may require several steps, before
it can be run.

Moreover, it only has a text-based user interface, which means it runs
in a terminal emulator / command prompt.

Consider yourself warned 😀

## Table of Contents

- [Quick Start](#quick-start)
- [System Requirements](#system-requirements)
- [Installation](#installation)
- [Running](#running)
- [Configuration](#configuration)
- [Solution Overview](#solution-overview)

## Quick Start

- Make sure a [JDK 21](https://openjdk.org/projects/jdk/21/) is installed
  on your computer
- Clone or download [advent-of-code](https://github.com/almasy/advent-of-code)
 project repository from the GitHub
- Open terminal/command line and change active/working directory to `advent-of-code-kotlin`
  inside of the project root
- Type `./gradlew aoc-2024:runAll`
- Wait until everything's downloaded, setup and compiled
- Enjoy the show... (not that it's too much to look at)

## System Requirements

Apart from the [JDK 21](https://openjdk.org/projects/jdk/21/), there's nothing
unusual needed to build and run this project. Just for the sake of completeness,
here's a full list of requirements
- Operating system
    - Windows 10 or 11
    - Any recent Linux distribution which supports [JDK 21](https://openjdk.org/projects/jdk/21/)
    - Any recent macOS
- Working [JDK 21](https://openjdk.org/projects/jdk/21/) installation
- At least ?? GB of free disk space
- [Git](https://git-scm.com/) (optional)

Amount of RAM or a CPU type shouldn't impose any particular constraints
on the ability to run any of the AoC day solutions.

## Installation

### JDK 21

This project's been developed and tested using
[Eclipse Temurin JDK](https://adoptium.net/temurin/releases/?version=21),
as the target / runtime platform, but any proper JDK 21 (or newer) 
implementation should work just fine.

JDK installation and setup is too broad of a topic to be covered by this
document. If you're not familiar with the process, you may want to consider
using one of the tools, which make the installation and management
(slightly more) user-friendly. Some of the most popular tools are
- [Scoop](https://scoop.sh/) - package manager for Windows
- [SDKMAN!](https://sdkman.io/) - SDK manager for Unix systems
- [Homebrew](https://brew.sh/) - package manager for macOS

### Kotlin 2.1.x

The aforementioned JDK 21 serves as a target platform, however all code
has been written in Kotlin. Thanks to the fact the project is built via
Gradle, no explicit "Kotlin setup & installation" steps are necessary.

### Detailed Instructions

All following steps should be performed from a terminal emulator running
a shell (powershell, bash, zsh, etc.) or a command prompt (cmd.exe)

> [!NOTE]
>
> Windows command prompt users should replace `./gradlew` with
> `gradlew.bat` (or just `gradlew`) in all relevant steps mentioned bellow.

- Make sure JDK 21 is available
```shell
java -version
```
- Checkout the [advent-of-code](https://github.com/almasy/advent-of-code) project from GitHub into a location
  of your choice.
```shell
git clone ...
```
- If you don't have git installed, you can download
  a [zip archive](https://github.com/almasy/advent-of-code/archive/refs/heads/main.zip) 
with the most recent project version and extract it to a location 
of your choice.
- Change the working directory to `advent-of-code-kotlin` inside of the
  checked-out / unzipped GitHub project root.
- Run following command to compile the project
```shell
./gradlew build
```
- If the command completes without errors, the installation
  was successful.

## Running

After a successful installation, type in the following command to see,
which AoC days are available / ready to be run
```shell
./gradlew tasks --group aoc-2024
```

Each available / implemented day will have its own task.
You can run any task of the ***aoc-2024*** group by simply typing
`./gradlew <taskName>`. For example, to run `day03`, type

```shell
./gradlew :aoc-2024:day03
```

The `runAll` is a special task, which will run all
implemented days (of the given AoC year) in a sequence.

Each day can be started in one of the 3 modes. See
the [Running Modes](#running-modes)
chapter to find out, how to switch between them.

## Configuration

The AoC-Kotlin "pico-framework" provides following configuration mechanisms:

- Environment variables (starting with `AOC_` prefix)
- JVM system properties (could be passed as command-line parameters)
- Project properties (for AoC 2024, these are kept in the
  [aoc-2024/src/main/resources/config.properties](./aoc-2024/src/main/resources/config.properties))

The value of each particular configuration item is determined by checking 
the possible sources for the first match in the following order

1. System properties
2. Environment variables
3. Project properties
4. Built-in fall-back values

### Puzzle Input Data

The project is configured to use bundled puzzle inputs taken from
the official description of each particular day. The "full" puzzle
inputs will never be part of the public repository, as this is against
the [rules of the Advent of Code](https://adventofcode.com/2024/about).

However, anyone can obtain their own set of "full" puzzle inputs by
[logging-in](https://adventofcode.com/2024/auth/login).

Puzzle input for each day **must** be saved into a text file with
a name conforming to pattern `day<XY>.txt`, where the `<XY>` stands
for the puzzle day number (padded with 0, if necessary).

All puzzle input files must reside in the same directory. The name
of the directory is specified by property `puzzle.input.root`
(or its equivalent system variable `AOC_PUZZLE_INPUT_ROOT`).

#### Example
Let's assume all puzzle inputs are located in `C:\Users\AoC\2024\data`.
In order to pass this location to the program via command line, type
in following

```shell
./gradlew :aoc-2024:runAll -P"puzzle.input.root=C:\AoC\2024\data"
```

The same can be achieved by setting environment variable
- Windows command prompt
  `set AOC_PUZZLE_INPUT_ROOT=C:\Users\AoC\2024\data`
- Windows Powershell
  `$env:AOC_PUZZLE_INPUT_ROOT="C:\Users\AoC\2024\data"`
- Bash / ZSH
  `export AOC_PUZZLE_INPUT_ROOT="/home/AoC/2024/data"`

### Running Modes

Each day can be run in 3 different modes

- `DEFAULT` will just perform the calculation and show the results
  of both part 1 and part 2 of the given day.
- `MEASURED` extends the DEFAULT mode functionality by measuring
  and printing the duration of puzzle input loading and part 1 and
  part 2 solutions.
- `BENCHMARK` will run a micro-benchmark of puzzle input loading
  and part 1 and part 2 solutions.

The choice of a run-mode can be controlled via property `puzzle.run.mode`
or environment variable `AOC_PUZZLE_RUN_MODE`.

> [!NOTE]
>
> Values other than `DEFAULT`, `MEASURED` or `BENCHMARK`
> are ignored (`DEFAULT` is used as a fall-back).

### Example

To run a benchmark of day07 type

```shell
./gradlew :aoc-2024:day07 -P"puzzle.run.mode=BENCHMARK"
```

### Benchmark Options

The number of iterations each day puzzle solution is put through when
running a benchmark can be modified via property `benchmark.iterations`
or environment variable `AOC_BENCHMARK_ITERATIONS`. It can be set to any
integer value from `2` upwards to `1 000 000` (one million).

### Example
```shell
./gradlew :aoc-2024:day02 -P"puzzle.run.mode=BENCHMARK" -P"benchmark.iterations=5000"
```

Considering some daily puzzles could take over 1 second to execute,
one has to be careful with the iterations limit (1 000 000 seconds =
11 days, 13 hours and 46 minutes). However, other puzzle days can
complete under 1 millisecond (1 000 000 milliseconds = 16 minutes,
40 seconds). To address this disparity, the AoC project offers
another benchmark related setting - a property `benchmark.limit.minutes`
(or an environment variable `AOC_BENCHMARK_LIMIT_MINUTES`).
This one allows to limit the total runtime of a benchmark to given
amount of minutes.

Accepted value range for the `benchmark.limit.minutes` is from `1` to `480`
(i.e. 8 hours).

### Example
```shell
./gradlew :aoc-2024:day06 -P"puzzle.run.mode=BENCHMARK" -P"benchmark.limit.minutes=1" -P"benchmark.iterations=5000"
```

## Solution Overview

T.B.D.

### Project Structure

T.B.D.

### Gradle Plugin

T.B.D.
