package dai.pipeline

import org.gradle.api.Project
import org.gradle.api.provider.Property

open class PipelineExtension(project: Project) {

    companion object {
        @JvmStatic
        fun create(project: Project): PipelineExtension {
            return project.extensions.create("pipeline", PipelineExtension::class.java, project)
        }
    }

    val greeting: Property<String> = project.objects.property(String::class.java)

    init {
        greeting.convention("Hello")
    }

}