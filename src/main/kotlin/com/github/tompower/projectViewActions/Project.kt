package com.github.tompower.projectViewActions

import com.intellij.ide.projectView.impl.ProjectViewPane
import com.intellij.openapi.actionSystem.AnActionEvent


class Project : ProjectViewAction() {
    override fun actionPerformed(event: AnActionEvent) {
        super.changeViewAction(ProjectViewPane.ID, event)
    }
}
