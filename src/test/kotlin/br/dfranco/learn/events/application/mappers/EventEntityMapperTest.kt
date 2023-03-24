package br.dfranco.learn.events.application.mappers

import br.dfranco.learn.events.application.dtos.Event
import br.dfranco.learn.events.domain.entities.EventEntity
import br.dfranco.learn.events.domain.entities.LocationEntity
import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.UUID

@SpringBootTest
class EventEntityMapperTest {


    @Autowired
    lateinit var eventEntityMapper: EventEntityMapper

    @Test
    fun `should mapping input to entity`(){
        // given
        val eventName = "My event"
        val eventOwner = "Me"
        val eventDate = LocalDateTime.now()

        val eventInput = Event.CreateEventInput(name = eventName, date = eventDate, owner = eventOwner, locationId = null)


        // when
        val entity = eventEntityMapper.toEntity(eventInput)

        // then
        assertThat(entity).isNotNull
        assertThat(entity.id).isNull()
        assertThat(entity.name).isEqualTo(eventName)
        assertThat(entity.status).isEqualTo(EventStatusEnum.UNPUBLISHED)
        assertThat(entity.date).isEqualTo(eventDate)
        assertThat(entity.owner).isEqualTo(eventOwner)
        assertThat(entity.location).isNull()
        assertThat(entity.creationDate).isNull()
        assertThat(entity.updateDate).isNull()
    }

    @Test
    fun `should mapping entity to output without location`(){
        // given
        val eventName = "My event whitout location"
        val eventOwner = "Alone"
        val eventDate = LocalDateTime.now()
        val eventId = UUID.randomUUID()
        val status = EventStatusEnum.UNPUBLISHED

        val eventEntity = EventEntity(id=eventId, name = eventName, date = eventDate, owner = eventOwner, location = null, status = status)

        // when
        val eventOutput = eventEntityMapper.toOutput(eventEntity)

        // then
        assertThat(eventOutput).isNotNull
        assertThat(eventOutput.id).isNotNull
        assertThat(eventOutput.name).isEqualTo(eventName)
        assertThat(eventOutput.status).isEqualTo(EventStatusEnum.UNPUBLISHED)
        assertThat(eventOutput.date).isEqualTo(eventDate)
        assertThat(eventOutput.owner).isEqualTo(eventOwner)
        assertThat(eventOutput.location).isNull()
    }

    @Test
    fun `should mapping entity to output with location`(){
        // given
        val eventId = UUID.randomUUID()
        val status = EventStatusEnum.UNPUBLISHED
        val eventName = "My event"
        val eventDate = LocalDateTime.now()
        val eventOwner = "Me"
        val locationId = UUID.randomUUID()
        val locationName = "My Location"
        val locationAddress = "My Address"

        val location = LocationEntity(id = locationId, name = locationName, address = locationAddress)
        val eventEntity = EventEntity(id=eventId, name = eventName, date = eventDate, owner = eventOwner, location = location, status = status)

        // when
        val eventOutput = eventEntityMapper.toOutput(eventEntity)

        // then
        assertThat(eventOutput).isNotNull
        assertThat(eventOutput.id).isNotNull
        assertThat(eventOutput.name).isEqualTo(eventName)
        assertThat(eventOutput.status).isEqualTo(EventStatusEnum.UNPUBLISHED)
        assertThat(eventOutput.date).isEqualTo(eventDate)
        assertThat(eventOutput.owner).isEqualTo(eventOwner)
        assertThat(eventOutput.location).isNotNull
        assertThat(eventOutput.location?.id).isEqualTo(locationId)
        assertThat(eventOutput.location?.name).isEqualTo(locationName)
        assertThat(eventOutput.location?.address).isEqualTo(locationAddress)
    }


}