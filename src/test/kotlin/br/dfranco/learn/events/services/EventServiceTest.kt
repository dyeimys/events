package br.dfranco.learn.events.services

import br.dfranco.learn.events.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.NotFoundException
import br.dfranco.learn.events.mappers.EventMapper
import br.dfranco.learn.events.repositories.EventRepository
import br.dfranco.learn.events.repositories.LocationRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID


@SpringBootTest
internal class EventServiceTest {

    @Mock
    lateinit var eventRepository: EventRepository

    @Mock
    lateinit var  eventMapper: EventMapper

    @Mock
    lateinit var  locationRepository: LocationRepository


    @InjectMocks
    lateinit var eventService: EventService

    @Test
    fun `should set status publish in  event`() {
        // given
        val eventId = UUID.randomUUID()
        `when`(eventRepository.existsById(eventId)).thenReturn(true)

        // when
        eventService.publishingEvent(eventId)

        // then
        verify(eventRepository, times(1)).updateStatusById(eventId, EventStatusEnum.PUBLISHED)
    }

    @Test
    fun `should not set status publish in event because there is no event`() {
        // given
        val eventId: UUID = UUID.randomUUID()
        `when`(eventRepository.existsById(eventId)).thenReturn(false)

        // when/then
        assertThrows<NotFoundException> { eventService.publishingEvent(eventId) }
        verify(eventRepository, never()).updateStatusById(eventId, EventStatusEnum.PUBLISHED)

    }


    @Test
    fun `should set status unpublish in  event`() {
        val eventId: UUID =  UUID.randomUUID()
        `when`(eventRepository.existsById(eventId)).thenReturn(true)

        // when
        eventService.unpublishingEvent(eventId)

        // then
        verify(eventRepository, times(1)).updateStatusById(eventId, EventStatusEnum.UNPUBLISHED)
    }

    @Test
    fun `should not set status unpublish in event because there is no event`() {
        // given
        val eventId: UUID =  UUID.randomUUID()
        `when`(eventRepository.existsById(eventId)).thenReturn(false)

        // when/then
        assertThrows<NotFoundException> { eventService.unpublishingEvent(eventId) }

        verify(eventRepository, never()).updateStatusById(eventId, EventStatusEnum.UNPUBLISHED)
    }

}