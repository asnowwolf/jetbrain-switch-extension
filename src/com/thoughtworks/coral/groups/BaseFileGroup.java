package com.thoughtworks.coral.groups;

import com.thoughtworks.coral.types.FileType;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class BaseFileGroup implements FileGroup {
    List<FileType> types;

    protected BaseFileGroup(final List<FileType> types) {
        this.types = types;
    }

    public Optional<FileType> nextOf(final FileType type, final Predicate<? super FileType> acceptable) {
        int index = types.indexOf(type);
        // 如果根本没有找到就算了
        if (index == -1) {
            return Optional.empty();
        }
        for (int i = 0; i < types.size(); ++i) {
            index = (index + 1) % types.size();
            FileType nextType = types.get(index);
            // 如果调用者认为有效, 则直接返回它, 否则尝试下一个
            if (acceptable.test(nextType)) {
                return Optional.of(nextType);
            }
        }
        // 没找到就算了
        return Optional.empty();
    }

    public Optional<FileType> getDefaultType(List<FileType> excludes) {
        return types.stream().filter(type -> excludes == null || !excludes.contains(type)).findFirst();
    }

    public List<FileType> getTypes() {
        return types;
    }
}
