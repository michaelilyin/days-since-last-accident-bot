package net.dslab.slack.json

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import io.quarkus.jackson.ObjectMapperCustomizer
import net.dslab.slack.json.deserialize.InteractionCallbackIdDeserializer
import net.dslab.slack.json.deserialize.InteractionCommandTypeDeserializer
import net.dslab.slack.json.deserialize.SlackCallbackTypeDeserializer
import net.dslab.slack.model.InteractionCallbackId
import net.dslab.slack.model.InteractionType
import net.dslab.slack.model.SlackCallbackType
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class SlackJsonConfiguration : ObjectMapperCustomizer {
    override fun customize(objectMapper: ObjectMapper) {
        val module = SimpleModule("SlackModule").apply {
            addDeserializer(SlackCallbackType::class.java, SlackCallbackTypeDeserializer())
            addDeserializer(InteractionCallbackId::class.java, InteractionCallbackIdDeserializer())
            addDeserializer(InteractionType::class.java, InteractionCommandTypeDeserializer())
        }

        objectMapper.registerModule(module)
        objectMapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS)
    }
}
