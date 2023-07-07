package com.github.tompower.projectViewActions

import com.intellij.openapi.actionSystem.AnActionEvent


class Scope : ProjectViewAction() {
    override fun actionPerformed(event: AnActionEvent) {
        super.actionPerformed(event)
        super.changeView("Scope")
    }

    override fun update(event: AnActionEvent) {
        super.update(event)
    }
}
