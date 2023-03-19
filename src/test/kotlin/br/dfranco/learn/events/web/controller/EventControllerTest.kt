package br.dfranco.learn.events.web.controller

import br.dfranco.learn.events.enuns.EventStatusEnum
import br.dfranco.learn.events.web.request.EventRequest
import br.dfranco.learn.events.web.request.LocationRequest
import br.dfranco.learn.events.web.response.EventResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class EventControllerTest {

    @Autowired
    lateinit var restTestTemplate: TestRestTemplate

    @Test
    fun `perform creation of event`() {

        // given
        val name = "My first event "
        val date = LocalDateTime.now()
        val owner = "Owner"
        val status = EventStatusEnum.UNPUBLISHED
        val locationName = "My Location"
        val locationAddress = "My Address"

        val location = buildLocationRequest(locationName, locationAddress)
        val eventRequest = buildEventRequest(name, date, location, owner, status)

        // when
        val postForEntity: ResponseEntity<EventResponse> = restTestTemplate.postForEntity(
                "/events",
                eventRequest,
                EventResponse::class.java
        )

        // then
        val statusCode = postForEntity.statusCode
        val eventResponse = postForEntity.body!!
        val locationResponse = eventResponse.location

        assertEquals(HttpStatusCode.valueOf(200), statusCode)

        assertNotNull(eventResponse.id, "event id expected not null")
        assertNotNull(eventResponse.creationDate, "event creationDate expected not null")
        assertEquals(name, eventResponse.name)
        assertEquals(owner, eventResponse.owner)
        assertEquals(date, eventResponse.date)
        assertEquals(status, eventResponse.status)

        assertNotNull(locationResponse.id, "location creationDate expected not null")
        assertNotNull(locationResponse.creationDate, "location creationDate expected not null")
        assertEquals(locationName, locationResponse.name)
        assertEquals(locationAddress, locationResponse.address)
    }

    private fun buildLocationRequest(locationName: String, locationAddress: String) =
            LocationRequest(name = locationName, address = locationAddress)

    private fun buildEventRequest(name: String,
                                  date: LocalDateTime,
                                  location: LocationRequest,
                                  owner: String,
                                  status: EventStatusEnum): EventRequest {
        return EventRequest(null, name, date, location, owner, status)
    }


}