# Annotation Processing

An example of how an [annotation processor](https://hannesdorfmann.com/annotation-processing/annotationprocessing101/) and code generation can be used in combination to eliminate the need
for writing boilerplate code.

This demo uses the [BuilderProcessor](../annotation-processing/src/main/java/org/global/dax/annotation/example/ExampleProcessor.java) annotation processor to generate a builder class for the [Demo](src/main/java/org/global/dax/annotation/example/Demo.java) record
annotated with the [@Builder](../annotation-processing/src/main/java/org/global/dax/annotation/example/Example.java) annotation.

To generate Java source code, the demo uses the [JavaPoet](https://github.com/square/javapoet) library. The library
provides the ability to easily create Java source files, integrating simply with the annotation processor via the
[Filer](https://docs.oracle.com/en/java/javase/17/docs/api/java.compiler/javax/annotation/processing/Filer.html).

To simplify the annotation processing code, the BuilderProcessor uses the [TypeProcessor](https://github.com/global-dax-outdoor/annotation-processing/blob/main/src/main/java/org/global/dax/annotation/TypeProcessor.java)
abstract class from the DAX Outdoor [annotation-processing](https://github.com/global-dax-outdoor/annotation-processing)
library.

The annotation processor will run at compile time. To compile the code, and run the annotation processor, 
run the following in your terminal:

```shell
./gradlew code-generation-demo:clean code-generation-demo:build
```

After the build finishes, you should see the DemoBuilder class in the
`code-generation-demo/build/generated/sources/annotationProcessor` directory. If you are using Intellij, run a project
rebuild (`Build->Rebuild Project`).

To see how the DemoBuilder works, see [DemoBuilderTest](src/test/java/org/global/dax/annotation/codegen/DemoBuilderTest.java).
