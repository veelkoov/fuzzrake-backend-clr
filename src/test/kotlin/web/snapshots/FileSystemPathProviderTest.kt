package web.snapshots

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import web.url.FreeUrl

class FileSystemPathProviderTest {
    private val subject = FileSystemPathProvider()

    @Test
    fun getSnapshotDirPath() {
        var result = subject.getSnapshotDirPath(FreeUrl("https://www.tumblr.com/getfursuit"))
        assertTrue(
            result.startsWith("T/tumblr.com/getfursuit"),
            "Unexpected prefix: $result",
        )

        result = subject.getSnapshotDirPath(FreeUrl("https://furries.club/@getfursuit"))
        assertTrue(
            result.startsWith("F/furries.club/getfursuit"),
            "Unexpected prefix: $result",
        )

        result = subject.getSnapshotDirPath(FreeUrl("https://github.com/veelkoov/fuzzrake/pull/194"))
        assertTrue(
            result.startsWith("G/github.com/veelkoov_fuzzrake_pull_194"),
            "Unexpected prefix: $result",
        )
    }
}
