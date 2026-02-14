package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.View
import com.intellij.openapi.actionSystem.AnAction

class SelectChangedFilesActionTest : ProjectViewSelectActionContract() {
    override val action: () -> AnAction = { changedFilesViewAction }
    override val view: () -> View = { changedFilesView }
    override val otherAction: () -> AnAction = { projectViewAction }
    override val otherView: () -> View = { projectView }
}