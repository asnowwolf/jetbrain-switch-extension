package com.thoughtworks.coral.types;

import java.util.Arrays;

public class CssFile extends BaseFileType {
    public CssFile() {
        super(Arrays.asList(".css", ".scss", ".less", ".styl"));
    }
}
