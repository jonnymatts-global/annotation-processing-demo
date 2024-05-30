package org.global.dax.annotation.codegen;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import java.util.List;
import java.util.stream.Collectors;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.global.dax.annotation.utils.ElementUtils;

public class BuilderTypeSpecGenerator {

    private final ElementUtils elementUtils;

    public BuilderTypeSpecGenerator(final ElementUtils elementUtils) {

        this.elementUtils = elementUtils;
    }

    public TypeSpec generateForElement(final TypeElement typeElement,
                                       final ClassName typeName,
                                       final ClassName builderTypeName) {

        final var typeSpecBuilder = TypeSpec.classBuilder(builderTypeName)
                                            .addModifiers(PUBLIC, FINAL);

        final var fields = getFields(typeElement);
        for (final var field : fields) {
            typeSpecBuilder.addField(createFieldSpec(field));
            typeSpecBuilder.addMethod(createBuilderMethod(builderTypeName, field));
        }
        typeSpecBuilder.addMethod(createBuildMethod(typeName, typeElement));

        return typeSpecBuilder.build();
    }

    private List<Field> getFields(final TypeElement typeElement) {

        return elementUtils.getFields(typeElement)
                           .stream()
                           .map(e -> new Field(e.getSimpleName().toString(), elementUtils.getType(e)))
                           .toList();
    }

    private FieldSpec createFieldSpec(final Field field) {

        return FieldSpec.builder(field.typeName(), field.name())
                        .addModifiers(PRIVATE)
                        .build();
    }

    private MethodSpec createBuilderMethod(final TypeName builderTypeName, final Field field) {

        return MethodSpec.methodBuilder(field.name())
                         .addModifiers(PUBLIC)
                         .returns(builderTypeName)
                         .addParameter(ParameterSpec.builder(field.typeName(),
                                                             field.name())
                                                    .addModifiers(FINAL)
                                                    .build())
                         .addCode(CodeBlock.builder()
                                           .addStatement("this.$L = $L", field.name(), field.name())
                                           .addStatement("return this")
                                           .build())
                         .build();
    }

    private MethodSpec createBuildMethod(final ClassName typeName,
                                         final TypeElement typeElement) {

        final var constructor = elementUtils.getConstructors(typeElement).get(0);
        final var constructorParameters = constructor.getParameters()
                                                     .stream()
                                                     .map(VariableElement::getSimpleName)
                                                     .collect(Collectors.joining(", "));
        return MethodSpec.methodBuilder("build")
                         .addModifiers(PUBLIC)
                         .returns(typeName)
                         .addCode(CodeBlock.builder()
                                           .addStatement("return new $T($L)", typeName, constructorParameters)
                                           .build())
                         .build();
    }

    private record Field(String name, TypeName typeName) {}
}
