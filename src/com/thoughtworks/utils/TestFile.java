package com.thoughtworks.utils;

import java.util.Arrays;
import java.util.List;

public class TestFile extends BaseFileType {
    public List<String> getExtensions() {
        return Arrays.asList(".test.js", ".test.ts", ".test.es6", ".test.coffee", "_spec.js", "_spec.ts", "_spec.es6",
                "_spec.coffee");
    }
}
