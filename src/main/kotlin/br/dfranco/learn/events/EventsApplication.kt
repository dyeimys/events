package br.dfranco.learn.events

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class EventsApplication


fun main(args: Array<String>) {
	runApplication<EventsApplication>(*args)
}
