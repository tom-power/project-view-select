package com.github.tompower.projectViewSelect.action

import com.github.tompower.projectViewSelect.ProjectViewSelect
import com.github.tompower.projectViewSelect.ViewSelect
import com.intellij.ide.actions.ActivateToolWindowAction
import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.projectView.impl.AbstractProjectViewPane
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
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
        (ActionManager.getInstance()
            .getAction("ActivateProjectToolWindow") as ActivateToolWindowAction)
            .actionPerformed(event)
    }
}