package dai.pipeline

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class PipelinePluginFunctionalTest : FunctionalTestBase() {

    /**
     * 4.9 project.register(name,TaskClass)
     * 5.1 property.convention(defaultValue)
     * 6.0 Gradle beings support for JDK13
     */
    @Test
    fun `should be compatible with Gradle v6_0+`(@TempDir tempDir: Path) {
        tempDir.resolve("build.gradle").apply {
            Files.createFile(this)
            this.toFile().fillFromResource("default.gradle")
        }
        val runResult = GradleRunner.create()
            .withGradleVersion("6.0")
            .withProjectDir(tempDir.toFile())
            .withArguments("helloTask")
            .withTestKitDir(tempDir.resolve("testKitDir").toFile())
            .withPluginClasspath()
            .build()
        println("""
            \/\/\/\/\/\/\/\/\/\/
            ${runResult.output}
            /\/\/\/\/\/\/\/\/\/\
        """.trimIndent())
        assertThat(runResult.output).contains("Hello, world!")
        assertThat(runResult.task(":helloTask")?.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }

    @Test
    fun `should have configurable greeting`(@TempDir tempDir: Path) {
        tempDir.resolve("build.gradle").apply {
            Files.createFile(this)
            this.toFile().fillFromResource("configured.gradle")
        }
        val runResult = GradleRunner.create()
            .withGradleVersion("6.1")
            .withProjectDir(tempDir.toFile())
            .withArguments("helloTask")
            .withTestKitDir(tempDir.resolve("testKitDir").toFile())
            .withPluginClasspath()
            .build()
        println("""
            \/\/\/\/\/\/\/\/\/\/
            ${runResult.output}
            /\/\/\/\/\/\/\/\/\/\
        """.trimIndent())
        assertThat(runResult.output).contains("Hola, world!")
        assertThat(runResult.task(":helloTask")?.outcome).isEqualTo(TaskOutcome.SUCCESS)
    }

}
