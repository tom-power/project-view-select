package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.action.ProjectViewSelectAction
import com.github.tompower.projectViewSelect.action.SelectScopeAllChangedFiles
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.testFramework.MapDataContext
import com.intellij.testFramework.SkipInHeadlessEnvironment

@SkipInHeadlessEnvironment
class ProjectViewSelectActionTest : AbstractProjectWindowTestCase() {
    fun testActions() {
        projectViewSelectProject.let { viewSelect ->
            (ActionManager.getInstance()
                .getAction("ProjectViewSelectProject") as ProjectViewSelectAction)
                .actionPerformed(actionFor())
            with(viewSelect) {
                assertEquals(id, currentProjectViewPane?.id)
                assertEquals(subId, currentProjectViewPane?.subId)
            }
        }
        projectViewSelectScopeAllChangedFiles.let { viewSelect ->
            (ActionManager.getInstance()
                .getAction("ProjectViewSelectScopeAllChangedFiles") as SelectScopeAllChangedFiles)
                .actionPerformed(actionFor())
            with(viewSelect) {
                assertEquals(id, currentProjectViewPane?.id)
                assertEquals(subId, currentProjectViewPane?.subId)
            }
        }
    }

    private fun actionFor(): AnActionEvent {
        val context = MapDataContext()
        context.put(CommonDataKeys.PROJECT, project)
        return AnActionEvent.createFromDataContext("", null, context)
    }

}