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
        val postForEntity: ResponseEntity<EventResponse> = restTestTemplate.postForEntity(
                "/events",
                EventRequest(
                        name = "My first event ",
                        date = LocalDateTime.now(),
                        location = LocationRequest(name = "My Location", address = "My Address"),
                        owner = "Owner",
                        status = EventStatusEnum.UNPUBLISHED
                ),
                EventResponse::class.java
        )

        assertEquals(HttpStatusCode.valueOf(200), postForEntity.statusCode)
    }


}