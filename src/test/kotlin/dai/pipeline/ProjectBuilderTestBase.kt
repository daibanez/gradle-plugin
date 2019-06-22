package dai.pipeline

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class ProjectBuilderTestBase {

    protected lateinit var project: Project

    @BeforeAll
    fun setUp() {
        project = ProjectBuilder.builder().build()
        project.pluginManager.apply("dai.pipeline")
    }

}
