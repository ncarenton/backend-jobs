# Planning de Garde level 1 to 4 #

## Technical view ##

[![Build Status](https://travis-ci.com/ncarenton/planning-de-garde.svg?branch=master)](https://travis-ci.com/ncarenton/planning-de-garde)
[![Coverage Status](https://coveralls.io/repos/github/ncarenton/planning-de-garde/badge.svg?branch=master)](https://coveralls.io/github/ncarenton/planning-de-garde?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d4c6be86243248c284e6327692f677a0)](https://app.codacy.com/app/ncarenton/planning-de-garde?utm_source=github.com&utm_medium=referral&utm_content=ncarenton/planning-de-garde&utm_campaign=Badge_Grade_Dashboard)

### Required configuration ###

-   JDK 1.11

### Technologies ###

-   Programming: [Java 11](https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html)
-   Tests: [JUnit](http://junit.org/), [AssertJ](http://joel-costigliola.github.io/assertj/index.html)
-   Code generation: [Lombok](https://projectlombok.org)

## Execution ##

### Compilation ###
```console
./gradlew clean build
```

### Running ###

#### Using gradle ####
```console
./gradlew run --args=" -l [level] -i [input_file] -o [output_file]"
```

#### Using start script ####
```console
unzip build/distributions/planning-de-garde.zip
./planning-de-garde/bin/planning-de-garde -l [level] -i [input_file] -o [output_file]
```
