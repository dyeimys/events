package br.dfranco.learn.events.infrastructure.persistence

import br.dfranco.learn.events.domain.entities.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LocationRepository : JpaRepository<LocationEntity, UUID>