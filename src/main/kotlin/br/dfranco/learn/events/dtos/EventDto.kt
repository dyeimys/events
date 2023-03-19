package br.dfranco.learn.events.dtos

import br.dfranco.learn.events.enuns.EventStatusEnum
import java.time.LocalDateTime
import java.util.UUID

data class EventDto(
        var id: UUID? = null,
        var name: String,
        var date: LocalDateTime,
        var locationId: UUID?,
        var owner: String,
        var status: EventStatusEnum? = EventStatusEnum.UNPUBLISHED,
        var creationDate: LocalDateTime? = null,
        var updateDate: LocalDateTime? = null
)
