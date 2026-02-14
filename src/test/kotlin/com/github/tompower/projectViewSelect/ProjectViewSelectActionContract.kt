package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.View
import com.intellij.openapi.actionSystem.AnAction

abstract class ProjectViewSelectActionContract : AbstractProjectViewSelectTestCase() {
    abstract val action: () -> AnAction
    abstract val view: () -> View
    abstract val otherAction: () -> AnAction

    fun `test select view action`() {
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

    fun `test deactivate project window when view select action matches view`() {
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

    fun `test view select action from other view`() {
        Given {
            projectWindowIsInactive()
            performAction(otherAction())
        }

        When {
            performAction(action())
        }

        Then {
            projectWindowIsActive()
            currentViewIs(view())
        }
    }
}