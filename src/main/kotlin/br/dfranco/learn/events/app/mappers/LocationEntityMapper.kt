package br.dfranco.learn.events.app.mappers

import br.dfranco.learn.events.domain.dtos.LocationDto
import br.dfranco.learn.events.domain.entities.LocationEntity
import org.mapstruct.Mapper

@Mapper
interface LocationEntityMapper {


    fun dtoToEntity(locationDto: LocationDto): LocationEntity

    fun entityToDto(locationDto: LocationEntity): LocationDto

}