package com.github.tompower.projectViewSelect

import com.intellij.ide.projectView.impl.ProjectViewPane
import com.intellij.openapi.actionSystem.AnActionEvent

class Project : ProjectViewSelect() {
    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let { project ->
            super.action(
                event = event,
                viewPane = ProjectViewPane(project),
                namedScope = null
            )
        }
    }
}
