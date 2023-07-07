package com.github.tompower.pva

import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.DumbAware


class ChangedFiles() : AnAction(), DumbAware {
    override fun actionPerformed(event: AnActionEvent) {
        val am = ActionManager.getInstance()
        am.getAction("ActivateProjectToolWindow").actionPerformed(
                AnActionEvent(
                        /* inputEvent = */ null,
                        /* dataContext = */ DataManager.getInstance().dataContext,
                        /* place = */ ActionPlaces.UNKNOWN,
                        /* presentation = */ Presentation(),
                        /* actionManager = */ ActionManager.getInstance(), /* modifiers = */ 0))


    }

    override fun update(event: AnActionEvent) {
        super.update(event)
    }
}
