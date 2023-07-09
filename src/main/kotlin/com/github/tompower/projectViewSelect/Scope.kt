package com.github.tompower.projectViewSelect

import com.intellij.ide.scopeView.ScopeViewPane
import com.intellij.openapi.actionSystem.AnActionEvent

class Scope : ProjectViewSelect() {
    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let { project ->
            super.selectViewAction(
                viewPane = ScopeViewPane(project),
                event = event
            )
        }
    }
}
