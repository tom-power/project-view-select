package com.github.tompower.projectViewActions

import com.intellij.ide.projectView.impl.ProjectViewPane
import com.intellij.openapi.actionSystem.AnActionEvent


class Project : ProjectViewAction() {
    override fun actionPerformed(event: AnActionEvent) {
        super.actionPerformed(event)
        super.changeView(ProjectViewPane.ID)
    }

    override fun update(event: AnActionEvent) {
        super.update(event)
    }
}
