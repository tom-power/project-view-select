package com.github.tompower.projectViewSelect

import com.intellij.ide.scopeView.ScopeViewPane
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.vcs.changes.ChangeListScope

class SelectScopeAllChangedFiles : ProjectViewSelectAction() {
    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let { project ->
            super.projectViewSelect(
                event = event,
                project = project,
                viewPane = ScopeViewPane(project),
                namedScope = ChangeListScope(ChangeListManager.getInstance(project))
            )
        }
    }
}