package com.thoughtworks.utils;

import java.util.Arrays;
import java.util.List;

public class HtmlFile extends BaseFileType {
    public List<String> getExtensions() {
        return Arrays.asList(".html", ".htm", ".jade");
    }
}
