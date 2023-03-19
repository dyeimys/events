package br.dfranco.learn.events.web.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

data class EventRequest(
        @Schema(example = "My Event", description = "Name of event")
        var name: String,
        @Schema(description = "Date of event")
        var date: LocalDateTime,
        @Schema(description = "Location of event")
        var locationId: UUID?,
        @Schema(example = "Mr. James")
        var owner: String,
)