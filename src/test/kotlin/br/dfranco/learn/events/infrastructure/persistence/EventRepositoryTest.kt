package br.dfranco.learn.events.infrastructure.persistence

import br.dfranco.learn.events.domain.entities.EventEntity
import br.dfranco.learn.events.domain.entities.LocationEntity
import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime


@SpringBootTest
internal class EventRepositoryTest {

    @Autowired
    lateinit var eventRepository: EventRepository

    @Autowired
    lateinit var locationRepository: LocationRepository

    @AfterEach
    fun before() {
        eventRepository.deleteAll()
        locationRepository.deleteAll()
    }

    @Test
    fun `should salve one event`() {

        // given
        val eventName = "Draw your body Fire"
        val eventDate = LocalDateTime.now()
        val eventOwner = "James Wire Fire"


        val eventEntity = buildEventEntity(eventName, eventDate, eventOwner)

        // when
        val eventSaved = eventRepository.save(eventEntity)

        // then
        assertNotNull(eventSaved.id, "id not null")
        assertNotNull(eventSaved.creationDate, "creationDate not null")
        assertNotNull(eventSaved.location, "location not null")
        assertEquals(eventSaved.status, EventStatusEnum.UNPUBLISHED)
        assertEquals(eventName, eventSaved.name)
        assertEquals(eventDate, eventSaved.date)
        assertEquals(eventOwner, eventSaved.owner)
    }

    @Test
    fun `should salve three events`() {
        // given
        val eventName = "Draw your body"
        val eventDate = LocalDateTime.now()
        val eventOwner = "James Wire"

        val eventEntityOne = buildEventEntity(eventName, eventDate, eventOwner)
        val eventEntityTwo = buildEventEntity(eventName, eventDate, eventOwner)
        val eventEntityThree = buildEventEntity(eventName, eventDate, eventOwner)

        // when
        eventRepository.save(eventEntityOne)
        eventRepository.save(eventEntityTwo)
        eventRepository.save(eventEntityThree)

        // then
        val allEvents = eventRepository.findAll()
        assertEquals(3, allEvents.size)
    }

    private fun buildEventEntity(eventName: String, eventDate: LocalDateTime, eventOwner: String): EventEntity =
            EventEntity(
                    name = eventName,
                    location = locationRepository.saveAndFlush(LocationEntity(name = "Dream Hall", address = "Dreams Street")),
                    date = eventDate,
                    owner = eventOwner)
}