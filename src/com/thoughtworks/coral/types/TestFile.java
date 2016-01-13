package com.thoughtworks.coral.types;

import java.util.Arrays;

public class TestFile extends BaseFileType {
    public TestFile() {
        super(Arrays.asList(".test.js", ".test.ts", ".test.es6", ".test.coffee", "_spec.js", "_spec.ts", "_spec.es6",
                "_spec.coffee"));
    }
}
