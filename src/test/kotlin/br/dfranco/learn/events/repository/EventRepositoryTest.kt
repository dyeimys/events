package br.dfranco.learn.events.repository

import br.dfranco.learn.events.entity.EventEntity
import br.dfranco.learn.events.entity.LocationEntity
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime


@SpringBootTest
class EventRepositoryTest {

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
        val eventName = "Draw your body"
        val eventDate = LocalDateTime.now()
        val eventOwner = "James Wire"

        val eventLocation = locationRepository.saveAndFlush(LocationEntity(name = "Dream Hall", address = "Dreams Street"))
        val eventEntity = EventEntity(name = eventName, location = eventLocation, date = eventDate, owner = eventOwner)

        // when
        val eventSaved = eventRepository.save(eventEntity)

        // then
        assertNotNull(eventSaved.id, "id not null")
        assertNotNull(eventSaved.creationDate, "creationDate not null")
        assertNotNull(eventSaved.updateDate, "updateDate not null")
        assertEquals(eventName, eventSaved.name)
        assertEquals(eventLocation, eventSaved.location)
        assertEquals(eventDate, eventSaved.date)
        assertEquals(eventOwner, eventSaved.owner)
    }

    @Test
    fun `should salve three events`() {
        // given
        val eventName = "Draw your body"
        val eventDate = LocalDateTime.now()
        val eventOwner = "James Wire"

        val eventLocationOne = locationRepository.saveAndFlush(LocationEntity(name = "Dream Hall", address = "Dreams Street"))
        val eventEntityOne = EventEntity(name = eventName, location = eventLocationOne, date = eventDate, owner = eventOwner)

        val eventLocationTwo = locationRepository.saveAndFlush(LocationEntity(name = "Dream Hall", address = "Dreams Street"))
        val eventEntityTwo = EventEntity(name = eventName, location = eventLocationTwo, date = eventDate, owner = eventOwner)

        val eventLocationThree = locationRepository.saveAndFlush(LocationEntity(name = "Dream Hall", address = "Dreams Street"))
        val eventEntityThree = EventEntity(name = eventName, location = eventLocationThree, date = eventDate, owner = eventOwner)

        // when
        eventRepository.save(eventEntityOne)
        eventRepository.save(eventEntityTwo)
        eventRepository.save(eventEntityThree)

        // then
        val allEvents = eventRepository.findAll();
        assertEquals(3, allEvents.size)
    }
}