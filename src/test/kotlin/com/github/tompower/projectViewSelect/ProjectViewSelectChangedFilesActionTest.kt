package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.View
import com.intellij.openapi.actionSystem.AnAction

class ProjectViewSelectChangedFilesActionTest : ProjectViewSelectActionContract() {
    override val action: () -> AnAction = { selectChangedFilesViewAction }
    override val view: () -> View = { changedFilesView }
    override val otherAction: () -> AnAction = { selectProjectViewAction }
}