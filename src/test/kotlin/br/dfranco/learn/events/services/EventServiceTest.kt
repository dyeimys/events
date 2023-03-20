//package br.dfranco.learn.events.services
//
//import br.dfranco.learn.events.application.dto.EventDto
//import br.dfranco.learn.events.application.dto.LocationDto
//import br.dfranco.learn.events.domain.entities.EventEntity
//import br.dfranco.learn.events.domain.entities.LocationEntity
//import br.dfranco.learn.events.domain.enuns.EventStatusEnum
//import br.dfranco.learn.events.exceptions.NotFoundException
//import br.dfranco.learn.events.app.mappers.EventEntityMapper
//import br.dfranco.learn.events.app.mappers.LocationEntityMapper
//import br.dfranco.learn.events.infrastructure.persistence.EventRepository
//import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
//import br.dfranco.learn.events.domain.services.EventService
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito.any
//import org.mockito.Mockito.never
//import org.mockito.Mockito.times
//import org.mockito.Mockito.verify
//import org.mockito.Mockito.`when`
//import org.springframework.boot.test.context.SpringBootTest
//import java.time.LocalDateTime
//import java.util.Optional
//import java.util.UUID
//
//
//@SpringBootTest
//internal class EventServiceTest {
//
//    @Mock
//    lateinit var eventRepository: EventRepository
//
//    @Mock
//    lateinit var eventMapper: EventEntityMapper
//
//    @Mock
//    lateinit var locationRepository: LocationRepository
//
//    @Mock
//    lateinit var locationMapper: LocationEntityMapper
//
//    @InjectMocks
//    lateinit var eventService: EventService
//
//    @Test
//    fun `should set status publish in  event`() {
//        // given
//        val eventId = UUID.randomUUID()
//        `when`(eventRepository.existsById(eventId)).thenReturn(true)
//
//        // when
//        eventService.publishingEvent(eventId)
//
//        // then
//        verify(eventRepository, times(1)).updateStatusById(eventId, EventStatusEnum.PUBLISHED)
//    }
//
//    @Test
//    fun `should not set status publish in event because there is no event`() {
//        // given
//        val eventId: UUID = UUID.randomUUID()
//        `when`(eventRepository.existsById(eventId)).thenReturn(false)
//
//        // when/then
//        assertThrows<NotFoundException> { eventService.publishingEvent(eventId) }
//        verify(eventRepository, never()).updateStatusById(eventId, EventStatusEnum.PUBLISHED)
//
//    }
//
//
//    @Test
//    fun `should set status unpublish in  event`() {
//        val eventId: UUID = UUID.randomUUID()
//        `when`(eventRepository.existsById(eventId)).thenReturn(true)
//
//        // when
//        eventService.unpublishingEvent(eventId)
//
//        // then
//        verify(eventRepository, times(1)).updateStatusById(eventId, EventStatusEnum.UNPUBLISHED)
//    }
//
//    @Test
//    fun `should not set status unpublish in event because there is no event`() {
//        // given
//        val eventId: UUID = UUID.randomUUID()
//        `when`(eventRepository.existsById(eventId)).thenReturn(false)
//
//        // when/then
//        assertThrows<NotFoundException> { eventService.unpublishingEvent(eventId) }
//
//        verify(eventRepository, never()).updateStatusById(eventId, EventStatusEnum.UNPUBLISHED)
//    }
//
//    @Test
//    fun `should create event and location`() {
//        // given
//        val eventName = "My great event"
//        val eventOwner = "Mr James"
//        val eventDate = LocalDateTime.now()
//        val eventStatus = EventStatusEnum.UNPUBLISHED
//        val locationAddress = "Dream Street"
//        val locationName = "My Location"
//
//        val eventDto = buildEventDto(eventName, eventOwner, eventDate, eventStatus, null)
//        val locationEntity = LocationEntity(null, locationName, locationAddress)
//        val eventEntity = EventEntity(null, eventName, eventDate, locationEntity, eventOwner, eventStatus)
//
//        `when`(eventMapper.dtoToEntity(eventDto)).thenReturn(eventEntity)
//
//        // when
//        eventService.createEvent(eventDto)
//
//        // then
//        verify(eventMapper).dtoToEntity(eventDto)
//        verify(locationRepository, never()).findById(any())
//        verify(eventRepository).save(eventEntity)
//    }
//
//    @Test
//    fun `should create event with location exists`() {
//        // given
//        val eventName = "My great event II"
//        val eventOwner = "Mr James Din"
//        val eventDate = LocalDateTime.now()
//        val eventStatus = EventStatusEnum.UNPUBLISHED
//        val locationId = UUID.randomUUID()
//        val locationAddress = "Dream Street Brown"
//        val locationName = "My Location special"
//
//        val eventDto = buildEventDto(eventName, eventOwner, eventDate, eventStatus, locationId)
//        val locationEntity = LocationEntity(locationId, locationName, locationAddress)
//        val eventEntity = EventEntity(null, eventName, eventDate, locationEntity, eventOwner, eventStatus)
//
//        `when`(eventMapper.dtoToEntity(eventDto)).thenReturn(eventEntity)
//        `when`(locationRepository.findById(locationId)).thenReturn(Optional.of(locationEntity))
//
//        // when
//        eventService.createEvent(eventDto)
//
//        // then
//        verify(eventMapper).dtoToEntity(eventDto)
//        verify(locationRepository).findById(any())
//        verify(locationRepository, never()).save(locationEntity)
//        verify(eventRepository).save(eventEntity)
//    }
//
//
//    @Test
//    fun `should not create event with location not exists`() {
//        // given
//        val eventName = "My great event II"
//        val eventOwner = "Mr James Din"
//        val eventDate = LocalDateTime.now()
//        val eventStatus = EventStatusEnum.UNPUBLISHED
//        val locationId = UUID.randomUUID()
//        val locationAddress = "Dream Street Brown"
//        val locationName = "My Location special"
//
//        val eventDto = buildEventDto(eventName, eventOwner, eventDate, eventStatus, locationId)
//        val locationEntity = LocationEntity(locationId, locationName, locationAddress)
//        val eventEntity = EventEntity(null, eventName, eventDate, locationEntity, eventOwner, eventStatus)
//
//        `when`(eventMapper.dtoToEntity(eventDto)).thenReturn(eventEntity)
//        `when`(locationRepository.save(locationEntity)).thenReturn(locationEntity)
//
//        // when
//        assertThrows<NotFoundException> { eventService.createEvent(eventDto) }
//
//        // then
//        verify(eventMapper).dtoToEntity(eventDto)
//        verify(locationRepository).findById(any())
//        verify(locationRepository, never()).save(locationEntity)
//        verify(eventRepository, never()).save(eventEntity)
//    }
//
//    @Test
//    fun `should update event location`() {
//        // given
//        val eventId = UUID.randomUUID()
//        val eventName = "My great event II"
//        val eventOwner = "Mr James Din"
//        val eventDate = LocalDateTime.now()
//        val eventStatus = EventStatusEnum.UNPUBLISHED
//        val locationId = UUID.randomUUID()
//        val locationAddress = "Dream Street Brown"
//        val locationName = "My Location special"
//
//        val locationDto = buildLocationDto(locationId, locationName, locationAddress)
//        val locationEntity = LocationEntity(locationId, locationName, locationAddress)
//        val eventEntity = EventEntity(null, eventName, eventDate, locationEntity, eventOwner, eventStatus)
//
//        `when`(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity))
//        `when`(locationMapper.dtoToEntity(locationDto)).thenReturn(locationEntity)
//        `when`(locationRepository.findById(locationId)).thenReturn(Optional.of(locationEntity))
//        `when`(eventRepository.save(eventEntity)).thenReturn(eventEntity.apply { id = UUID.randomUUID() })
//
//        // when
//        eventService.updateLocation(eventId, locationDto)
//
//        // then
//        verify(eventRepository).findById(eventId)
//        verify(locationMapper).dtoToEntity(locationDto)
//        verify(locationRepository).findById(locationId)
//        verify(locationRepository, never()).save(locationEntity)
//        verify(eventRepository).save(eventEntity)
//        verify(eventMapper).entityToDto(eventEntity)
//    }
//
//    @Test
//    fun `should not update event location without event`() {
//        // given
//        val eventId = UUID.randomUUID()
//        val locationId = UUID.randomUUID()
//        val locationAddress = "Dream Street Brown"
//        val locationName = "My Location special"
//
//        val locationDto = buildLocationDto(locationId, locationName, locationAddress)
//
//        `when`(eventRepository.findById(eventId)).thenReturn(Optional.empty())
//
//        // when
//        assertThrows<NotFoundException> {eventService.updateLocation(eventId, locationDto)}
//
//        // then
//        verify(eventRepository).findById(eventId)
//        verify(locationMapper, never()).dtoToEntity(locationDto)
//        verify(locationRepository, never()).findById(locationId)
//        verify(locationRepository, never()).save(any())
//        verify(eventRepository, never()).save(any())
//    }
//
//    @Test
//    fun `should create and update event location`() {
//        // given
//        val eventId = UUID.randomUUID()
//        val eventName = "My great event II"
//        val eventOwner = "Mr James Din"
//        val eventDate = LocalDateTime.now()
//        val eventStatus = EventStatusEnum.UNPUBLISHED
//        val locationId = UUID.randomUUID()
//        val locationAddress = "Dream Street Brown"
//        val locationName = "My Location special"
//
//        val locationDto = buildLocationDto(null, locationName, locationAddress)
//        val locationEntity = LocationEntity(null, locationName, locationAddress)
//        val eventEntity = EventEntity(eventId, eventName, eventDate, locationEntity, eventOwner, eventStatus)
//
//        `when`(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity))
//        `when`(locationMapper.dtoToEntity(locationDto)).thenReturn(locationEntity)
//        `when`(locationRepository.save(locationEntity)).thenReturn(locationEntity)
//        `when`(eventRepository.save(eventEntity)).thenReturn(eventEntity)
//
//        // when
//        eventService.updateLocation(eventId, locationDto)
//
//        // then
//        verify(eventRepository).findById(eventId)
//        verify(locationMapper).dtoToEntity(locationDto)
//        verify(locationRepository , never()).findById(locationId)
//        verify(locationRepository).save(locationEntity)
//        verify(eventRepository).save(eventEntity)
//        verify(eventMapper).entityToDto(eventEntity)
//    }
//
//    @Test
//    fun `should not update event location with location not exists`() {
//        // given
//        val eventId = UUID.randomUUID()
//        val eventName = "My great event II"
//        val eventOwner = "Mr James Din"
//        val eventDate = LocalDateTime.now()
//        val eventStatus = EventStatusEnum.UNPUBLISHED
//        val locationId = UUID.randomUUID()
//        val locationAddress = "Dream Street Brown"
//        val locationName = "My Location special"
//
//        val locationDto = buildLocationDto(locationId, locationName, locationAddress)
//        val locationEntity = LocationEntity(locationId, locationName, locationAddress)
//        val eventEntity = EventEntity(null, eventName, eventDate, locationEntity, eventOwner, eventStatus)
//
//        `when`(eventRepository.findById(eventId)).thenReturn(Optional.of(eventEntity))
//        `when`(locationMapper.dtoToEntity(locationDto)).thenReturn(locationEntity)
//        `when`(locationRepository.findById(locationId)).thenReturn(Optional.empty())
//
//        // when
//        assertThrows<NotFoundException> {eventService.updateLocation(eventId, locationDto)}
//
//        // then
//        verify(eventRepository).findById(eventId)
//        verify(locationMapper).dtoToEntity(locationDto)
//        verify(locationRepository).findById(locationId)
//        verify(locationRepository, never()).save(locationEntity)
//        verify(eventRepository, never()).save(eventEntity)
//        verify(eventMapper, never()).entityToDto(eventEntity)
//    }
//
//    private fun buildEventDto(
//            name: String,
//            owner: String,
//            date: LocalDateTime,
//            status: EventStatusEnum,
//            locationId: UUID?): EventDto = EventDto(null, name, date, locationId, owner, status)
//
//    private fun buildLocationDto(id: UUID? = null, name: String, address: String) = LocationDto(id, name, address)
//
//}