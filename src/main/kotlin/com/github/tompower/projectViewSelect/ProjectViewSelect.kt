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
import com.intellij.psi.search.scope.packageSet.NamedScope

abstract class ProjectViewSelect : AnAction(), DumbAware {
    protected fun action(
        event: AnActionEvent,
        viewPane: AbstractProjectViewPane,
        namedScope: NamedScope?
    ) {
        event.project?.let { project ->
            ProjectViewSelectAction(project, event).invoke(viewPane, namedScope)
        }
    }

    class ProjectViewSelectAction(
        private val project: Project,
        private val event: AnActionEvent,
    ) : (AbstractProjectViewPane, NamedScope?) -> Unit {
        private val projectView: ProjectView get() = ProjectView.getInstance(project) as ProjectViewImpl
        private val windowManager: ToolWindowManager get() = ToolWindowManager.getInstance(project)

        override fun invoke(viewPane: AbstractProjectViewPane, namedScope: NamedScope?) {
            changeView(viewPane, namedScope)
            if (shouldActivateProjectWindow(viewPane, namedScope)) {
                activateProjectWindow()
            }
        }

        private fun shouldActivateProjectWindow(viewPane: AbstractProjectViewPane, namedScope: NamedScope?): Boolean {
            fun isCurrentViewPane(): Boolean {
                fun ProjectView.isCurrentViewPaneId(): Boolean = currentProjectViewPane.id == viewPane.id
                fun ProjectView.isCurrentViewPaneSubId(): Boolean =
                    namedScope
                        ?.let { currentProjectViewPane.subId == it.subId() }
                        ?: true

                return projectView.isCurrentViewPaneId() && projectView.isCurrentViewPaneSubId()
            }

            fun isProjectWindowActive(): Boolean = windowManager.activeToolWindowId == ToolWindowId.PROJECT_VIEW

            return !isProjectWindowActive() || isCurrentViewPane()
        }

        private fun activateProjectWindow() {
            ActionManager.getInstance()
                .getAction("ActivateProjectToolWindow")
                .actionPerformed(event)
        }

        private fun changeView(viewPane: AbstractProjectViewPane, namedScope: NamedScope?) {
            namedScope
                ?.let { projectView.changeViewCB(viewPane.id, namedScope.subId()) }
                ?: projectView.changeView(viewPane.id)
        }

        private fun NamedScope.subId() = this.toString() + "; " + this.javaClass
    }
}
