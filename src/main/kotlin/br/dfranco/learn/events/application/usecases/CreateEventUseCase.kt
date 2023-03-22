package br.dfranco.learn.events.application.usecases

import br.dfranco.learn.events.application.dtos.EventDto
import br.dfranco.learn.events.application.mappers.EventEntityMapper
import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.LocationNotFoundException
import br.dfranco.learn.events.infrastructure.persistence.EventRepository
import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateEventUseCase(
        @Autowired
        private val eventRepository: EventRepository,
        private val locationRepository: LocationRepository,
        private val eventMapper: EventEntityMapper,
) {

    fun execute(eventDto: EventDto): EventDto {
        val locationId = eventDto.locationId

        //TODO alterar essa gambiarra
        val locationEntity = if (locationId != null) {
            if (locationRepository.existsById(locationId)) {
                locationRepository.findById(locationId)
                        .orElseThrow { LocationNotFoundException("Location $locationId not found") }
            } else
                throw LocationNotFoundException("Location $locationId not found")
        } else {
            null
        }


        return eventDto.let(eventMapper::toEntity)
                .apply {
                    location = locationEntity
                    status = EventStatusEnum.UNPUBLISHED
                }
                .let(eventRepository::save)
                .let { eventMapper.toDto(it) }
    }
}