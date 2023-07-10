package com.github.tompower.projectViewSelect

import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.projectView.impl.AbstractProjectViewPane
import com.intellij.openapi.wm.ToolWindowId
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.psi.search.scope.packageSet.NamedScope

class ProjectViewSelect(
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
            projectView.changeViewCB(id, subId)
        }
    }
}

data class ViewSelect(
    private val viewPane: AbstractProjectViewPane,
    private val namedScope: NamedScope?
) {
    val id = viewPane.id
    val subId = namedScope?.subId()

    private fun NamedScope.subId() = this.toString() + "; " + this.javaClass
}