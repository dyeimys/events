package br.dfranco.learn.events.application.dtos

import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import java.time.LocalDateTime
import java.util.*

class Event {
    data class CreateEventInput(
            var name: String,
            var date: LocalDateTime,
            var locationId: UUID?,
            var owner: String,
    )

    data class CreateEventOutput(
            val id: UUID,
            val name: String,
            val date: LocalDateTime,
            val location: Location?,
            val owner: String,
            val status: EventStatusEnum
    )

    data class Location(
            val id: UUID,
            var name: String,
            var address: String
    )
}
