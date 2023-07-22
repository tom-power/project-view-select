@file:Suppress("RAW_RUN_BLOCKING")

package com.github.tompower.projectViewSelect

import com.intellij.ide.SelectInTarget
import com.intellij.ide.projectView.ProjectView
import com.intellij.ide.projectView.impl.AbstractProjectViewPane
import com.intellij.ide.projectView.impl.ProjectViewPane
import com.intellij.ide.scopeView.ScopeViewPane
import com.intellij.openapi.util.ActionCallback
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vcs.changes.ChangeListManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.ToolWindowEP
import com.intellij.openapi.wm.ToolWindowId
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.openapi.wm.impl.IdeFrameImpl
import com.intellij.openapi.wm.impl.ProjectFrameHelper
import com.intellij.openapi.wm.impl.ToolWindowManagerImpl
import com.intellij.testFramework.LightPlatformTestCase
import com.intellij.testFramework.SkipInHeadlessEnvironment
import com.intellij.testFramework.replaceService
import com.intellij.vcs.changes.ChangeListScope
import javax.swing.Icon
import javax.swing.JComponent

@SkipInHeadlessEnvironment
abstract class AbstractProjectWindowTestCase : LightPlatformTestCase() {
    @JvmField
    protected var manager: ToolWindowManagerImpl? = null
    protected var projectView: ProjectView? = null
    protected val currentProjectViewPane: AbstractProjectViewPane?
        get() = projectView?.currentProjectViewPane

    protected val actionToViewSelect
        get() = mapOf(
            "ProjectViewSelectProject" to
                ViewSelect(
                    viewPane = ProjectViewPane(project),
                    namedScope = null
                ),
            "ProjectViewSelectScopeAllChangedFiles" to
                ViewSelect(
                    viewPane = ScopeViewPane(project),
                    namedScope = ChangeListScope(ChangeListManager.getInstance(project))
                )
        )

    final override fun runInDispatchThread() = true

    public override fun setUp() {
        super.setUp()
        replaceToolWindowManager()
        addProjectWindow()
        setUpProjectView()
    }

    private fun setUpProjectView() {
        projectView = ProjectView.getInstance(project)
        with(projectView!!) {
            ProjectViewPane(project).let {
                removeProjectPane(it)
                addProjectPane(it)
            }
            scopeViewPane(withWeight = 99).apply { subId = allChangeFilesScopeId }.let {
                removeProjectPane(it)
                addProjectPane(it)
            }
        }
    }

    private val allChangeFilesScopeId =
        "Scope 'All Changed Files'; set:All Changed Files; ALL; class com.intellij.vcs.changes.ChangeListScope"

    private fun replaceToolWindowManager() {
        manager = ToolWindowManagerImpl(project = this.project)
        this.project.replaceService(ToolWindowManager::class.java, manager!!, testRootDisposable)
        val frame = ProjectFrameHelper(IdeFrameImpl(), null).apply { init() }
        manager!!.doInit(frame, this.project.messageBus.connect(testRootDisposable))
    }

    private fun addProjectWindow() {
        for (extension in ToolWindowEP.EP_NAME.extensionList) {
            if (ToolWindowId.PROJECT_VIEW == extension.id) {
                manager!!.initToolWindow(extension)
            }
        }
    }

    private fun scopeViewPane(withWeight: Int): AbstractProjectViewPane {
        val scopeViewPane = ScopeViewPane(project)
        return object : AbstractProjectViewPane(project) {
            override fun getWeight(): Int {
                return withWeight
            }

            override fun getPresentableSubIdName(subId: String): String {
                return allChangeFilesScopeId
            }

            override fun getSubIds(): Array<String> {
                return arrayOf(allChangeFilesScopeId)
            }

            override fun getTitle(): String {
                return scopeViewPane.title
            }

            override fun getIcon(): Icon {
                return scopeViewPane.icon
            }

            override fun getId(): String {
                return scopeViewPane.id
            }

            override fun createComponent(): JComponent {
                return scopeViewPane.createComponent()
            }

            override fun updateFromRoot(restoreExpandedPaths: Boolean): ActionCallback {
                return scopeViewPane.updateFromRoot(restoreExpandedPaths)
            }

            override fun select(element: Any?, file: VirtualFile?, requestFocus: Boolean) {
                scopeViewPane.select(element, file, requestFocus)
            }

            override fun createSelectInTarget(): SelectInTarget {
                return scopeViewPane.createSelectInTarget()
            }
        }
    }
}