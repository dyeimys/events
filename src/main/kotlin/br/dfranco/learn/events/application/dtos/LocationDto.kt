package br.dfranco.learn.events.application.dtos

import java.time.LocalDateTime
import java.util.*

data class LocationDto (
        var id: UUID? = null,
        var name: String,
        var address: String,
        var creationDate: LocalDateTime? = null,
        var updateDate: LocalDateTime? = null
        ){
}