package net.dslab.slack.service.message.builder

import com.slack.api.model.block.SectionBlock
import io.quarkus.test.junit.QuarkusTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import javax.inject.Inject

@QuarkusTest
internal class SlackMessageBuilderFactoryImplTest {

    @Inject
    private lateinit var slackMessageBuilderFactory: SlackMessageBuilderFactory

    @Test
    internal fun builderText() {
        val builder = slackMessageBuilderFactory.builder()
        builder.paragraph {
            plainText("test")
        }

        val res = builder.build()

        assertNotNull(res.blocks)
        assertEquals(1, res.blocks.size)
        assertEquals("test", (res.blocks.first() as SectionBlock).text.text)
    }
}
