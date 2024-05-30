# annotation-processing-demo

This project contains examples demonstrating how annotation processing can be used to introspect Java files at 
compile time, and how code generation can be a useful tool for reducing boilerplate.

## Examples

- [Annotation Processing](annotation-processing-demo/README.md)
- [Code Generation](code-generation-demo/README.md)

## Build

To build the project locally, use the following Gradle command:

```shell
./gradlew clean build
```

Running this command will also run the tests for the code generated by the code generation example.

## Usage

To use any of the libraries from this project in your own, you must publish them to your local maven repository.
This project uses the `maven-publish` Gradle plugin to do this.

To publish the libraries locally, use the following command:

```bash
./gradlew publishToMavenLocal
```

The published libraries will have the version `0.0.0`.

When adding the libraries as dependencies to your project, ensure that they are included in both the `implementation`
and `annotationProcessor` configurations.

```groovy
dependencies {
    ...
    implementation "org.global.owl.annotation.example:annotation-processing:0.0.0"
    annotationProcessor "org.global.owl.annotation.example:annotation-processing:0.0.0"
    ...
}
```

