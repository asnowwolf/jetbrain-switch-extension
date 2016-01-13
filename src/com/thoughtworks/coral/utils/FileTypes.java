package com.thoughtworks.coral.utils;

import com.thoughtworks.coral.types.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FileTypes {
    // html/jade等文件
    public static FileType htmlFile = new HtmlFile();
    // css/scss/less/styl等文件
    public static FileType cssFile = new CssFile();
    // js/ts/coffee/es6等文件
    public static FileType jsFile = new JsFile();
    // test.js/test.ts/test.coffee/test.es6文件
    public static FileType testFile = new TestFile();
    // 文件类型的检测顺序. 注意, 这里的顺序不能乱, 否则可能会把test.js文件误判为js文件
    static List<FileType> fileTypes = Arrays.asList(htmlFile, cssFile, testFile, jsFile);
    static List<FileType> switchTypes = Arrays.asList(htmlFile, jsFile, testFile, cssFile);

    public static Optional<FileType> typeOf(String filePath) {
        return fileTypes.stream().filter(type -> type.matched(filePath)).findFirst();
    }

    public static Optional<FileType> nextTypeOf(String filePath, Predicate<FileType> skipIt) {
        Optional<FileType> type = typeOf(filePath);
        if (type.isPresent()) {
            int index = switchTypes.indexOf(type.get());
            for (int i = 0; i < switchTypes.size(); ++i) {
                if (!skipIt.test(switchTypes.get(i))) {
                    return Optional.of(switchTypes.get((index + 1) % switchTypes.size()));
                }
            }
        }
        return Optional.empty();
    }
}
