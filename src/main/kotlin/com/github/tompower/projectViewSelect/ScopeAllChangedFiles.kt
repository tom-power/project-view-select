package com.github.tompower.projectViewSelect

import com.intellij.ide.scopeView.ScopeViewPane
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.vcs.changes.ChangeListScope

class ScopeAllChangedFiles : ProjectViewSelect() {
    override fun actionPerformed(event: AnActionEvent) {
        event.project?.let { project ->
            super.action(
                event = event,
                viewPane = ScopeViewPane(project),
                namedScope = ChangeListScope(ChangeListManager.getInstance(project))
            )
        }
    }
}
