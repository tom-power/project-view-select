package com.github.tompower.projectViewActions

import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.projectView.impl.ProjectViewImpl
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.ex.ProjectManagerEx
import com.intellij.openapi.wm.ToolWindowId
import com.intellij.openapi.wm.ToolWindowManager

abstract class ProjectViewAction : AnAction(), DumbAware {
    protected fun changeViewAction(viewPaneId: String, event: AnActionEvent) {
        projectView()?.let { projectView ->
            if(!isProjectWindowActive() || projectView.isCurrent(viewPaneId)) {
                activateProjectWindow(event)
            }
            projectView.changeView(viewPaneId)
        }
    }

    private fun projectView(): ProjectView? =
        project()?.let {
            ProjectView.getInstance(it) as ProjectViewImpl
        }

    private fun ProjectView.isCurrent(viewPaneId: String): Boolean = currentProjectViewPane?.id == viewPaneId

    private fun isProjectWindowActive(): Boolean = windowManager()?.activeToolWindowId == ToolWindowId.PROJECT_VIEW

    private fun windowManager() =
        project()
            ?.let { ToolWindowManager.getInstance(it) }

    private fun project() = ProjectManagerEx.getInstance().openProjects.singleOrNull { it.isOpen }


    private fun projectToolWindow() =
        windowManager()
            ?.getToolWindow(ToolWindowId.PROJECT_VIEW)


    private fun activateProjectWindow(event: AnActionEvent) {
        ActionManager.getInstance()
            .getAction("ActivateProjectToolWindow")
            .actionPerformed(event)
    }
}
