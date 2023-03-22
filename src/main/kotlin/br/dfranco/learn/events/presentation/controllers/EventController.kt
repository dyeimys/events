package br.dfranco.learn.events.presentation.controllers

import br.dfranco.learn.events.application.usecases.CreateEventUseCase
import br.dfranco.learn.events.presentation.mappers.EventDtoMapper
import br.dfranco.learn.events.presentation.requests.EventRequest
import br.dfranco.learn.events.presentation.responses.EventResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventController(
        @Autowired private var createEventUseCase: CreateEventUseCase,
        @Autowired private var eventMapper: EventDtoMapper
) {

    @PostMapping
    fun createEvent(@RequestBody event: EventRequest): EventResponse = event.let(eventMapper::toDto)
            .let(createEventUseCase::execute)
            .let { eventMapper.toResponse(it) }
}