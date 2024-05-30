package org.global.dax.annotation.example;

import java.util.List;

@Inspect
public record Demo(Integer i, String s, List<String> l) {}
