package com.thoughtworks.coral.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.thoughtworks.coral.utils.ActionHandler;

public class SwitchGroup extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        ActionHandler handler = new ActionHandler(e);
        handler.switchToNextGroup();
    }
}
