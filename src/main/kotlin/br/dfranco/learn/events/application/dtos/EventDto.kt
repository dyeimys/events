package br.dfranco.learn.events.application.dtos

import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import java.time.LocalDateTime
import java.util.*

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
