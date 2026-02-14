package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.View
import com.intellij.openapi.actionSystem.AnAction

class SelectProjectActionTest : ProjectViewSelectActionContract() {
    override val action: () -> AnAction = { projectViewAction }
    override val view: () -> View = { projectView }
    override val otherAction: () -> AnAction = { changedFilesViewAction }
    override val otherView: () -> View = { changedFilesView }
}