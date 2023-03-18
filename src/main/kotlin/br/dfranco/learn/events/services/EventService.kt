package br.dfranco.learn.events.services

import br.dfranco.learn.events.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.NotFoundException
import br.dfranco.learn.events.repositories.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class EventService(
        @Autowired
        var eventRepository: EventRepository
) {

    @Transactional
    fun unpublishingEvent(id: UUID) =
            if (eventRepository.existsById(id))
                eventRepository.updateStatusById(id, EventStatusEnum.UNPUBLISHED)
            else throw NotFoundException()

    @Transactional
    fun publishingEvent(id: UUID) =
            if (eventRepository.existsById(id))
                eventRepository.updateStatusById(id, EventStatusEnum.PUBLISHED)
            else throw NotFoundException()
}