package br.dfranco.learn.events.application.services

import br.dfranco.learn.events.application.dtos.EventDto
import br.dfranco.learn.events.application.dtos.LocationDto
import br.dfranco.learn.events.domain.entities.LocationEntity
import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.NotFoundException
import br.dfranco.learn.events.application.mappers.EventMapper
import br.dfranco.learn.events.application.mappers.LocationDtoMapper
import br.dfranco.learn.events.infrastructure.persistence.EventRepository
import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class EventService(
        @Autowired private var eventRepository: EventRepository,
        @Autowired private var eventMapper: EventMapper,
        @Autowired private var locationRepository: LocationRepository,
        @Autowired private var locationMapper: LocationDtoMapper,
) {

    @Transactional
    fun createEvent(eventDto: EventDto): EventDto {
        return eventDto
                .let(eventMapper::dtoToEntity)
                .apply {
                    location = retrieveLocation(eventDto.locationId)
                    status = EventStatusEnum.UNPUBLISHED
                }
                .let(eventRepository::save)
                .let(eventMapper::entityToDto)
    }

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

    private fun retrieveLocation(locationId: UUID?): LocationEntity? {
        return if (locationId != null) {
            locationRepository.findById(locationId)
                    .orElseThrow { NotFoundException("Location $locationId not found") }
        } else {
            null
        }
    }
}
