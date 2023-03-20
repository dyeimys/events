package br.dfranco.learn.events.web.controller

import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import br.dfranco.learn.events.presentation.requests.EventRequest
import br.dfranco.learn.events.presentation.requests.LocationRequest
import br.dfranco.learn.events.presentation.responses.EventResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime
import java.util.*


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

        val eventRequest = buildEventRequest(name, date, null, owner)

        // when
        val postForEntity: ResponseEntity<EventResponse> = restTestTemplate.postForEntity(
                "/events",
                eventRequest,
                EventResponse::class.java
        )

        // then
        val statusCode = postForEntity.statusCode
        val eventResponse = postForEntity.body!!

        assertEquals(HttpStatusCode.valueOf(200), statusCode)

        assertNotNull(eventResponse.id, "event id expected not null")
        assertNull(eventResponse.location)
        assertEquals(name, eventResponse.name)
        assertEquals(owner, eventResponse.owner)
        assertEquals(date, eventResponse.date)
        assertEquals(status, eventResponse.status)

    }

    private fun buildLocationRequest(locationName: String, locationAddress: String) =
            LocationRequest(name = locationName, address = locationAddress)

    private fun buildEventRequest(name: String,
                                  date: LocalDateTime,
                                  locationId: UUID?,
                                  owner: String): EventRequest {
        return EventRequest(name, date, locationId, owner)
    }


}