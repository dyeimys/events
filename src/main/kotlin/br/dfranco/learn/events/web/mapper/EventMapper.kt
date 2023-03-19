package br.dfranco.learn.events.web.mapper

import br.dfranco.learn.events.dtos.EventDto
import br.dfranco.learn.events.web.request.EventRequest
import br.dfranco.learn.events.web.response.EventResponse
import org.mapstruct.Mapper

@Mapper
interface EventMapper {

    fun toDto(eventRequest: EventRequest): EventDto

    fun toResponse(eventDto: EventDto): EventResponse
}