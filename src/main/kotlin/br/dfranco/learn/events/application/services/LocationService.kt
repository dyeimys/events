package br.dfranco.learn.events.application.services

import br.dfranco.learn.events.domain.entities.LocationEntity
import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class LocationService(
        @Autowired private var locationRepository: LocationRepository,
) {

    fun findOrCreate(locationEntity: LocationEntity): Optional<LocationEntity> {
        val (locationId) = locationEntity

        return locationId?.let {
            findById(it)
        } ?: let {
            locationRepository.save(locationEntity).let { Optional.of(it) }
        }


    }

    fun findById(id: UUID): Optional<LocationEntity> = locationRepository.findById(id)
}