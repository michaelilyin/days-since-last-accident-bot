package net.dslab

import java.io.InputStream

fun Any.resource(path: String): InputStream {
    return javaClass.classLoader.getResourceAsStream(path)
        ?: throw RuntimeException("Not Found $path")
}

fun Any.slackHttpResource(path: String): InputStream {
    return resource("/net/dslab/slack/api/http/$path")
}
