package com.revoid.patcher

import com.revoid.patcher.apk.ApkBundle
import com.revoid.patcher.logging.Logger

/**
 * Options for the [Patcher].
 * @param apkBundle The [ApkBundle].
 * @param logger Custom logger implementation for the [Patcher].
 */
class PatcherOptions(
    internal val apkBundle: ApkBundle,
    internal val logger: Logger = Logger.Nop
)