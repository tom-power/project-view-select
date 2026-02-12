package com.github.tompower.projectViewSelect

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.testFramework.TestActionEvent

class ProjectViewSelectActionTest : AbstractProjectWindowTestCase() {

    fun testActions() {
        projectViewSelectProject.let { viewSelect ->
            val action = ActionManager.getInstance()
                .getAction("ProjectViewSelectProject") as ProjectViewSelectAction
            performAction(action)
            with(viewSelect) {
                assertEquals(id, currentProjectViewPane?.id)
                assertEquals(subId, currentProjectViewPane?.subId)
            }
        }
        projectViewSelectScopeAllChangedFiles.let { viewSelect ->
            val action = ActionManager.getInstance()
                .getAction("ProjectViewSelectScopeAllChangedFiles") as SelectScopeAllChangedFiles
            performAction(action)
            with(viewSelect) {
                assertEquals(id, currentProjectViewPane?.id)
                assertEquals(subId, currentProjectViewPane?.subId)
            }
        }
    }

    private fun performAction(action: AnAction) {
        val event = TestActionEvent.createTestEvent { dataId ->
            when (dataId) {
                CommonDataKeys.PROJECT.name -> project
                else -> null
            }
        }
        when (action) {
            is SelectProject -> action.actionPerformed(event)
            is SelectScopeAllChangedFiles -> action.actionPerformed(event)
        }
    }

}