package com.github.tompower.projectViewSelect

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import junit.framework.TestCase
import org.junit.Test

class ProjectViewSelectTest : BasePlatformTestCase() {

    @Test
    fun `test select project view`() {
        val testAction = myFixture.testAction(Project())

        TestCase.assertTrue(testAction.isEnabledAndVisible)
    }

    @Test
    fun `test select scope all changed files view`() {
        val testAction = myFixture.testAction(ScopeAllChangedFiles())

        TestCase.assertTrue(testAction.isEnabledAndVisible)
    }
}