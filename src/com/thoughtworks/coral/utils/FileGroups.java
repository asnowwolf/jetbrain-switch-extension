package com.thoughtworks.coral.utils;

import com.thoughtworks.coral.groups.ContentGroup;
import com.thoughtworks.coral.groups.FileGroup;
import com.thoughtworks.coral.groups.LogicGroup;
import com.thoughtworks.coral.groups.StyleGroup;
import com.thoughtworks.coral.types.FileType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileGroups {
    // 逻辑组: 用来在js和test.js之间切换
    public static FileGroup logicGroup = new LogicGroup();
    // 视图组: 用来在js和html之间切换
    public static FileGroup contentGroup = new ContentGroup();
    // 样式组: 用来在html和css之间切换
    public static FileGroup styleGroup = new StyleGroup();
    // 组的切换顺序
    public static List<FileGroup> groups = Arrays.asList(contentGroup, logicGroup, styleGroup);

    // 根据currentType和latestType判断现在所处的组
    public static Optional<FileGroup> groupOf(Optional<FileType> currentType, Optional<FileType> latestType) {
        // 如果完全没指定, 则返回默认的内容组
        if (!currentType.isPresent()) {
            return Optional.empty();
        }

        // 如果只指定了一个, 则直接返回它所属的第一个组
        if (!latestType.isPresent()) {
            return groups.stream()
                    .filter(group -> group.getTypes().contains(currentType.get()))
                    .findFirst();
        }

        Optional<FileGroup> commonGroup = groups.stream()
                .filter(group -> group.getTypes().contains(currentType.get()))
                .filter(group -> group.getTypes().contains(latestType.get()))
                .findFirst();
        // 如果两个文件有交集, 则直接返回它
        if (commonGroup.isPresent()) {
            return commonGroup;
        } else {
            // 如果两个文件没有交集, 则返回当前类型所属的第一个组
            return groups.stream()
                    .filter(group -> group.getTypes().contains(currentType.get()))
                    .findFirst();
        }
    }

    static Optional<FileGroup> nextGroup(Optional<FileGroup> group) {
        if (!group.isPresent()) {
            return Optional.empty();
        }
        int index = groups.indexOf(group.get());
        return Optional.ofNullable(groups.get((index + 1) % groups.size()));
    }

    public static Optional<FileGroup> nextGroupOf(Optional<FileType> currentType, Optional<FileType> latestType) {
        return nextGroup(groupOf(currentType, latestType));
    }
}
