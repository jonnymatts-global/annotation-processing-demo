# Annotation Processing

An example of how an [annotation processor](https://hannesdorfmann.com/annotation-processing/annotationprocessing101/) can be used to introspect annotated classes.

This demo uses the [InspectProcessor](../annotation-processing/src/main/java/org/global/dax/annotation/example/InspectProcessor.java) annotation processor to process the [Demo](src/main/java/org/global/dax/annotation/example/Demo.java) record
annotated with the [@Inspect](../annotation-processing/src/main/java/org/global/dax/annotation/example/Inspect.java) annotation.

The annotation processor will use the [Messager](https://docs.oracle.com/en/java/javase/17/docs/api/java.compiler/javax/annotation/processing/Messager.html) provided to it to print the name of the Demo record
and all the fields it contains, along with their types.

The annotation processor will run at compile time, with the Messager printing the messages as compilation notes.

To compile the code, and run the annotation processor, run the following in your terminal:

```shell
./gradlew annotation-processing-demo:clean annotation-processing-demo:build
```

After the build finishes, you should see the following in the terminal:

```
> Task :annotation-processing-demo:compileJava
Note: Inspecting: org.global.dax.annotation.example.Demo
Note: Demo | i: int
Note: Demo | s: java.lang.String
Note: Demo | l: java.util.List<java.lang.String>
```
