package com.revoid.patcher.fingerprint.method.annotation

import com.revoid.patcher.fingerprint.method.impl.MethodFingerprint

/**
 * Annotations to scan a pattern [MethodFingerprint] with fuzzy algorithm.
 * @param threshold if [threshold] or more of the opcodes do not match, skip.
 */
@Target(AnnotationTarget.CLASS)
annotation class FuzzyPatternScanMethod(
    val threshold: Int = 1
)