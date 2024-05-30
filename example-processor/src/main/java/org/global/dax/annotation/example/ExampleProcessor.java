package org.global.dax.annotation.example;

import static javax.lang.model.element.ElementKind.FIELD;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import java.util.List;
import java.util.Set;

public class ExampleProcessor extends AbstractProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {

        for (final var annotation : annotations) {
            final var annotatedTypes = getAnnotatedElements(roundEnv, annotation, TypeElement.class);
            for (final var type : annotatedTypes) {
                printName(type);
                final var fields = getFields(type);
                for (final Element field : fields) {
                    printFieldForType(type, field);
                }
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        return Set.of(Example.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {

        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {

        super.init(processingEnv);
    }

    private static void printName(final TypeElement element) {

        System.out.println("Processing: " + element.getQualifiedName());
    }

    private static void printFieldForType(final TypeElement type, final Element field) {

        final var fieldName = field.getSimpleName();
        final var fieldType = field.asType();
        System.out.println(type.getSimpleName() + " | " + fieldName + ": " + fieldType);
    }

    private static List<? extends Element> getFields(final TypeElement element) {

        return element.getEnclosedElements().stream()
                      .filter(e -> e.getKind().equals(FIELD))
                      .toList();
    }

    private <E extends Element> List<E> getAnnotatedElements(final RoundEnvironment roundEnvironment,
                                                             final TypeElement annotationTypeElement,
                                                             final Class<E> elementType) {

        return roundEnvironment.getElementsAnnotatedWith(annotationTypeElement)
                               .stream()
                               .filter(elementType::isInstance)
                               .map(elementType::cast)
                               .toList();
    }
}
