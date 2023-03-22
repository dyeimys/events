package br.dfranco.learn.events.application.usecases

import br.dfranco.learn.events.application.dtos.EventDto
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
    fun `should salve one event`() {
        // given


        val eventName = "My event"
        val eventDate = LocalDateTime.now()
        val eventOwner = "Me"

        val eventEntity = EventEntity(name = eventName, date = eventDate, owner = eventOwner, location = null)
        val eventDto = EventDto(name = eventName, date = eventDate, owner = eventOwner, locationId = null)

        `when`(eventMapper.toEntity(eventDto)).thenReturn(eventEntity)
        `when`(eventMapper.toDto(eventEntity)).thenReturn(eventDto)
        `when`(eventRepository.save(eventEntity)).thenReturn(eventEntity)

        // when
        val execute = createEventUseCase.execute(eventDto)


        // then
        assertThat(execute.locationId).isNull()
        assertThat(execute.status).isEqualTo(EventStatusEnum.UNPUBLISHED)


        verify(locationRepository, never()).existsById(any(UUID::class.java))
        verify(locationRepository, never()).findById(any(UUID::class.java))
        verify(eventRepository).save(eventEntity)
        verify(eventMapper).toDto(eventEntity)
        verify(eventMapper).toEntity(eventDto)


    }

    @Test
    fun `should salve one event with locationId valid`() {
        // given
        val eventName = "My event"
        val eventDate = LocalDateTime.now()
        val eventOwner = "Me"
        val locationId = UUID.randomUUID()


        val locationEntity = LocationEntity(id = locationId, name = "My Location", address = "My Address")
        val eventDto = EventDto(name = eventName, date = eventDate, owner = eventOwner, locationId = locationId)
        val eventEntity = EventEntity(name = eventName, date = eventDate, owner = eventOwner, location = locationEntity)

        `when`(locationRepository.existsById(locationId)).thenReturn(true)
        `when`(locationRepository.findById(locationId)).thenReturn(Optional.of(locationEntity))

        `when`(eventMapper.toEntity(eventDto)).thenReturn(eventEntity)
        `when`(eventMapper.toDto(eventEntity)).thenReturn(eventDto)
        `when`(eventRepository.save(eventEntity)).thenReturn(eventEntity)

        // when
        val execute = createEventUseCase.execute(eventDto)

        // then
        assertThat(execute.locationId).isNotNull
        assertThat(execute.status).isEqualTo(EventStatusEnum.UNPUBLISHED)

        verify(locationRepository).existsById(any(UUID::class.java))
        verify(locationRepository).findById(any(UUID::class.java))
        verify(eventRepository).save(eventEntity)
        verify(eventMapper).toDto(eventEntity)
        verify(eventMapper).toEntity(eventDto)


    }

    @Test
    fun `should salve one event with locationId invalid`() {
        // given
        val eventName = "My event"
        val eventDate = LocalDateTime.now()
        val eventOwner = "Me"
        val locationId = UUID.randomUUID()


        val eventDto = EventDto(name = eventName, date = eventDate, owner = eventOwner, locationId = locationId)

        `when`(locationRepository.existsById(locationId)).thenReturn(false)


        // when
        assertThrows<LocationNotFoundException> { createEventUseCase.execute(eventDto) }

        // then

        verify(locationRepository).existsById(locationId)
        verify(locationRepository, never()).findById(any(UUID::class.java))
        verify(eventRepository, never()).save(any(EventEntity::class.java))
        verify(eventMapper, never()).toDto(any(EventEntity::class.java))
        verify(eventMapper, never()).toEntity(any(EventDto::class.java))
    }

    @Test
    fun `should salve one event with location empty`() {
        // given
        val eventName = "My event"
        val eventDate = LocalDateTime.now()
        val eventOwner = "Me"
        val locationId = UUID.randomUUID()


        val eventDto = EventDto(name = eventName, date = eventDate, owner = eventOwner, locationId = locationId)

        `when`(locationRepository.existsById(locationId)).thenReturn(true)
        `when`(locationRepository.findById(locationId)).thenReturn(Optional.empty())


        // when
        assertThrows<LocationNotFoundException> { createEventUseCase.execute(eventDto) }

        // then

        verify(locationRepository).existsById(locationId)
        verify(locationRepository).findById(locationId)
        verify(eventRepository, never()).save(any(EventEntity::class.java))
        verify(eventMapper, never()).toDto(any(EventEntity::class.java))
        verify(eventMapper, never()).toEntity(any(EventDto::class.java))


    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

}