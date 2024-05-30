package org.global.dax.annotation.codegen;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import java.util.Set;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import org.global.dax.annotation.TypeProcessor;

/**
 * Annotation processor that creates Builder classes for records annotated with {@link Builder}.
 */
public class BuilderProcessor extends TypeProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        return Set.of(Builder.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {

        return SourceVersion.latestSupported();
    }

    @Override
    protected boolean allowSubsequentProcessing() {

        return true;
    }

    /**
     * Generates and writes Builder Java files for provided records {@link TypeElement}s.
     *
     * @param typeElement {@link TypeElement} annotated with {@link Builder}
     */
    @Override
    protected void processAnnotatedType(final TypeElement typeElement) {

        final var typePackage = elementUtils.getPackage(typeElement);
        final var typeName = ClassName.get(typePackage, typeElement.getSimpleName().toString());
        final var builderTypeName = ClassName.get(typePackage, typeName.simpleName() + "Builder");

        processorUtils.log("Creating builder for " + typeName.canonicalName() + " - " + builderTypeName.canonicalName());

        final var builderTypeSpecGenerator = new BuilderTypeSpecGenerator(elementUtils);
        final var typeSpec = builderTypeSpecGenerator.generateForElement(typeElement, typeName, builderTypeName);

        processorUtils.writeToFiler(JavaFile.builder(typePackage, typeSpec).build());
    }
}
