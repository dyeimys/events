package br.dfranco.learn.events.application.mappers

import br.dfranco.learn.events.application.dtos.EventDto
import br.dfranco.learn.events.domain.entities.EventEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface EventEntityMapper {

    fun toEntity(dto: EventDto): EventEntity

    @Mapping(target = "locationId", source = "location.id")
    fun toDto(entity: EventEntity): EventDto

}