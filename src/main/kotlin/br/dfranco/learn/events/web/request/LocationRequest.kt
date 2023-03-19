package br.dfranco.learn.events.web.request

import java.time.LocalDateTime
import java.util.*

data class LocationRequest(
        var id: UUID? = null,
        var name: String,
        var address: String,
        var creationDate: LocalDateTime? = null,
        var updateDate: LocalDateTime? = null
)