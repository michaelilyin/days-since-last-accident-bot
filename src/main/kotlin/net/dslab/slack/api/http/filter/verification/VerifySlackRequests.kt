package net.dslab.slack.api.http.filter.verification

import javax.ws.rs.NameBinding

@NameBinding
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class VerifySlackRequests
