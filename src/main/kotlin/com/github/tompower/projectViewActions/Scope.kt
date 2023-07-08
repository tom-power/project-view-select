package com.github.tompower.projectViewActions

import com.intellij.ide.scopeView.ScopeViewPane
import com.intellij.openapi.actionSystem.AnActionEvent

class Scope : ProjectViewAction() {
    override fun actionPerformed(event: AnActionEvent) {
        super.changeViewAction(ScopeViewPane.ID, event)
    }
}
