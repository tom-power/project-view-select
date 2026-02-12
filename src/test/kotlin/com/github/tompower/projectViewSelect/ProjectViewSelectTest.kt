package com.github.tompower.projectViewSelect

class ProjectViewSelectTest : AbstractProjectWindowTestCase() {
    private lateinit var projectViewSelect: ProjectViewSelect

    override fun setUp() {
        super.setUp()
        projectViewSelect = ProjectViewSelect(projectView, manager)
    }

    fun testChangeView() {
        projectViewSelectProject.let { viewSelect ->
            projectViewSelect.changeView(viewSelect)
            with(viewSelect) {
                assertEquals(id, currentProjectViewPane?.id)
                assertEquals(subId, currentProjectViewPane?.subId)
            }
        }
        projectViewSelectScopeAllChangedFiles.let { viewSelect ->
            projectViewSelect.changeView(viewSelect)
            with(viewSelect) {
                assertEquals(id, currentProjectViewPane?.id)
                assertEquals(subId, currentProjectViewPane?.subId)
            }
        }
    }
}