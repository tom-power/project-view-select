package com.github.tompower.pva

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware


class ChangedFiles : AnAction(), DumbAware {
    override fun actionPerformed(event: AnActionEvent) {
        val am = ActionManager.getInstance()
        am.getAction("ActivateProjectToolWindow").actionPerformed(event)
    }

    override fun update(event: AnActionEvent) {
        super.update(event)
    }
}
