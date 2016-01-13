package com.thoughtworks.coral.groups;

import com.thoughtworks.coral.utils.FileTypes;

import java.util.Arrays;

public class TotallyGroup extends BaseFileGroup {
    public TotallyGroup() {
        super(Arrays.asList(FileTypes.htmlFile, FileTypes.cssFile, FileTypes.jsFile, FileTypes.testFile));
    }
}
