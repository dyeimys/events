package br.dfranco.learn.events.repositories

import br.dfranco.learn.events.entities.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LocationRepository : JpaRepository<LocationEntity, UUID>