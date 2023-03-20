package br.dfranco.learn.events.app.controllers.response

import br.dfranco.learn.events.domain.dtos.LocationDto
import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import java.time.LocalDateTime
import java.util.*

data class EventResponse(
        var id: UUID? = null,
        var name: String,
        var date: LocalDateTime,
        var location: LocationResponse?,
        var owner: String,
        var status: EventStatusEnum = EventStatusEnum.UNPUBLISHED
) {
}