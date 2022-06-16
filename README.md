# Days Since Last Accident Bot

<a href="https://www.codacy.com/gh/michaelilyin/days-since-last-accident-bot/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=michaelilyin/days-since-last-accident-bot&amp;utm_campaign=Badge_Grade"><img src="https://app.codacy.com/project/badge/Grade/9317760d14be43e6a0b12d205b715d53"/></a>
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)

[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/9317760d14be43e6a0b12d205b715d53)](https://www.codacy.com/gh/michaelilyin/days-since-last-accident-bot/dashboard?utm_source=github.com&utm_medium=referral&utm_content=michaelilyin/days-since-last-accident-bot&utm_campaign=Badge_Coverage)
[![codecov](https://codecov.io/gh/michaelilyin/days-since-last-accident-bot/branch/master/graph/badge.svg?token=158vAvL71x)](https://codecov.io/gh/michaelilyin/days-since-last-accident-bot)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=coverage)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)

[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=bugs)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=michaelilyin_days-since-last-accident-bot&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=michaelilyin_days-since-last-accident-bot)


This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/days-since-last-accident-bot-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- YAML Configuration ([guide](https://quarkus.io/guides/config#yaml)): Use YAML to configure your Quarkus application
- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin
- WebSockets ([guide](https://quarkus.io/guides/websockets)): WebSocket communication channel support

## Provided Code

### YAML Config

Configure your application with YAML

[Related guide section...](https://quarkus.io/guides/config-reference#configuration-examples)

The Quarkus application configuration is located in `src/main/resources/application.yml`.

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

### WebSockets

WebSocket communication channel starter code

[Related guide section...](https://quarkus.io/guides/websockets)
