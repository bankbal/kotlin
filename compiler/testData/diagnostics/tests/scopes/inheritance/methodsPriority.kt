// !WITH_NEW_INFERENCE
// !DIAGNOSTICS: -UNUSED_VARIABLE
// FILE: A.java
public class A {
    int foo() {return 1;}
}

// FILE: 1.kt

fun foo() = ""

open class B: A() {
    init {
        val a: Int = <!DEBUG_INFO_LEAKING_THIS!>foo<!>()
    }
}

fun test() {
    fun foo() = ""

    class B: A() {
        init {
            val a: Int = <!NI;TYPE_MISMATCH, TYPE_MISMATCH!>foo()<!> // todo
        }
    }
}

class E: A() {
    fun <!VIRTUAL_MEMBER_HIDDEN!>foo<!>() = A()

    init {
        val a: A = foo() // todo: discuss
    }
}