package br.dfranco.learn.events.repository

import br.dfranco.learn.events.entity.EventEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface EventRepository : JpaRepository<EventEntity, UUID>