package com.thoughtworks.coral.groups;

import com.thoughtworks.coral.utils.FileTypes;

import java.util.Arrays;

public class LogicGroup extends BaseFileGroup {
    public LogicGroup() {
        super(Arrays.asList(FileTypes.jsFile, FileTypes.testFile));
    }
}
