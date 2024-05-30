package org.global.dax.annotation.codegen;

import java.util.List;

@Builder
public record Demo(Integer i, String s, List<String> l) {}
