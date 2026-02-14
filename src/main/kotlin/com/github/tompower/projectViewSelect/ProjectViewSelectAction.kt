package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.View
import com.intellij.ide.projectView.ProjectView
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowId
import com.intellij.openapi.wm.ToolWindowManager

abstract class ProjectViewSelectAction : AnAction(), DumbAware {
    protected fun projectViewSelect(
        project: Project,
        view: View
    ) {
        ProjectViewSelect(
            projectView = ProjectView.getInstance(project),
            windowManager = ToolWindowManager.getInstance(project),
            project = project,
        ).run {
            if (projectWindowActive() && currentViewMatches(view)) {
                deactivateProjectWindow()
            } else {
                changeView(view)
                if (!projectWindowActive()) {
                    activateProjectWindow()
                }
            }
        }
    }

}

private class ProjectViewSelect(
    private val projectView: ProjectView,
    private val windowManager: ToolWindowManager,
    private val project: Project,
) {

    fun projectWindowActive(): Boolean = windowManager.activeToolWindowId == ToolWindowId.PROJECT_VIEW

    fun activateProjectWindow() {
        projectWindow()?.activate(null)
    }

    fun deactivateProjectWindow() {
        projectWindow()?.hide()
    }

    private fun projectWindow(): ToolWindow? = ToolWindowManager.getInstance(project).getToolWindow("Project")

    fun changeView(view: View) {
        with(view) {
            projectView.changeView(id, subId)
        }
    }

    fun currentViewMatches(view: View): Boolean =
        with(projectView.currentProjectViewPane) {
            id == view.id
                && subId == (view.subId ?: subId)
        }
}