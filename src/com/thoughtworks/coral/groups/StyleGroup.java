package com.thoughtworks.coral.groups;

import com.thoughtworks.coral.utils.FileTypes;

import java.util.Arrays;

public class StyleGroup extends BaseFileGroup {
    public StyleGroup() {
        // 注意! 这里比较特殊, css必须在html前面, 否则会导致从Logic组切换过来时切换到html文件, 从而无法切换出css
        super(Arrays.asList(FileTypes.cssFile, FileTypes.htmlFile));
    }
}
