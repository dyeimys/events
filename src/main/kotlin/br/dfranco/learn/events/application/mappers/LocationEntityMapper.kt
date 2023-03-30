package br.dfranco.learn.events.application.mappers

import br.dfranco.learn.events.application.dtos.Location
import br.dfranco.learn.events.domain.entities.LocationEntity
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface LocationEntityMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    fun toEntity(input: Location.CreateLocationInput): LocationEntity

    fun toLocationOutput(entity: LocationEntity): Location.CreateLocationOutput


}