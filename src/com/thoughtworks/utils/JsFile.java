package com.thoughtworks.utils;

import java.util.Arrays;
import java.util.List;

public class JsFile extends BaseFileType {
    public List<String> getExtensions() {
        return Arrays.asList(".js", ".ts", ".es6", ".coffee");
    }
}
