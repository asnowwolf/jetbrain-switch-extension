package com.thoughtworks.coral.types;

import java.util.Arrays;

public class HtmlFile extends BaseFileType {
    public HtmlFile() {
        super(Arrays.asList(".html", ".htm", ".jade"));
    }
}
