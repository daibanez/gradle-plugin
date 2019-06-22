package dai.pipeline

import assertk.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class FunctionalTestBase {

//    companion object {
//        @TempDir
//        @JvmStatic
//        protected lateinit var tempDir: Path
//    }
//
//    @BeforeAll
//    fun setUp() {
//        assertThat(Files.isDirectory(tempDir))
//    }

    fun File.fillFromResource(resourceName: String) {
        ClassLoader.getSystemResourceAsStream(resourceName).use { inputStream ->
            outputStream().use { inputStream.copyTo(it) }
        }
    }

}
