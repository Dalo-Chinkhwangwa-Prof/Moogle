package com.example.admin.moogle.util.rx


sealed class Optional<T> {
    class Some<T>(val result: T) : Optional<T>()
    fun asNullable(): T? {
        return if (this is Some<T>) {
            this.result
        } else null
    }
}