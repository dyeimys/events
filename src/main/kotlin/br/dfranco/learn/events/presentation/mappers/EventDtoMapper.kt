//package br.dfranco.learn.events.presentation.mappers
//
//import br.dfranco.learn.events.application.dtos.EventDto
//import br.dfranco.learn.events.presentation.requests.EventRequest
//import br.dfranco.learn.events.presentation.responses.EventResponse
//import org.mapstruct.Mapper
//import org.mapstruct.Mapping
//
//@Mapper
//interface EventDtoMapper {
//
//    @Mapping(target = "id", ignore = true)
//    fun toDto(eventRequest: EventRequest): EventDto
//
//    fun toResponse(eventDto: EventDto): EventResponse
//}