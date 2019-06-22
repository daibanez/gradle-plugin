import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    "kotlin"
    kotlin("jvm").version("1.3.40")
    id("maven-publish")
    id("java-gradle-plugin")
    id("com.github.ben-manes.versions").version("0.21.0")
}

repositories {
    mavenCentral()
    jcenter()
}

group = "dai.pipeline"
version = "0.0.1-SNAPSHOT"

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
}

val test: Test by tasks
test.useJUnitPlatform()

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets.main.get().allSource)
}

gradlePlugin {
    plugins {
        create("pipeline") {
            id = "dai.pipeline"
            implementationClass = "dai.pipeline.PipelinePlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
//            artifact(sourcesJar.get())
        }
    }
    repositories {
        maven {
            url = uri("$buildDir/repository")
        }
    }
}

sourceSets {
    create("functionalTest") {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/functionalTest/kotlin")
            resources.srcDir("src/functionalTest/resources")
            compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
            runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
        }
    }
}

task<Test>("functionalTest") {
    description = "Runs the functional tests"
    group = "verification"
    testClassesDirs = sourceSets["functionalTest"].output.classesDirs
    classpath = sourceSets["functionalTest"].runtimeClasspath
    //mustRunAfter(tasks["test"])
    useJUnitPlatform()
}

//check.dependsOn functionalTest

val implementation by configurations
val testImplementation by configurations
val testRuntimeOnly by configurations
dependencies {

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // plugin stuff
    implementation(gradleApi())
    testImplementation(gradleTestKit())

    // Testing: JUnit5 and kotlin utilities
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testImplementation("org.mockito:mockito-inline:2.28.2")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.17")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

gradle.taskGraph.whenReady {
    println("================================================================================")
    println("Task Graph Analysis")
    println(": $this")
    println(": ${this.allTasks.size} tasks)")
    this.allTasks.forEach { task ->
        println("> $task")
        task.dependsOn.forEach { dep ->
            println("    - $dep")
        }
    }
    println("================================================================================")
}
