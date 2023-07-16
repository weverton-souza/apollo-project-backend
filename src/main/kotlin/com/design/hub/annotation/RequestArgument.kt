package com.design.hub.annotation

import com.design.hub.annotation.impl.RequestArgumentValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [RequestArgumentValidator::class])
annotation class RequestArgument(
    val message: String = "http.4xx.400-bad-request.argument-not-valid-exception",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)
