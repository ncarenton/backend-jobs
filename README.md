# Planning de Garde level 1 #

## Technical view ##

[![Build Status](https://travis-ci.org/ncarenton/backend-jobs.svg?branch=master)](https://travis-ci.org/ncarenton/backend-jobs)
[![Coverage Status](https://coveralls.io/repos/github/ncarenton/backend-jobs/badge.svg?branch=master)](https://coveralls.io/github/ncarenton/backend-jobs?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/a4c374253bff4bd29ca84c61183097c8)](https://app.codacy.com/app/ncarenton/backend-jobs?utm_source=github.com&utm_medium=referral&utm_content=ncarenton/backend-jobs&utm_campaign=Badge_Grade_Dashboard)

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
./gradlew run --args="-i [input_file] -o [output_file]"
```

#### Using start script ####
```console
unzip build/distributions/planning-de-garde.zip
./planning-de-garde/bin/planning-de-garde -i [input_file] -o [output_file]
```
