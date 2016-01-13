package com.thoughtworks.coral.types;

import java.util.List;

public interface FileType {
    List<String> getExtensions();

    boolean matched(final String filePath);
}
