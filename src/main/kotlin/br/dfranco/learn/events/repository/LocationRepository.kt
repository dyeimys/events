package br.dfranco.learn.events.repository

import br.dfranco.learn.events.entity.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LocationRepository : JpaRepository<LocationEntity, UUID>