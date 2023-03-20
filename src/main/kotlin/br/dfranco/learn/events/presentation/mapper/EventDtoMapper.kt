package br.dfranco.learn.events.presentation.mapper

import br.dfranco.learn.events.application.dto.EventDto
import br.dfranco.learn.events.presentation.request.EventRequest
import br.dfranco.learn.events.presentation.response.EventResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface EventDtoMapper {

    @Mapping(target = "id", ignore = true)
    fun toDto(eventRequest: EventRequest): EventDto

    fun toResponse(eventDto: EventDto): EventResponse
}