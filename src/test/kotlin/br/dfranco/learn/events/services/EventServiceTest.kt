package br.dfranco.learn.events.services

import br.dfranco.learn.events.entities.EventEntity
import br.dfranco.learn.events.entities.LocationEntity
import br.dfranco.learn.events.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.NotFoundException
import br.dfranco.learn.events.repositories.EventRepository
import br.dfranco.learn.events.repositories.LocationRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.UUID


@SpringBootTest
internal class EventServiceTest {

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var locationRepository: LocationRepository

    @Autowired
    lateinit var eventService: EventService

    @AfterEach
    fun before() {
        eventRepository.deleteAll()
        locationRepository.deleteAll()
    }

    @Test
    fun `should set status publish in  event`() {
        val eventName = "Draw your body Fire"
        val eventDate = LocalDateTime.now()
        val eventOwner = "James Wire Fire"
        val (eventId) = eventRepository.save(buildEventEntity(eventName, eventDate, eventOwner, EventStatusEnum.UNPUBLISHED))

        // when
        eventService.publishEvent(eventId!!)

        // then
        eventRepository.findById(eventId)
                .map(EventEntity::status)
                .ifPresent { Assertions.assertEquals(EventStatusEnum.PUBLISHED, it) }
    }

    @Test
    fun `should not set status publish in  event`() {
        // given
        val eventId = UUID.randomUUID()

        // when/then
        assertThrows<NotFoundException> { eventService.publishEvent(eventId) }
    }


    @Test
    fun `should set status unpublish in  event`() {
        val eventName = "Draw your body "
        val eventDate = LocalDateTime.now()
        val eventOwner = "James Wire "
        val (eventId) = eventRepository.save(buildEventEntity(eventName, eventDate, eventOwner, EventStatusEnum.PUBLISHED))

        // when
        eventService.unpublishEvent(eventId!!)

        // then
        eventRepository.findById(eventId)
                .map(EventEntity::status)
                .ifPresent { Assertions.assertEquals(EventStatusEnum.UNPUBLISHED, it) }
    }

    @Test
    fun `should not set status unpublish in  event`() {
        // given
        val eventId = UUID.randomUUID()

        // when/then
        assertThrows<NotFoundException> { eventService.unpublishEvent(eventId) }
    }


    private fun buildEventEntity(eventName: String, eventDate: LocalDateTime, eventOwner: String, statusEnum: EventStatusEnum?): EventEntity =
            EventEntity(
                    name = eventName,
                    location = locationRepository.save(LocationEntity(name = "Dream Hall", address = "Dreams Street")),
                    date = eventDate,
                    owner = eventOwner,
                    status = statusEnum!!
            )
}