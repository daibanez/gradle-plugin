package dai.pipeline

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PipelinePluginTest : ProjectBuilderTestBase() {

    @Test
    fun `should give project the helloTask task`() {
        val task = project.tasks.getByName("helloTask")
        assertThat(task).isInstanceOf(HelloTask::class.java)
    }

}
