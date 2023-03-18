package br.dfranco.learn.events.repositories

import br.dfranco.learn.events.entities.EventEntity
import br.dfranco.learn.events.enuns.EventStatusEnum
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface EventRepository : JpaRepository<EventEntity, UUID> {

    @Modifying(flushAutomatically = true)
    @Query(value = "update EventEntity e set e.status = :status where e.id = :id")
    fun updateStatusById(id: UUID, status: EventStatusEnum)
}