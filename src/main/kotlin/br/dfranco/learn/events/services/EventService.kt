package br.dfranco.learn.events.services

import br.dfranco.learn.events.dtos.EventDto
import br.dfranco.learn.events.dtos.LocationDto
import br.dfranco.learn.events.entities.EventEntity
import br.dfranco.learn.events.entities.LocationEntity
import br.dfranco.learn.events.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.NotFoundException
import br.dfranco.learn.events.mappers.EventEntityMapper
import br.dfranco.learn.events.mappers.LocationEntityMapper
import br.dfranco.learn.events.repositories.EventRepository
import br.dfranco.learn.events.repositories.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class EventService(
        @Autowired private var eventRepository: EventRepository,
        @Autowired private var eventMapper: EventEntityMapper,
        @Autowired private var locationRepository: LocationRepository,
        @Autowired private var locationMapper: LocationEntityMapper,
) {

    @Transactional
    fun createEvent(eventDto: EventDto): EventDto = eventDto
            .let(eventMapper::dtoToEntity)
            .apply { location = retrieveOrCreateLocation(this) }
            .let(eventRepository::save)
            .let(eventMapper::entityToDto)

    @Transactional
    fun updateLocation(eventId: UUID, locationDto: LocationDto): EventDto {
        return eventId.let(eventRepository::findById)
                .orElseThrow { NotFoundException("Event $eventId not found") }
                .apply { location = retrieveOrCreateLocation(locationMapper.dtoToEntity(locationDto)) }
                .let(eventRepository::save)
                .let(eventMapper::entityToDto)
    }

    @Transactional
    fun unpublishingEvent(id: UUID) =
            if (eventRepository.existsById(id)) eventRepository.updateStatusById(id, EventStatusEnum.UNPUBLISHED)
            else throw NotFoundException()

    @Transactional
    fun publishingEvent(id: UUID) =
            if (eventRepository.existsById(id)) eventRepository.updateStatusById(id, EventStatusEnum.PUBLISHED)
            else throw NotFoundException()

    private fun retrieveOrCreateLocation(location: LocationEntity): LocationEntity {
        return if (location.id != null) locationRepository.findById(location.id!!).orElseThrow { NotFoundException("Location ${location.id} not found") }
        else locationRepository.save(location)
    }

    private fun retrieveOrCreateLocation(eventEntity: EventEntity): LocationEntity = retrieveOrCreateLocation(eventEntity.location)
}
