package com.thoughtworks.coral.groups;

import com.thoughtworks.coral.utils.FileTypes;

import java.util.Arrays;

public class StyleGroup extends BaseFileGroup {
    public StyleGroup() {
        super(Arrays.asList(FileTypes.htmlFile, FileTypes.cssFile));
    }
}
