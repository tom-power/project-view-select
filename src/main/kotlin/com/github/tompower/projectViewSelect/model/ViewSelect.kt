package com.github.tompower.projectViewSelect.model

import com.intellij.ide.projectView.impl.AbstractProjectViewPane
import com.intellij.psi.search.scope.packageSet.NamedScope

data class ViewSelect(
    private val viewPane: AbstractProjectViewPane,
    private val namedScope: NamedScope?
) {
    val id = viewPane.id
    val subId = namedScope?.run { this.toString() + "; " + this.javaClass }
}