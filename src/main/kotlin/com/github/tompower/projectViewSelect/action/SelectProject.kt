package com.github.tompower.projectViewSelect.action

import com.intellij.ide.projectView.impl.ProjectViewPane
import com.intellij.openapi.actionSystem.AnActionEvent

class SelectProject : ProjectViewSelectAction() {
    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let { project ->
            super.projectViewSelect(
                event = event,
                project = project,
                viewPane = ProjectViewPane(project),
                namedScope = null
            )
        }
    }
}