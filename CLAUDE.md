# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**water-tool** is a Java 8 utility library (published to Maven Central) providing a collection of common tool classes for everyday Java development. The package base is `io.github.loulangogogo.water`.

## Build & Test Commands

```bash
# Compile
mvn compile

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=ClassName

# Run a single test method
mvn test -Dtest=ClassName#methodName

# Package (includes javadoc jar)
mvn package

# Skip GPG signing during local builds
mvn package -Dgpg.skip=true

# Publish to Maven Central
mvn deploy
```

## Code Architecture

The library is organized into packages by domain, each containing static utility classes:

| Package | Purpose |
|---------|---------|
| `tool` | Core utilities: `StrTool` (string ops), `ObjectTool` (null checks), `AssertTool`, `ReflectTool`, `IdTool`, `CharsetTool`, `TreeTool` |
| `bean` | Bean manipulation: `BeanTool` (copy/map/convert), `BeanPropertiesTool`, `JsonBeanTool`, `SerializeTool` |
| `json` | JSON handling via Jackson: `JsonTool` (serialize/parse), `JsonMap`, `JSON` |
| `crypto` | Cryptography: `AESTool`, `RSATool`, `MD5Tool`, `SHA2Tool`, `SHA3Tool`, `Base64Tool` |
| `collection` | Collection/array helpers: `CollectionTool`, `CollTool`, `ArrayTool` |
| `date` | Date/time utilities (wrapping both `java.util.Date` and `java.time` APIs) |
| `io` | File/IO operations: `FileTool`, `IoTool`, `ZipTool`, `ResourceTool` |
| `math` | Math utilities: `BigDecimalTool`, `MathTool`, `WeightRandom` |
| `thread` | Thread pool management: `ThreadPoolTool`, `ExecutorBuilder`, `NamedThreadFactory` |
| `stream` | Stream collector helpers: `CollectorTool` |
| `map` | Map utilities: `MapTool` |
| `enums` | Shared enums: `CharsetEnum`, `MaskingDataTypeEnum` |
| `exception` | Custom runtime exceptions (one per domain): `JsonException`, `EncryptException`, etc. |
| `interfaces` | Common interfaces: `Builder<T>`, `TreeNode<T>` |

## Key Design Patterns

- **Static utility classes**: All tool classes use static methods — no instantiation. Classes are not final and have no public constructors (implicit).
- **Delegation to Apache Commons**: Many methods delegate to `commons-lang3.StringUtils` rather than re-implementing (e.g., `StrTool.isBlank` → `StringUtils.isBlank`).
- **Jackson-based JSON**: `JsonTool` extends `JsonToolObjectMapper` which holds a shared `ObjectMapper`. Custom type handling via `JavaType`, `CollectionType`, `MapType`.
- **Custom exceptions per domain**: Each package has its own runtime exception (e.g., `JsonException`, `DecryptException`, `CopyPropertieException`) — tool methods catch checked exceptions and rethrow as these unchecked ones.
- **Builder pattern for thread pools**: `ExecutorBuilder` implements `Builder<ExecutorService>` for fluent thread pool creation.

## Dependencies

Key deps: `commons-lang3`, `commons-io`, `commons-codec`, `jackson-databind`, `jackson-datatype-jsr310`, `tika-core`, `slf4j-api`, `junit` (test).

## Version Management

Use `mvn versions` commands (documented in pom.xml) to change versions:
```bash
mvn versions:set -DnewVersion=1.2.1
mvn versions:revert  # undo
```
