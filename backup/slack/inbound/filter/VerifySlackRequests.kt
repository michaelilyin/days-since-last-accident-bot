package ilyin.slack.inbound.filter

import javax.ws.rs.NameBinding

@NameBinding
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class VerifySlackRequests
