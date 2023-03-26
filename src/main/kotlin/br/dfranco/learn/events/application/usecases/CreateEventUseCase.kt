package br.dfranco.learn.events.application.usecases

import br.dfranco.learn.events.application.dtos.Event
import br.dfranco.learn.events.application.mappers.EventEntityMapper
import br.dfranco.learn.events.domain.entities.LocationEntity
import br.dfranco.learn.events.exceptions.LocationNotFoundException
import br.dfranco.learn.events.infrastructure.persistence.EventRepository
import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CreateEventUseCase(
        @Autowired
        private val eventRepository: EventRepository,
        private val locationRepository: LocationRepository,
        private val eventMapper: EventEntityMapper,
) {

    fun execute(eventInput: Event.CreateEventInput): Event.CreateEventOutput {
        val locationId = eventInput.locationId

        var locationEntity: LocationEntity? = null
        if (locationId != null) {
            locationEntity = locationRepository.findById(locationId)
                    .orElseThrow { LocationNotFoundException("Location $locationId not found") }
        }

        return eventInput.let(eventMapper::toEntity)
                .apply { location = locationEntity }
                .let(eventRepository::save)
                .let { eventMapper.toOutput(it) }
    }
}