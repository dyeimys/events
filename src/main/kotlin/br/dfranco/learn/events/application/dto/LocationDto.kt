package br.dfranco.learn.events.application.dto

import java.time.LocalDateTime
import java.util.UUID

data class LocationDto (
        var id: UUID? = null,
        var name: String,
        var address: String,
        var creationDate: LocalDateTime? = null,
        var updateDate: LocalDateTime? = null
        ){
}