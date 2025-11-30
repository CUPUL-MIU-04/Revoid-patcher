package com.revoid.patcher.usage.resource.annotation

import com.revoid.patcher.annotation.Compatibility
import com.revoid.patcher.annotation.Package

@Compatibility(
    [Package(
        "com.example.examplePackage", arrayOf("0.0.1", "0.0.2")
    )]
)
@Target(AnnotationTarget.CLASS)
internal annotation class ExampleResourceCompatibility

