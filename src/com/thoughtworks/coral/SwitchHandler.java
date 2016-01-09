package com.thoughtworks.coral;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.thoughtworks.utils.FileType;
import com.thoughtworks.utils.FileTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwitchHandler extends AnAction {
    private Direction direction;

    SwitchHandler(Direction direction) {
        this.direction = direction;
    }

    private final FileTypes fileTypes = new FileTypes();

    @Nullable
    private VirtualFile findNextFile(VirtualFile file) {
        assert file != null;
        String path = file.getCanonicalPath();
        assert path != null;
        FileType type = fileTypes.typeOf(path).get();
        for (int i = 0; i < fileTypes.count(); ++i) {
            if (direction == Direction.UP) {
                type = fileTypes.prevType(type);
            } else {
                type = fileTypes.nextType(type);
            }
            String pathWithoutExtension = getExtension(file);
            VirtualFile nextFile = findFileByType(type, pathWithoutExtension);
            if (nextFile != null) {
                return nextFile;
            }
        }
        return null;
    }

    @NotNull
    private String getExtension(VirtualFile file) {
        assert file != null;
        String path = file.getCanonicalPath();
        assert path != null;
        String extension = fileTypes.extensionOf(path);
        return path.substring(0, path.length() - extension.length());
    }

    @Nullable
    private VirtualFile findFileByType(FileType nextType, String pathWithoutExtension) {
        for (String typeExt : nextType.getExtensions()) {
            String newFilePath = pathWithoutExtension + typeExt;
            VirtualFile newFile = LocalFileSystem.getInstance().findFileByPath(newFilePath);
            if (newFile != null) {
                return newFile;
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        VirtualFile file = e.getData(PlatformDataKeys.VIRTUAL_FILE);
        assert file != null;
        VirtualFile newFile = findNextFile(file);
        // 如果找不到下一个文件, 就什么也不做
        if (newFile == null) {
            return;
        }
        Project project = e.getData(PlatformDataKeys.PROJECT);
        assert project != null;
        new OpenFileDescriptor(project, newFile).navigate(true);
    }
}
