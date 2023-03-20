package br.dfranco.learn.events.app.mappers

import br.dfranco.learn.events.domain.dtos.EventDto
import br.dfranco.learn.events.domain.entities.EventEntity
import org.mapstruct.Mapper

@Mapper(uses = [LocationEntityMapper::class])
interface EventEntityMapper {

    fun dtoToEntity(eventDto: EventDto): EventEntity

    fun entityToDto(eventDto: EventEntity): EventDto

}