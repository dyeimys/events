package br.dfranco.learn.events.application.services

import br.dfranco.learn.events.application.dtos.EventDto
import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.LocationNotFoundException
import br.dfranco.learn.events.application.mappers.EventEntityMapper
import br.dfranco.learn.events.infrastructure.persistence.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class EventService(
        @Autowired private var eventRepository: EventRepository,
        @Autowired private var eventMapper: EventEntityMapper
) {

    @Transactional
    fun createEvent(eventDto: EventDto): EventDto {
        return eventDto
                .let(eventMapper::toEntity)
                .apply {
//                    location = retrieveLocation(eventDto.locationId)
                    status = EventStatusEnum.UNPUBLISHED
                }
                .let(eventRepository::save)
                .let(eventMapper::toDto)
    }

//    @Transactional
//    fun updateLocation(eventId: UUID, locationDto: LocationDto): EventDto {
//        return eventId.let(eventRepository::findById)
//                .orElseThrow { LocationNotFoundException("Event $eventId not found") }
//                .apply { location = retrieveOrCreateLocation(locationMapper.dtoToEntity(locationDto)) }
//                .let(eventRepository::save)
//                .let(eventMapper::entityToDto)
//    }

    @Transactional
    fun unpublishingEvent(id: UUID) =
            if (eventRepository.existsById(id)) eventRepository.updateStatusById(id, EventStatusEnum.UNPUBLISHED)
            else throw LocationNotFoundException()

    @Transactional
    fun publishingEvent(id: UUID) =
            if (eventRepository.existsById(id)) eventRepository.updateStatusById(id, EventStatusEnum.PUBLISHED)
            else throw LocationNotFoundException()

//    private fun retrieveOrCreateLocation(location: LocationEntity): LocationEntity {
//        return if (location.id != null) locationRepository.findById(location.id!!).orElseThrow { LocationNotFoundException("Location ${location.id} not found") }
//        else locationRepository.save(location)
//    }
//
//    private fun retrieveLocation(locationId: UUID?): LocationEntity? {
//        return if (locationId != null) {
//            locationRepository.findById(locationId)
//                    .orElseThrow { LocationNotFoundException("Location $locationId not found") }
//        } else {
//            null
//        }
//    }
}
