package net.dslab.slack.json.deserialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dslab.slack.model.InteractionType

class InteractionCommandTypeDeserializer :
    StdDeserializer<InteractionType>(InteractionType::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): InteractionType {
        val string = p.valueAsString

        return InteractionType.fromString(string)
    }
}
