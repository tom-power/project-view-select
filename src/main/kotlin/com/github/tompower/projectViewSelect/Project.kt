package com.github.tompower.projectViewSelect

import com.intellij.ide.projectView.impl.ProjectViewPane
import com.intellij.openapi.actionSystem.AnActionEvent


class Project : ProjectViewSelect() {
    override fun actionPerformed(event: AnActionEvent) {
        super.selectViewAction(
            viewPaneId = ProjectViewPane.ID,
            event = event
        )
    }
}
