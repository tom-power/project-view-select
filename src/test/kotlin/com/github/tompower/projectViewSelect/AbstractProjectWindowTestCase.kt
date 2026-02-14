package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.View
import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.projectView.impl.AbstractProjectViewPane
import com.intellij.ide.projectView.impl.ProjectViewPane
import com.intellij.ide.scopeView.ScopeViewPane
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.testFramework.LightPlatformTestCase
import com.intellij.testFramework.replaceService
import com.intellij.vcs.changes.ChangeListScope
import org.mockito.Mockito

abstract class AbstractProjectWindowTestCase : LightPlatformTestCase() {
    protected lateinit var manager: ToolWindowManager
    protected lateinit var projectView: ProjectView
    private lateinit var toolWindow: com.intellij.openapi.wm.ToolWindow

    protected var currentProjectViewPane: AbstractProjectViewPane? = null

    protected val projectViewSelectProject: View
        get() = View(
            viewPane = ProjectViewPane(project),
            namedScope = null
        )

    protected val projectViewSelectScopeAllChangedFiles: View
        get() = View(
            viewPane = ScopeViewPane(project),
            namedScope = ChangeListScope(ChangeListManager.getInstance(project))
        )

    final override fun runInDispatchThread() = true

    public override fun setUp() {
        super.setUp()
        setUpMocks()
    }

    protected fun setProjectWindowActive() {
        Mockito.`when`(manager.activeToolWindowId).thenReturn( "Project")
    }

    protected fun verifyProjectWindowDeactivated() {
        Mockito.verify(toolWindow).hide()
    }

    private fun setUpMocks() {
        manager = Mockito.mock(ToolWindowManager::class.java)
        Mockito.`when`(manager.activeToolWindowId).thenReturn(null)

        toolWindow = Mockito.mock(com.intellij.openapi.wm.ToolWindow::class.java)
        Mockito.`when`(manager.getToolWindow("Project")).thenReturn(toolWindow)

        project.replaceService(ToolWindowManager::class.java, manager, testRootDisposable)

        projectView = Mockito.mock(ProjectView::class.java)

        project.replaceService(ProjectView::class.java, projectView, testRootDisposable)

        Mockito.doAnswer { invocation ->
            val id = invocation.getArgument<String>(0)
            val subId = invocation.getArgument<String?>(1)

            currentProjectViewPane = Mockito.mock(AbstractProjectViewPane::class.java)
            Mockito.`when`(currentProjectViewPane?.id).thenReturn(id)
            Mockito.`when`(currentProjectViewPane?.subId).thenReturn(subId)
            null
        }.`when`(projectView).changeView(Mockito.anyString(), Mockito.nullable(String::class.java))

        Mockito.`when`(projectView.currentProjectViewPane).thenAnswer { currentProjectViewPane }
    }
}