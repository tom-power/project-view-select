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

abstract class AbstractProjectViewSelectTestCase : LightPlatformTestCase() {
    protected lateinit var manager: ToolWindowManager
    protected lateinit var currentProjectView: ProjectView
    private lateinit var toolWindow: com.intellij.openapi.wm.ToolWindow

    protected var currentProjectViewPane: AbstractProjectViewPane? = null

    protected val projectView: View
        get() = View(
            viewPane = ProjectViewPane(project),
            namedScope = null
        )

    protected val allChangedFilesView: View
        get() = View(
            viewPane = ScopeViewPane(project),
            namedScope = ChangeListScope(ChangeListManager.getInstance(project))
        )

    final override fun runInDispatchThread() = true

    public override fun setUp() {
        super.setUp()
        setUpMocks()
    }

    private var activeWindow: String? = null

    protected fun projectWindowIsActive() {
        assertNotNull(activeWindow)
    }

    protected fun projectWindowIsInactive() {
        assertNull(activeWindow)
    }

    private fun setUpMocks() {
        manager = Mockito.mock(ToolWindowManager::class.java)


        toolWindow = Mockito.mock(com.intellij.openapi.wm.ToolWindow::class.java)

        Mockito.`when`(manager.activeToolWindowId).thenAnswer { activeWindow }

        Mockito.doAnswer {
            activeWindow = "Project"
            null
        }.`when`(toolWindow).activate(null)

        Mockito.doAnswer {
            activeWindow = null
            null
        }.`when`(toolWindow).hide()

        Mockito.`when`(manager.getToolWindow("Project")).thenReturn(toolWindow)

        project.replaceService(ToolWindowManager::class.java, manager, testRootDisposable)

        currentProjectView = Mockito.mock(ProjectView::class.java)

        project.replaceService(ProjectView::class.java, currentProjectView, testRootDisposable)

        Mockito.doAnswer { invocation ->
            val id = invocation.getArgument<String>(0)
            val subId = invocation.getArgument<String?>(1)

            currentProjectViewPane = Mockito.mock(AbstractProjectViewPane::class.java)
            Mockito.`when`(currentProjectViewPane?.id).thenReturn(id)
            Mockito.`when`(currentProjectViewPane?.subId).thenReturn(subId)
            null
        }.`when`(currentProjectView).changeView(Mockito.anyString(), Mockito.nullable(String::class.java))

        Mockito.`when`(currentProjectView.currentProjectViewPane).thenAnswer { currentProjectViewPane }
    }
}