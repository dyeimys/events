package br.dfranco.learn.events.application.mappers

import br.dfranco.learn.events.application.dtos.Event
import br.dfranco.learn.events.domain.entities.EventEntity
import org.mapstruct.Mapper

@Mapper
interface EventEntityMapper {

    fun toEntity(eventInput: Event.CreateEventInput): EventEntity

    fun toOutput(entity: EventEntity): Event.CreateEventOutput

}