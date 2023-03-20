package br.dfranco.learn.events.presentation.responses

import java.time.LocalDateTime
import java.util.*

data class LocationResponse (
        var id: UUID? = null,
        var name: String,
        var address: String,
        var creationDate: LocalDateTime? = null,
        var updateDate: LocalDateTime? = null
        ){
}