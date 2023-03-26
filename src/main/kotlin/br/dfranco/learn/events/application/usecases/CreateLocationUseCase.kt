package br.dfranco.learn.events.application.usecases

import br.dfranco.learn.events.application.dtos.Location
import br.dfranco.learn.events.application.mappers.LocationEntityMapper
import br.dfranco.learn.events.domain.exceptions.LocationAlreadyExistsException
import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateLocationUseCase(
        @Autowired
        private val locationRepository: LocationRepository,
        private val locationEntityMapper: LocationEntityMapper
) {

    fun execute(locationInput: Location.CreateLocationInput): Location.CreateLocationOutput {

        return verifyIfExistsAndReturn(locationInput)
                .let(locationEntityMapper::toEntity)
                .let(locationRepository::save)
                .let(locationEntityMapper::toLocationOutput)

    }

    private fun verifyIfExistsAndReturn(locationInput: Location.CreateLocationInput): Location.CreateLocationInput = locationInput
            .takeUnless { locationRepository.existsByNameAndAddress(it.name, it.address) }
            ?: throw LocationAlreadyExistsException()
}