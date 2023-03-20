package br.dfranco.learn.events.presentation.controllers

import br.dfranco.learn.events.presentation.requests.EventRequest
import br.dfranco.learn.events.presentation.responses.EventResponse
import br.dfranco.learn.events.presentation.mappers.EventDtoMapper
import br.dfranco.learn.events.application.services.EventService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventController(
        @Autowired private var eventService: EventService,
        @Autowired private var eventMapper: EventDtoMapper
) {


    @Operation(
            description = "Create new event\n Create new event with location. This event is created with status UNPUBLISHED"
    )
    @PostMapping
    fun createEvent(@RequestBody event: EventRequest): EventResponse = event.let(eventMapper::toDto)
            .let(eventService::createEvent)
            .let(eventMapper::toResponse)

//    @GetMapping("/{id}")
//    fun findEvent(@PathVariable id: UUID): EventResponse = EventResponse()
//
//    @PutMapping("/{id}/publish")
//    fun publishEvent(@PathVariable id: UUID): EventResponse = EventResponse()
//
//    @PutMapping("/{id}/unpublish")
//    fun unpublishEvent(@PathVariable id: UUID): EventResponse = EventResponse()
//
//    @GetMapping("/{id}/location")
//    fun findEventLocation(@PathVariable id: UUID): LocationResponse = LocationResponse()
//
//    @PutMapping("/{id}/location")
//    fun updateEventLocation(@PathVariable id: UUID, @RequestBody locationRequest: LocationRequest): EventResponse = EventResponse()
}