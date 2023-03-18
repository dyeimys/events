package br.dfranco.learn.events.mappers

import br.dfranco.learn.events.dtos.LocationDto
import br.dfranco.learn.events.entities.LocationEntity
import org.mapstruct.Mapper

@Mapper
interface LocationMapper {


    fun dtoToEntity(locationDto: LocationDto): LocationEntity

    fun entityToDto(locationDto: LocationEntity): LocationDto

}