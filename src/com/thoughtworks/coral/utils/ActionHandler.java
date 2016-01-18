package com.thoughtworks.coral.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileEditor.impl.EditorHistoryManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.thoughtworks.coral.groups.FileGroup;
import com.thoughtworks.coral.types.FileType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ActionHandler {
    AnActionEvent event;

    public ActionHandler(AnActionEvent event) {
        this.event = event;
    }

    public Project getProject() {
        return event.getData(PlatformDataKeys.PROJECT);
    }

    public Optional<VirtualFile> findFileByPath(String path) {
        return Optional.ofNullable(LocalFileSystem.getInstance().findFileByPath(path));
    }

    List<VirtualFile> getRecentFiles() {
        VirtualFile[] files = EditorHistoryManager.getInstance(getProject()).getFiles();
        // TODO: 改用算法实现逆序
        List<VirtualFile> result = new ArrayList<>();
        for (int i = files.length - 1; i >= 0; --i) {
            VirtualFile file = files[i];
            // 跳过自己不认识的文件
            if (FileTypes.typeOf(file.getCanonicalPath()).isPresent()) {
                result.add(files[i]);
            }
        }
        return result;
    }

    Optional<VirtualFile> getCurrentFile() {
        try {
            return Optional.ofNullable(getRecentFiles().get(0));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    Optional<FileType> getCurrentType() {
        if (!getCurrentFile().isPresent()) {
            return Optional.empty();
        }
        return FileTypes.typeOf(getCurrentFile().get().getCanonicalPath());
    }

    Optional<VirtualFile> getLatestFile() {
        try {
            return Optional.ofNullable(getRecentFiles().get(1));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    Optional<FileType> getLatestType() {
        if (!getLatestFile().isPresent()) {
            return Optional.empty();
        }
        return FileTypes.typeOf(getLatestFile().get().getCanonicalPath());
    }

    String getBasePath(String path) {
        return path.replaceAll("(.test)?\\.\\w+$", "");
    }

    void openFile(Optional<VirtualFile> file) {
        new OpenFileDescriptor(getProject(), file.get()).navigate(true);
    }

    void switchToNextFileInGroup(Optional<FileGroup> group, Optional<FileGroup> prevGroup) {
        // 当前必须至少打开了一个文件
        assert getCurrentFile().isPresent();
        // 找出历史中第一个和当前文件属于同一个组的, 切换过去, 如果没指定则默认为html组
        FileType type = getCurrentType().orElse(FileTypes.htmlFile);
        FileGroup currentGroup = group.orElse(FileGroups.contentGroup);

        String path = getCurrentFile().get().getCanonicalPath();
        String basePath = getBasePath(path);
        Optional<FileType> nextType = currentGroup.nextOf(type, acceptable(type, basePath));

        // 把上一个组中的文件排除, 只找下一个组中特有的文件
        List<FileType> excludes = null;
        if (prevGroup.isPresent()) {
            excludes = prevGroup.get().getTypes();
        }
        FileType defaultType = currentGroup.getDefaultType(excludes).get();
        List<String> extensions = nextType.orElse(defaultType).getExtensions();
        Optional<Optional<VirtualFile>> nextFile = extensions.stream()
                .map(extension -> findFileByPath(basePath + extension))
                .filter(Optional::isPresent)
                .findFirst();
        if (nextFile.isPresent()) {
            openFile(nextFile.get());
        }
    }

    @NotNull
    Predicate<FileType> acceptable(FileType latestType, String basePath) {
        // 如果这个类型不是刚才编辑的类型, 而且这个文件存在, 则接受
        return type -> type != latestType &&
                type.getExtensions().stream().anyMatch(extension -> findFileByPath(basePath + extension).isPresent());
    }

    public void switchToNextGroup() {
        Optional<FileGroup> group = FileGroups.groupOf(getCurrentType(), getLatestType());
        Optional<FileGroup> nextGroup = FileGroups.nextGroupOf(getCurrentType(), getLatestType());
        switchToNextFileInGroup(nextGroup, group);
    }

    public void switchToNextFile() {
        Optional<FileGroup> group = FileGroups.groupOf(getCurrentType(), getLatestType());
        switchToNextFileInGroup(group, Optional.empty());
    }
}
