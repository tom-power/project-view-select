package com.github.tompower.projectViewSelect

import com.github.tompower.projectViewSelect.model.View
import com.intellij.openapi.actionSystem.AnAction

class ProjectViewSelectProjectActionTest : ProjectViewSelectActionContract() {
    override val action: () -> AnAction = { selectProjectViewAction }
    override val view: () -> View = { projectView }
    override val otherAction: () -> AnAction = { selectChangedFilesViewAction }
}