package com.revoid.patcher.usage.resource.patch

import com.revoid.patcher.ResourceContext
import com.revoid.patcher.annotation.Description
import com.revoid.patcher.annotation.Name
import com.revoid.patcher.annotation.Version
import com.revoid.patcher.apk.Apk
import com.revoid.patcher.patch.ResourcePatch
import com.revoid.patcher.patch.annotations.Patch
import com.revoid.patcher.usage.resource.annotation.ExampleResourceCompatibility
import org.w3c.dom.Element

@Patch
@Name("example-resource-patch")
@Description("Example demonstration of a resource patch.")
@ExampleResourceCompatibility
@Version("0.0.1")
class ExampleResourcePatch : ResourcePatch {
    override fun execute(context: ResourceContext) {
        context.apkBundle.base.resources.openXmlFile(Apk.manifest).use { editor ->
            val element = editor // regular DomFileEditor
                .file
                .getElementsByTagName("application")
                .item(0) as Element
            element
                .setAttribute(
                    "exampleAttribute",
                    "exampleValue"
                )
        }
    }
}