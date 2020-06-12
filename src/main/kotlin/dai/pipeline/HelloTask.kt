package dai.pipeline

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskProvider

open class HelloTask : DefaultTask() {

    companion object {
        @JvmStatic
        fun register(project: Project, pipelineExtension: PipelineExtension): TaskProvider<HelloTask> {
            return project.tasks.register("helloTask", HelloTask::class.java) {
                this.greeting.set(pipelineExtension.greeting)
            }
        }
    }

    @Input
    val greeting: Property<String> = project.objects.property(String::class.java)

    init {
        group = "pipeline"
        description = "prints a friendly hello message"
    }

    @TaskAction
    fun action() {
        println("${greeting.get()}, world!")
    }

}
