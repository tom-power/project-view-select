package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.View
import com.intellij.openapi.actionSystem.AnAction
import org.junit.Test

abstract class ProjectViewSelectActionContract : AbstractProjectViewSelectTestCase() {
    abstract val action: () -> AnAction
    abstract val view: () -> View
    abstract val otherAction: () -> AnAction
    abstract val otherView: () -> View

    @Test
    fun `can select a view`() {
        Given {
            projectWindowIsInactive()
        }

        When {
            performAction(action())
        }

        Then {
            projectWindowIsActive()
            currentViewIs(view())
        }
    }

    @Test
    fun `can select a view from another view`() {
        Given {
            projectWindowIsInactive()
            performAction(otherAction())
            currentViewIs(otherView())
        }

        When {
            performAction(action())
        }

        Then {
            projectWindowIsActive()
            currentViewIs(view())
        }
    }

    @Test
    fun `project window is deactivated when action matches view`() {
        Given {
            projectWindowIsInactive()
            performAction(action())
        }

        Then {
            projectWindowIsActive()
            currentViewIs(view())
        }

        When {
            performAction(action())
        }

        Then {
            projectWindowIsInactive()
        }
    }
}