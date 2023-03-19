package br.dfranco.learn.events.web.request

import br.dfranco.learn.events.enuns.EventStatusEnum
import java.time.LocalDateTime
import java.util.*

data class EventRequest(
        var id: UUID? = null,
        var name: String,
        var date: LocalDateTime,
        var location: LocationRequest,
        var owner: String,
        var status: EventStatusEnum = EventStatusEnum.UNPUBLISHED,
        var creationDate: LocalDateTime? = null,
        var updateDate: LocalDateTime? = null
)