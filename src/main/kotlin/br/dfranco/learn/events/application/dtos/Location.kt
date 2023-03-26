package br.dfranco.learn.events.application.dtos

import java.time.LocalDateTime

class Location {
    data class CreateLocationInput(
            var name: String,
            var address: String
    )

    data class CreateLocationOutput(
            var name: String,
            var address: String,
            var creationDate: LocalDateTime? = null,
    )
}