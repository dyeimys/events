package br.dfranco.learn.events.web.response

import br.dfranco.learn.events.dtos.LocationDto
import br.dfranco.learn.events.enuns.EventStatusEnum
import java.time.LocalDateTime
import java.util.*

data class EventResponse(
        var id: UUID? = null,
        var name: String,
        var date: LocalDateTime,
        var location: LocationDto,
        var owner: String,
        var status: EventStatusEnum = EventStatusEnum.UNPUBLISHED,
        var creationDate: LocalDateTime? = null,
        var updateDate: LocalDateTime? = null
) {
}