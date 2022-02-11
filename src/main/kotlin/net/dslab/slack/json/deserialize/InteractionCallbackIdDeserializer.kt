package net.dslab.slack.json.deserialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dslab.slack.model.InteractionCallbackId

class InteractionCallbackIdDeserializer :
    StdDeserializer<InteractionCallbackId>(InteractionCallbackId::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): InteractionCallbackId {
        val string = p.valueAsString

        return InteractionCallbackId.fromString(string)
    }
}
