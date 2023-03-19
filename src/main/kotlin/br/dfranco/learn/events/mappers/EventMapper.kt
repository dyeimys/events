package br.dfranco.learn.events.mappers

import br.dfranco.learn.events.dtos.EventDto
import br.dfranco.learn.events.entities.EventEntity
import org.mapstruct.Mapper

@Mapper(uses = [LocationMapper::class])
interface EventMapper {

    fun dtoToEntity(eventDto: EventDto): EventEntity

    fun entityToDto(eventDto: EventEntity): EventDto

}