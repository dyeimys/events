package br.dfranco.learn.events.infrastructure.persistence

import br.dfranco.learn.events.domain.entities.EventEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EventRepository : JpaRepository<EventEntity, UUID> {

}