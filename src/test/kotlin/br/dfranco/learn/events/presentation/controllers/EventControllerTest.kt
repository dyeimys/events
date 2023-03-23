//package br.dfranco.learn.events.presentation.controllers
//
//import br.dfranco.learn.events.domain.entities.LocationEntity
//import br.dfranco.learn.events.domain.enuns.EventStatusEnum
//import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
//import br.dfranco.learn.events.presentation.requests.EventRequest
//import br.dfranco.learn.events.presentation.responses.EventResponse
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.http.HttpStatusCode
//import org.springframework.http.ResponseEntity
//import java.time.LocalDateTime
//import java.util.*
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//internal class EventControllerTest {
//
//    @Autowired
//    lateinit var restTestTemplate: TestRestTemplate
//
//    @Autowired
//    lateinit var locationRepository: LocationRepository
//
//    @Test
//    fun `perform creation of event without location`() {
//
//        // given
//        val name = "My first event "
//        val date = LocalDateTime.now()
//        val owner = "Owner"
//        val status = EventStatusEnum.UNPUBLISHED
//
//        val eventRequest = buildEventRequest(name, date, null, owner)
//
//        // when
//        val postForEntity: ResponseEntity<EventResponse> = restTestTemplate.postForEntity(
//                "/events",
//                eventRequest,
//                EventResponse::class.java
//        )
//
//        // then
//        val statusCode = postForEntity.statusCode
//        val eventResponse = postForEntity.body!!
//
//        assertEquals(HttpStatusCode.valueOf(200), statusCode)
//
//        assertNotNull(eventResponse.id, "event id expected not null")
//        assertNull(eventResponse.locationId)
//        assertEquals(name, eventResponse.name)
//        assertEquals(owner, eventResponse.owner)
//        assertEquals(date, eventResponse.date)
//        assertEquals(status, eventResponse.status)
//
//    }
//
//    @Test
//    fun `perform creation of event with location`() {
//
//        // given
//        val name = "My first event "
//        val date = LocalDateTime.now()
//        val owner = "Owner"
//        val status = EventStatusEnum.UNPUBLISHED
//        val locationName = "Name of event"
//        val locationAddress = "Address of event"
//
//        val (locationId) = locationRepository.save(LocationEntity(name= locationName, address = locationAddress))
//        val eventRequest = buildEventRequest(name, date, locationId, owner)
//
//        // when
//        val postForEntity: ResponseEntity<EventResponse> = restTestTemplate.postForEntity(
//                "/events",
//                eventRequest,
//                EventResponse::class.java
//        )
//
//        // then
//        val statusCode = postForEntity.statusCode
//        val eventResponse = postForEntity.body!!
//
//        assertEquals(HttpStatusCode.valueOf(200), statusCode)
//
//        assertNotNull(eventResponse.id, "event id expected not null")
//        assertNotNull(eventResponse.locationId)
//        assertEquals(name, eventResponse.name)
//        assertEquals(owner, eventResponse.owner)
//        assertEquals(date, eventResponse.date)
//        assertEquals(status, eventResponse.status)
//
//    }
//
//    private fun buildEventRequest(name: String,
//                                  date: LocalDateTime,
//                                  locationId: UUID?,
//                                  owner: String): EventRequest {
//        return EventRequest(name, date, locationId, owner)
//    }
//
//
//}