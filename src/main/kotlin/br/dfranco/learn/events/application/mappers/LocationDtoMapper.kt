package br.dfranco.learn.events.application.mappers

import br.dfranco.learn.events.application.dto.LocationDto
import br.dfranco.learn.events.domain.entities.LocationEntity
import org.mapstruct.Mapper

@Mapper
interface LocationDtoMapper {


    fun dtoToEntity(locationDto: LocationDto): LocationEntity

    fun entityToDto(locationDto: LocationEntity): LocationDto

}