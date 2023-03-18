package br.dfranco.learn.events.services

import br.dfranco.learn.events.dtos.EventDto
import br.dfranco.learn.events.entities.EventEntity
import br.dfranco.learn.events.entities.LocationEntity
import br.dfranco.learn.events.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.NotFoundException
import br.dfranco.learn.events.mappers.EventMapper
import br.dfranco.learn.events.repositories.EventRepository
import br.dfranco.learn.events.repositories.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class EventService(
        @Autowired var eventRepository: EventRepository,
        @Autowired var eventMapper: EventMapper,
        @Autowired var locationRepository: LocationRepository
) {

    @Transactional
    fun createEvent(eventDto: EventDto): EventDto = eventDto
            .let(eventMapper::dtoToEntity)
            .apply { location = retrieveOrCreateLocation(this) }
            .let(eventRepository::save)
            .let(eventMapper::entityToDto)

    @Transactional
    fun unpublishingEvent(id: UUID) =
            if (eventRepository.existsById(id)) eventRepository.updateStatusById(id, EventStatusEnum.UNPUBLISHED)
            else throw NotFoundException()

    @Transactional
    fun publishingEvent(id: UUID) =
            if (eventRepository.existsById(id)) eventRepository.updateStatusById(id, EventStatusEnum.PUBLISHED)
            else throw NotFoundException()

    private fun retrieveOrCreateLocation(eventEntity: EventEntity): LocationEntity =
            if (eventEntity.location.id != null) locationRepository.findById(eventEntity.location.id!!)
                    .orElseThrow { NotFoundException("Location ${eventEntity.location.id} not found") }
            else locationRepository.save(eventEntity.location)
}
