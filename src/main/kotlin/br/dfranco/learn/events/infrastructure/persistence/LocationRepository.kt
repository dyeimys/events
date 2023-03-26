package br.dfranco.learn.events.infrastructure.persistence

import br.dfranco.learn.events.domain.entities.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LocationRepository : JpaRepository<LocationEntity, UUID> {
    fun existsByNameAndAddress(name: String, address: String): Boolean
}

