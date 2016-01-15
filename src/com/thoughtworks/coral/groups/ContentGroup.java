package com.thoughtworks.coral.groups;

import com.thoughtworks.coral.utils.FileTypes;

import java.util.Arrays;

public class ContentGroup extends BaseFileGroup {
    public ContentGroup() {
        super(Arrays.asList(FileTypes.htmlFile, FileTypes.jsFile));
    }
}
