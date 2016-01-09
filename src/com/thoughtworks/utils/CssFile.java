package com.thoughtworks.utils;

import java.util.Arrays;
import java.util.List;

public class CssFile extends BaseFileType {
    public List<String> getExtensions() {
        return Arrays.asList(".css", ".scss", ".styl");
    }
}
