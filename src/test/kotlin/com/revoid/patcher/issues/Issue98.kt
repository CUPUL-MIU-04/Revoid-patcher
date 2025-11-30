package com.revoid.patcher.issues

import com.revoid.patcher.patch.PatchOption
import org.junit.jupiter.api.Test
import kotlin.test.assertNull

internal class Issue98 {
    companion object {
        var key1: String? by PatchOption.StringOption(
            "key1", null, "title", "description"
        )
    }

    @Test
    fun `should infer nullable type correctly`() {
        assertNull(key1)
    }
}