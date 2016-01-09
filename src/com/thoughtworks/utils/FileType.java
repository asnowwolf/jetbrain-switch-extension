package com.thoughtworks.utils;

import java.util.List;

public interface FileType {
    List<String> getExtensions();

    boolean matched(String path);
}
