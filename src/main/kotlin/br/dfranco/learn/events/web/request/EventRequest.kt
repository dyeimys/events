package br.dfranco.learn.events.web.request

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

data class EventRequest(
        @Schema(example = "My Event")
        var name: String,
        var date: LocalDateTime,
        var locationId: UUID?,
        @Schema(example = "Mr. James")
        var owner: String,
)