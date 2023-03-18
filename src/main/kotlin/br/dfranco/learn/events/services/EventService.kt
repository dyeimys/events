package br.dfranco.learn.events.services

import br.dfranco.learn.events.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.NotFoundException
import br.dfranco.learn.events.repositories.EventRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class EventService(var eventRepository: EventRepository) {

    @Transactional
    fun unpublishEvent(id: UUID) =
            if (eventRepository.existsById(id))
                eventRepository.updateStatusById(id, EventStatusEnum.UNPUBLISHED)
            else throw NotFoundException()

    @Transactional
    fun publishEvent(id: UUID) =
            if (eventRepository.existsById(id))
                eventRepository.updateStatusById(id, EventStatusEnum.PUBLISHED)
            else throw NotFoundException()
}