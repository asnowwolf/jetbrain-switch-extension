package com.thoughtworks.coral.types;

import com.thoughtworks.coral.utils.Lookup;

import java.util.List;

public abstract class BaseFileType implements FileType {
    List<String> extensions;

    protected BaseFileType(final List<String> extensions) {
        this.extensions = extensions;
    }

    public List<String> getExtensions() {
        return extensions;
    }

    public boolean matched(final String fileName) {
        return Lookup.findSuffix(fileName, getExtensions()) != -1;
    }
}
