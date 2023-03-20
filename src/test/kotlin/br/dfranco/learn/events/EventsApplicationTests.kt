package br.dfranco.learn.events

import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EventsApplicationTests {


	@Test
	@Order(1)
	fun `run main application`(){
		main(arrayOf("--spring.main.web-environment=false"))
	}
	@Test
	fun contextLoads() {

	}

}
