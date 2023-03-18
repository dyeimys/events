package br.dfranco.learn.events.services

import br.dfranco.learn.events.dtos.EventDto
import br.dfranco.learn.events.dtos.LocationDto
import br.dfranco.learn.events.entities.EventEntity
import br.dfranco.learn.events.entities.LocationEntity
import br.dfranco.learn.events.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.NotFoundException
import br.dfranco.learn.events.mappers.EventMapper
import br.dfranco.learn.events.repositories.EventRepository
import br.dfranco.learn.events.repositories.LocationRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.UUID


@SpringBootTest
internal class EventServiceTest {

    @Mock
    lateinit var eventRepository: EventRepository

    @Mock
    lateinit var eventMapper: EventMapper

    @Mock
    lateinit var locationRepository: LocationRepository


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
        val eventId: UUID = UUID.randomUUID()
        `when`(eventRepository.existsById(eventId)).thenReturn(true)

        // when
        eventService.unpublishingEvent(eventId)

        // then
        verify(eventRepository, times(1)).updateStatusById(eventId, EventStatusEnum.UNPUBLISHED)
    }

    @Test
    fun `should not set status unpublish in event because there is no event`() {
        // given
        val eventId: UUID = UUID.randomUUID()
        `when`(eventRepository.existsById(eventId)).thenReturn(false)

        // when/then
        assertThrows<NotFoundException> { eventService.unpublishingEvent(eventId) }

        verify(eventRepository, never()).updateStatusById(eventId, EventStatusEnum.UNPUBLISHED)
    }

    @Test
    fun `should create event and location`() {
        // given
        val eventName = "My great event"
        val eventOwner = "Mr James"
        val eventDate = LocalDateTime.now()
        val eventStatus = EventStatusEnum.UNPUBLISHED
        val locationAddress = "Dream Street"
        val locationName = "My Location"

        val locationDto = buildLocationDto(null, locationName, locationAddress)
        val eventDto = buildEventDto(null, eventName, eventOwner, eventDate, eventStatus, locationDto)
        val locationEntity = LocationEntity(null, locationName, locationAddress)
        val eventEntity = EventEntity(null, eventName, eventDate, locationEntity, eventOwner, eventStatus)

        `when`(eventMapper.dtoToEntity(eventDto)).thenReturn(eventEntity)
        `when`(locationRepository.save(locationEntity)).thenReturn(locationEntity)

        // when
        eventService.createEvent(eventDto)

        // then
        verify(eventMapper).dtoToEntity(eventDto)
        verify(locationRepository, never()).findById(any())
        verify(locationRepository).save(locationEntity)
        verify(eventRepository).save(eventEntity)
    }

    private fun buildEventDto(id: UUID? = null,
                              name: String,
                              owner: String,
                              date: LocalDateTime,
                              status: EventStatusEnum,
                              location: LocationDto): EventDto = EventDto(id, name, date, location, owner, status)

    private fun buildLocationDto(id: UUID? = null, name: String, address: String) = LocationDto(id, name, address)

}