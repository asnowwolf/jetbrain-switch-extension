package com.thoughtworks.coral.groups;

import com.thoughtworks.coral.utils.FileTypes;

import java.util.Arrays;

public class ViewGroup extends BaseFileGroup {
    public ViewGroup() {
        super(Arrays.asList(FileTypes.htmlFile, FileTypes.cssFile));
    }
}
