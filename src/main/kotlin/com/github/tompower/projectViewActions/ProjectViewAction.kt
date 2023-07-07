package com.github.tompower.projectViewActions

import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.projectView.impl.ProjectViewImpl
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.ex.ProjectManagerEx


open class ProjectViewAction : AnAction(), DumbAware {
    fun changeView(id: String) {
        val projectManager = ProjectManagerEx.getInstance()
        projectManager.openProjects.singleOrNull { it.isOpen }?.let {
            val projectView = ProjectView.getInstance(it) as ProjectViewImpl
            projectView.changeView(id)
        }
    }

    override fun actionPerformed(event: AnActionEvent) {
        val actionManager = ActionManager.getInstance()
        actionManager.getAction("ActivateProjectToolWindow").actionPerformed(event)
    }

    override fun update(event: AnActionEvent) {
        super.update(event)
    }
}
