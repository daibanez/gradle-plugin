package dai.pipeline

import org.junit.jupiter.api.TestInstance
import java.io.File

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
