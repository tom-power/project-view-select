package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.View
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.testFramework.TestActionEvent

class ProjectViewSelectActionTest : AbstractProjectViewSelectTestCase() {
    fun `test project view action`() {
        Given {
            projectWindowIsInactive()
        }

        When {
            performAction(selectProjectViewAction)
        }

        Then {
            projectWindowIsActive()
            currentViewIs(projectView)
        }
    }

    fun `test all changed files view action`() {
        Given {
            projectWindowIsInactive()
        }

        When {
            performAction(selectScopeAllChangedFilesViewAction)
        }

        Then {
            projectWindowIsActive()
            currentViewIs(allChangedFilesView)
        }
    }

    fun `test all changed files view action from project view`() {
        Given {
            projectWindowIsInactive()
            performAction(selectProjectViewAction)
        }

        When {
            performAction(selectScopeAllChangedFilesViewAction)
        }

        Then {
            projectWindowIsActive()
            currentViewIs(allChangedFilesView)
        }
    }

    fun `test deactivate project window when project view selected and project view select action`() {
        Given {
            projectWindowIsInactive()
            performAction(selectProjectViewAction)
        }

        Then {
            projectWindowIsActive()
            currentViewIs(projectView)
        }

        When {
            performAction(selectProjectViewAction)
        }

        Then {
            projectWindowIsInactive()
        }
    }

    fun `test deactivate project window when all changed files view selected and all changed files select action`() {
        Given {
            projectWindowIsInactive()
            performAction(selectScopeAllChangedFilesViewAction)
        }

        Then {
            projectWindowIsActive()
            currentViewIs(allChangedFilesView)
        }

        When {
            performAction(selectScopeAllChangedFilesViewAction)
        }

        Then {
            projectWindowIsInactive()
        }
    }

    private fun currentViewIs(view: View) {
        assertEquals(
            view,
            currentProjectViewPane?.run { View(id, subId) }
        )
    }

    private val selectProjectViewAction: ProjectViewSelectAction
        get() = ActionManager.getInstance().getAction("ProjectViewSelectProject") as ProjectViewSelectAction

    private val selectScopeAllChangedFilesViewAction: SelectScopeAllChangedFiles
        get() = ActionManager.getInstance()
            .getAction("ProjectViewSelectScopeAllChangedFiles") as SelectScopeAllChangedFiles

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