package com.design.hub.annotation

import org.springframework.security.access.prepost.PreAuthorize
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('ROLE_BACKOFFICE')")
annotation class IsBackoffice
