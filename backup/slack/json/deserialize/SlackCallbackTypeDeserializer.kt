package ilyin.slack.json.deserialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import ilyin.slack.model.SlackCallbackType

class SlackCallbackTypeDeserializer :
    StdDeserializer<SlackCallbackType>(SlackCallbackType::class.java) {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): SlackCallbackType {
        val string = p.valueAsString
        return SlackCallbackType.valueOf(string.uppercase())
    }
}
