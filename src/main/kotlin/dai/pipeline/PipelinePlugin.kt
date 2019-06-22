package dai.pipeline

import org.gradle.api.Plugin
import org.gradle.api.Project

class PipelinePlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val pipelineExtension = PipelineExtension.create(project)
        HelloTask.register(project, pipelineExtension)
    }

}

