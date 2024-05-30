package org.global.dax.annotation.example;

import java.util.List;

import org.global.dax.annotation.codegen.Builder;

@Builder
public record Demo(Integer i, String s, List<String> l) {}
