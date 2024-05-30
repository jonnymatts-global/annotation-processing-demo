package org.global.dax.annotation.example;

import static javax.lang.model.element.ElementKind.FIELD;
import static javax.tools.Diagnostic.Kind.NOTE;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import java.util.List;
import java.util.Set;

/**
 * An example annotation processor for classes annotated with {@link Inspect}.
 */
public class InspectProcessor extends AbstractProcessor {

    /**
     * Print the class name and all the fields found within the class.
     *
     * @param annotations the annotation interfaces requested to be processed
     * @param roundEnv    environment for information about the current and prior round
     * @return false - other annotation processors can process the annotations
     */
    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        for (final var annotation : annotations) {
            final var annotatedTypes = getAnnotatedTypes(roundEnv, annotation);
            for (final var type : annotatedTypes) {
                printName(type);
                final var fields = getFields(type);
                for (final Element field : fields) {
                    printFieldForType(type, field);
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        return Set.of(Inspect.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {

        return SourceVersion.latestSupported();
    }

    private void printName(final TypeElement element) {

        processingEnv.getMessager().printMessage(NOTE, "Inspecting: " + element.getQualifiedName());
    }

    private void printFieldForType(final TypeElement type, final Element field) {

        final var fieldName = field.getSimpleName();
        final var fieldType = field.asType();
        final var fieldMessage = type.getSimpleName() + " | " + fieldName + ": " + fieldType;

        processingEnv.getMessager().printMessage(NOTE, fieldMessage);
    }

    private List<? extends Element> getFields(final TypeElement element) {

        return element.getEnclosedElements().stream()
                      .filter(e -> e.getKind().equals(FIELD))
                      .toList();
    }

    private List<TypeElement> getAnnotatedTypes(final RoundEnvironment roundEnvironment,
                                                final TypeElement annotation) {

        return roundEnvironment.getElementsAnnotatedWith(annotation)
                               .stream()
                               .filter(TypeElement.class::isInstance)
                               .map(TypeElement.class::cast)
                               .toList();
    }
}
