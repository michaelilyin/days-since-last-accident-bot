package net.dslab.slack.service.command

import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
internal class SlackCommandResultBuilderFactoryImplTest {

    @Inject
    private lateinit var slackCommandResultBuilderFactory: SlackCommandResultBuilderFactory

    @Test
    internal fun builderText() {
        val builder = slackCommandResultBuilderFactory.builder()
        builder.plainText("test")

        val res = builder.build()

        assertEquals("test", res)
    }
}
