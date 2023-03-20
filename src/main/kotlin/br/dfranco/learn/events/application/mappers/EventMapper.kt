package br.dfranco.learn.events.application.mappers

import br.dfranco.learn.events.application.dtos.EventDto
import br.dfranco.learn.events.domain.entities.EventEntity
import org.mapstruct.Mapper

@Mapper(uses = [LocationDtoMapper::class])
interface EventMapper {

    fun dtoToEntity(eventDto: EventDto): EventEntity

    fun entityToDto(eventDto: EventEntity): EventDto

}