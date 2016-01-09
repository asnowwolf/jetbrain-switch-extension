package com.thoughtworks.utils;

import java.util.List;

public abstract class BaseFileType implements FileType {
    public boolean matched(String fileName) {
        return Lookup.findSuffix(fileName, getExtensions()) != -1;
    }
}
