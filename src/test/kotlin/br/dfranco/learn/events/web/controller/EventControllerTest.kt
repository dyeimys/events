package br.dfranco.learn.events.web.controller

import br.dfranco.learn.events.dtos.LocationDto
import br.dfranco.learn.events.enuns.EventStatusEnum
import br.dfranco.learn.events.web.request.EventRequest
import br.dfranco.learn.events.web.request.LocationRequest
import br.dfranco.learn.events.web.response.EventResponse
import org.junit.jupiter.api.Assertions.assertEquals
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

        val name = "My first event "
        val date = LocalDateTime.now()
        val location = LocationRequest(name = "My Location", address = "My Address")
        val owner = "Owner"
        val status = EventStatusEnum.UNPUBLISHED

        val eventRequest = eventRequest(name, date, location, owner, status)


        val postForEntity: ResponseEntity<EventResponse> = restTestTemplate.postForEntity(
                "/events",
                eventRequest,
                EventResponse::class.java
        )

        assertEquals(HttpStatusCode.valueOf(200), postForEntity.statusCode)
    }

    private fun eventRequest(name: String,
                             date: LocalDateTime,
                             location: LocationRequest,
                             owner: String,
                             status: EventStatusEnum): EventRequest {
        return EventRequest(null, name, date, location, owner, status)
    }


}