package com.thoughtworks.coral.groups;

import com.thoughtworks.coral.types.FileType;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface FileGroup {
    Optional<FileType> nextOf(final FileType type, final Predicate<? super FileType> skip);

    Optional<FileType> getDefaultType(List<FileType> excludes);

    List<FileType> getTypes();
}
