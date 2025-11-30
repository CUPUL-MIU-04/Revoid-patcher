package com.revoid.patcher.util.proxy.mutableTypes

import com.revoid.patcher.util.proxy.mutableTypes.encodedValue.MutableEncodedValue
import com.revoid.patcher.util.proxy.mutableTypes.encodedValue.MutableEncodedValue.Companion.toMutable
import org.jf.dexlib2.base.BaseAnnotationElement
import org.jf.dexlib2.iface.AnnotationElement
import org.jf.dexlib2.iface.value.EncodedValue

class MutableAnnotationElement(annotationElement: AnnotationElement) : BaseAnnotationElement() {
    private var name = annotationElement.name
    private var value = annotationElement.value.toMutable()

    fun setName(name: String) {
        this.name = name
    }

    fun setValue(value: MutableEncodedValue) {
        this.value = value
    }

    override fun getName(): String {
        return name
    }

    override fun getValue(): EncodedValue {
        return value
    }

    companion object {
        fun AnnotationElement.toMutable(): MutableAnnotationElement {
            return MutableAnnotationElement(this)
        }
    }
}