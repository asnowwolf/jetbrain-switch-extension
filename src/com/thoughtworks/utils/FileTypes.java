package com.thoughtworks.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileTypes {
    private List<FileType> types = Arrays.asList(new HtmlFile(), new CssFile(), new TestFile(), new JsFile());

    public Optional<FileType> typeOf(String fileName) {
        return types.stream().filter(type -> type.matched(fileName)).findFirst();
    }

    public String extensionOf(String fileName) {
        Optional<FileType> type = typeOf(fileName);
        if (type.isPresent()) {
            for (String extension : type.get().getExtensions()) {
                if (fileName.endsWith(extension)) {
                    return extension;
                }
            }
        }
        return "";
    }

    public int count() {
        return types.size();
    }

    public FileType prevType(FileType type) {
        int index = types.indexOf(type);
        return types.get((index - 1 + types.size()) % types.size());
    }

    public FileType nextType(FileType type) {
        int index = types.indexOf(type);
        return types.get((index + 1) % types.size());
    }
}
