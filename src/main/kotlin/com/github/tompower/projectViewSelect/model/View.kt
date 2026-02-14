package com.github.tompower.projectViewSelect.model

import com.intellij.ide.projectView.impl.AbstractProjectViewPane
import com.intellij.psi.search.scope.packageSet.NamedScope

data class View(
    val id: String,
    val subId: String?,
) {
    constructor(
        viewPane: AbstractProjectViewPane,
        namedScope: NamedScope?
    ) : this(
        id = viewPane.id,
        subId = namedScope?.run { this.toString() + "; " + this.javaClass }
    )
}