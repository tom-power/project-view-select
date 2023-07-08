package com.github.tompower.projectViewSelect

import com.intellij.ide.scopeView.ScopeViewPane
import com.intellij.openapi.actionSystem.AnActionEvent

class Scope : ProjectViewSelect() {
    override fun actionPerformed(event: AnActionEvent) {
        super.selectViewAction(
            viewPaneId = ScopeViewPane.ID,
            event = event
        )
    }
}
