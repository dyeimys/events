package br.dfranco.learn.events.application.usecases

import br.dfranco.learn.events.application.dtos.Event
import br.dfranco.learn.events.application.mappers.EventEntityMapper
import br.dfranco.learn.events.domain.entities.EventEntity
import br.dfranco.learn.events.domain.entities.LocationEntity
import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import br.dfranco.learn.events.exceptions.LocationNotFoundException
import br.dfranco.learn.events.infrastructure.persistence.EventRepository
import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID


@SpringBootTest
internal class CreateEventUseCaseTest {


    @InjectMocks
    lateinit var createEventUseCase: CreateEventUseCase

    @Mock
    lateinit var eventRepository: EventRepository

    @Mock
    lateinit var locationRepository: LocationRepository

    @Mock
    lateinit var eventMapper: EventEntityMapper


    @Test
    fun `should salve one event whitout location`() {
        // given
        val eventName = "My event"
        val eventOwner = "Me"
        val eventDate = LocalDateTime.now()
        val eventId = UUID.randomUUID()
        val status = EventStatusEnum.UNPUBLISHED

        val eventInput = Event.CreateEventInput(name = eventName, date = eventDate, owner = eventOwner, locationId = null)
        val eventEntity = EventEntity(name = eventName, date = eventDate, owner = eventOwner, location = null, status = status)
        val eventOutput = Event.CreateEventOutput(id = eventId, name = eventName, date = eventDate, owner = eventOwner, location = null, status = status)

        `when`(eventMapper.toEntity(eventInput)).thenReturn(eventEntity)
        `when`(eventRepository.save(eventEntity)).thenReturn(eventEntity)
        `when`(eventMapper.toOutput(eventEntity)).thenReturn(eventOutput)

        // when
        val execute = createEventUseCase.execute(eventInput)


        // then
        assertThat(execute.location).isNull()
        verify(locationRepository, never()).findById(any(UUID::class.java))
        verify(eventRepository).save(eventEntity)
        verify(eventMapper).toOutput(eventEntity)
        verify(eventMapper).toEntity(eventInput)


    }

    @Test
    fun `should salve one event with locationId valid`() {
        // given
        val eventName = "My event"
        val eventDate = LocalDateTime.now()
        val eventOwner = "Me"
        val locationId = UUID.randomUUID()
        val eventId = UUID.randomUUID()
        val eventStatus = EventStatusEnum.UNPUBLISHED
        val locationName = "My Location"
        val locationAddress = "My Address"

        val locationMock = Event.Location(id = locationId, name = locationName, address = locationAddress)
        val locationEntityMock = LocationEntity(id = locationId, name = locationName, address = locationAddress)
        val inputMock = Event.CreateEventInput(name = eventName, date = eventDate, owner = eventOwner, locationId = locationId)
        val eventEntityMock = EventEntity(name = eventName, date = eventDate, owner = eventOwner, location = locationEntityMock,  status = eventStatus)
        val outputMock = Event.CreateEventOutput(id = eventId, name = eventName, date = eventDate, owner = eventOwner, location = locationMock, status = eventStatus)

        `when`(locationRepository.findById(locationId)).thenReturn(Optional.of(locationEntityMock))

        `when`(eventMapper.toEntity(inputMock)).thenReturn(eventEntityMock)
        `when`(eventMapper.toOutput(eventEntityMock)).thenReturn(outputMock)
        `when`(eventRepository.save(eventEntityMock)).thenReturn(eventEntityMock)

        // when
        val output = createEventUseCase.execute(inputMock)

        // then
        assertThat(output.location).isNotNull

        verify(locationRepository).findById(any(UUID::class.java))
        verify(eventRepository).save(eventEntityMock)
        verify(eventMapper).toOutput(eventEntityMock)
        verify(eventMapper).toEntity(inputMock)
    }

    @Test
    fun `should salve one event with locationId invalid`() {
        // given
        val eventName = "My event"
        val eventDate = LocalDateTime.now()
        val eventOwner = "Me"
        val locationId = UUID.randomUUID()


        val eventInput = Event.CreateEventInput(name = eventName, date = eventDate, owner = eventOwner, locationId = locationId)

        // when
        assertThrows<LocationNotFoundException> { createEventUseCase.execute(eventInput) }

        // then

        verify(locationRepository).findById(locationId)
        verify(eventRepository, never()).save(any(EventEntity::class.java))
        verify(eventMapper, never()).toOutput(any(EventEntity::class.java))
        verify(eventMapper, never()).toEntity(any(Event.CreateEventInput::class.java))
    }

    @Test
    fun `should salve one event with location empty`() {
        // given
        val eventName = "My event"
        val eventDate = LocalDateTime.now()
        val eventOwner = "Me"
        val locationId = UUID.randomUUID()


        val eventDto = Event.CreateEventInput(name = eventName, date = eventDate, owner = eventOwner, locationId = locationId)

        `when`(locationRepository.findById(locationId)).thenReturn(Optional.empty())


        // when
        assertThrows<LocationNotFoundException> { createEventUseCase.execute(eventDto) }

        // then
        verify(locationRepository).findById(locationId)
        verify(eventRepository, never()).save(any(EventEntity::class.java))
        verify(eventMapper, never()).toOutput(any(EventEntity::class.java))
        verify(eventMapper, never()).toEntity(any(Event.CreateEventInput::class.java))
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

}