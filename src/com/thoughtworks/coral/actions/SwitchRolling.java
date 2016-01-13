package com.thoughtworks.coral.actions;

import com.intellij.openapi.actionSystem.*;
import com.thoughtworks.coral.utils.ActionHandler;

public class SwitchRolling extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        new ActionHandler(e).switchToNextFile();
    }
}
