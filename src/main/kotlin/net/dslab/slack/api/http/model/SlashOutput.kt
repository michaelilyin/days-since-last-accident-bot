package net.dslab.slack.api.http.model

import com.slack.api.model.block.LayoutBlock

data class SlashOutput(
    val blocks: List<LayoutBlock>
) {
}
