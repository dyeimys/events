package br.dfranco.learn.events.domain.mapper

import br.dfranco.learn.events.domain.dtos.EventDto
import br.dfranco.learn.events.app.controllers.request.EventRequest
import br.dfranco.learn.events.app.controllers.response.EventResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface EventMapper {

    @Mapping(target = "id", ignore = true)
    fun toDto(eventRequest: EventRequest): EventDto

    fun toResponse(eventDto: EventDto): EventResponse
}