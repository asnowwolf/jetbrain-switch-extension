package com.thoughtworks.coral.utils;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.fileEditor.impl.EditorHistoryManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
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

    public VirtualFile findFileByPath(String path) {
        return LocalFileSystem.getInstance().findFileByPath(path);
    }

    List<VirtualFile> getRecentFiles() {
        VirtualFile[] files = EditorHistoryManager.getInstance(getProject()).getFiles();
        // TODO: 改用算法实现逆序
        List<VirtualFile> result = new ArrayList<>();
        for (int i = files.length - 1; i >= 0; --i) {
            VirtualFile file = files[i];
            if (FileTypes.typeOf(file.getCanonicalPath()).isPresent()) {
                result.add(files[i]);
            }
        }
        return result;
    }

    Optional<VirtualFile> getCurrentFile() {
        try {
            return Optional.of(getRecentFiles().get(0));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    Optional<VirtualFile> getLatestFile() {
        try {
            return Optional.of(getRecentFiles().get(1));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    String getBasePath(String path) {
        return path.replaceAll("(.test)?.\\w+$", "");
    }

    void openFile(VirtualFile file) {
        new OpenFileDescriptor(getProject(), file).navigate(true);
    }

    public void switchToRecentFile() {
        Optional<VirtualFile> currentFile = getCurrentFile();
        assert currentFile.isPresent();

        String path = currentFile.get().getCanonicalPath();
        assert path != null;

        Optional<FileType> type = FileTypes.typeOf(path);

        if (!type.isPresent()) {
            throw new UnsupportedFileType();
        }

        String basePath = getBasePath(path);
        Optional<VirtualFile> recentFile = getRecentFiles().stream()
                .skip(1)
                .filter(file -> getBasePath(file.getCanonicalPath()).compareToIgnoreCase(basePath) == 0)
                .findFirst();
        // 如果找到了最近打开过的同名文件, 则打开它, 否则打开下一个文件
        if (recentFile.isPresent()) {
            openFile(recentFile.get());
        } else {
            switchToNextFile();
        }
    }

    public void switchToNextFile() {
        Optional<VirtualFile> currentFile = getCurrentFile();
        assert currentFile.isPresent();

        String path = currentFile.get().getCanonicalPath();
        assert path != null;

        Optional<FileType> type = FileTypes.typeOf(path);

        if (!type.isPresent()) {
            throw new UnsupportedFileType();
        }

        Optional<VirtualFile> latestFile = getLatestFile();
        assert latestFile.isPresent();
        String latestPath = latestFile.get().getCanonicalPath();
        assert latestPath != null;

        Optional<FileType> latestType = FileTypes.typeOf(latestPath);

        String basePath = getBasePath(path);
        Optional<FileType> nextType = FileTypes.nextTypeOf(path, skipIt(latestType, basePath));

        if (nextType.isPresent()) {
            Optional<VirtualFile> nextFile = nextType.get().getExtensions().stream()
                    .map(extension -> findFileByPath(basePath + extension))
                    .filter(file -> file != null)
                    .findFirst();
            if (nextFile.isPresent()) {
                openFile(nextFile.get());
            }
        }

    }

    @NotNull
    private Predicate<FileType> skipIt(Optional<FileType> latestType, String basePath) {
        // 无论是这个扩展名不存在, 或者这个类型就是刚才编辑的类型, 都需要跳过
        return aType -> (latestType.isPresent() && aType == latestType.get()) ||
                aType.getExtensions().stream().noneMatch(extension -> findFileByPath(basePath + extension) != null);
    }
}
