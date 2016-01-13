package com.thoughtworks.coral.types;

import java.util.Arrays;

public class JsFile extends BaseFileType {
    public JsFile() {
        super(Arrays.asList(".js", ".ts", ".es6", ".coffee"));
    }
}
