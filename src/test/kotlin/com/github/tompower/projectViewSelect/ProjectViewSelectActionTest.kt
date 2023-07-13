package com.github.tompower.projectViewSelect

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.testFramework.MapDataContext
import com.intellij.testFramework.SkipInHeadlessEnvironment

@SkipInHeadlessEnvironment
class ProjectViewSelectActionTest : AbstractProjectWindowTestCase() {
    fun testActions() {
        actionToViewSelect.forEach { (action, viewSelect) ->
            runAction(action, actionFor())
            with(viewSelect) {
                assertEquals(id, currentProjectViewPane?.id)
                assertEquals(subId, currentProjectViewPane?.subId)
            }
        }
    }

    private fun runAction(action: String, event: AnActionEvent) {
        ActionManager.getInstance()
            .getAction(action)
            .actionPerformed(event)
    }

    private fun actionFor(): AnActionEvent {
        val context = MapDataContext()
        context.put(CommonDataKeys.PROJECT, project)
        return AnActionEvent.createFromDataContext("", null, context)
    }

}