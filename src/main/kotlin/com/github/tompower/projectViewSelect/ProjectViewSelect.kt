package com.github.tompower.projectViewSelect

import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.projectView.impl.AbstractProjectViewPane
import com.intellij.ide.projectView.impl.ProjectViewImpl
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowId
import com.intellij.openapi.wm.ToolWindowManager

abstract class ProjectViewSelect : AnAction(), DumbAware {
    protected fun selectViewAction(viewPane: AbstractProjectViewPane, event: AnActionEvent) {
        event.project?.let { project ->
            SelectViewAction(project, event).invoke(viewPane)
        }
    }
}

class SelectViewAction(
    private val project: Project,
    private val event: AnActionEvent,
) : (AbstractProjectViewPane) -> Unit {
    private val projectView: ProjectView get() = ProjectView.getInstance(project) as ProjectViewImpl
    private val windowManager: ToolWindowManager get() = ToolWindowManager.getInstance(project)

    override fun invoke(viewPane: AbstractProjectViewPane) {
        if(shouldActivateProjectWindow(viewPane)) {
            activateProjectWindow()
        }
        changeView(viewPane)
    }

    private fun shouldActivateProjectWindow(viewPane: AbstractProjectViewPane): Boolean {
        fun ProjectView.isCurrentViewPane(): Boolean = currentProjectViewPane.id == viewPane.id
        fun isProjectWindowActive(): Boolean = windowManager.activeToolWindowId == ToolWindowId.PROJECT_VIEW

        return !isProjectWindowActive() || projectView.isCurrentViewPane()
    }

    private fun activateProjectWindow() {
        ActionManager.getInstance()
            .getAction("ActivateProjectToolWindow")
            .actionPerformed(event)
    }

    private fun changeView(viewPane: AbstractProjectViewPane) {
        projectView.changeView(viewPane.id)
    }
}
