package com.github.tompower.projectViewSelect

import com.intellij.testFramework.SkipInHeadlessEnvironment

@SkipInHeadlessEnvironment
class ProjectViewSelectTest : AbstractProjectWindowTestCase() {
    private lateinit var projectViewSelect: ProjectViewSelect

    override fun setUp() {
        super.setUp()
        projectViewSelect = ProjectViewSelect(projectView!!, manager!!)
    }

    fun testChangeView() {
        actionToViewSelect.values.forEach { viewSelect ->
            projectViewSelect.changeView(viewSelect)
            with(viewSelect) {
                assertEquals(id, currentProjectViewPane?.id)
                assertEquals(subId, currentProjectViewPane?.subId)
            }
        }
    }
}