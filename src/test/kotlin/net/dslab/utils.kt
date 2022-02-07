package net.dslab

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.InputStream

val objectMapper = ObjectMapper()

fun Any.resource(path: String): InputStream {
    return javaClass.classLoader.getResourceAsStream(path)
        ?: throw RuntimeException("Not Found $path")
}

fun Any.slackHttpResource(path: String): InputStream {
    return resource("/net/dslab/slack/api/http/$path")
}

fun Any.firebaseResource(path: String): InputStream {
    return resource("/net/dslab/slack/firebase/$path")
}

fun InputStream.json(): Map<String, Any> {
    return objectMapper.readValue(this)
}
