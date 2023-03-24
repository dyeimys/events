package br.dfranco.learn.events.application.mappers

import br.dfranco.learn.events.application.dtos.Event
import br.dfranco.learn.events.domain.entities.EventEntity
import br.dfranco.learn.events.domain.enuns.EventStatusEnum
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(imports = [EventStatusEnum::class])
interface EventEntityMapper {

    @Mapping(target = "status", expression = "java(EventStatusEnum.UNPUBLISHED)")
    fun toEntity(eventInput: Event.CreateEventInput): EventEntity

    fun toOutput(entity: EventEntity): Event.CreateEventOutput

}