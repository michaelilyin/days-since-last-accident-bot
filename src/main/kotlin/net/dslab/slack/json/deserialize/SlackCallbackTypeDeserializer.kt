package net.dslab.slack.json.deserialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import net.dslab.slack.model.SlackCallbackType

class SlackCallbackTypeDeserializer :
    StdDeserializer<SlackCallbackType>(SlackCallbackType::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): SlackCallbackType {
        val string = p.valueAsString

        return SlackCallbackType.fromString(string)
    }
}
