package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.ViewSelect
import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.projectView.impl.AbstractProjectViewPane
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindowId
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.psi.search.scope.packageSet.NamedScope

abstract class ProjectViewSelectAction : AnAction(), DumbAware {
    protected fun projectViewSelect(
        event: AnActionEvent,
        project: Project,
        viewPane: AbstractProjectViewPane,
        namedScope: NamedScope?
    ) {
        val viewSelect = ViewSelect(viewPane, namedScope)

        ProjectViewSelect(
            projectView = ProjectView.getInstance(project),
            windowManager = ToolWindowManager.getInstance(project)
        ).run {
            changeView(viewSelect)
            if (shouldActivateProjectWindow(viewSelect)) {
                activateProjectWindow(event)
            }
        }
    }

    private fun activateProjectWindow(event: AnActionEvent) {
        val project = event.project ?: return
        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("Project")
        toolWindow?.activate(null)
    }
}

private class ProjectViewSelect(
    val projectView: ProjectView,
    val windowManager: ToolWindowManager
) {
    fun shouldActivateProjectWindow(viewSelect: ViewSelect): Boolean {
        fun isProjectWindowActive(): Boolean = windowManager.activeToolWindowId == ToolWindowId.PROJECT_VIEW

        fun isCurrentViewPane(): Boolean =
            with(projectView.currentProjectViewPane) {
                id == viewSelect.id
                    && subId == (viewSelect.subId ?: subId)
            }

        return !isProjectWindowActive() || isCurrentViewPane()
    }

    fun changeView(viewSelect: ViewSelect) {
        with(viewSelect) {
            projectView.changeView(id, subId)
        }
    }
}