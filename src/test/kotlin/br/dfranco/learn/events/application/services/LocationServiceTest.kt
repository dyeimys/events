package br.dfranco.learn.events.application.services

import br.dfranco.learn.events.domain.entities.LocationEntity
import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.*


@SpringBootTest
internal class LocationServiceTest {
    @Mock
    lateinit var locationRepository: LocationRepository

    @InjectMocks
    lateinit var locationService: LocationService

    @Test
    fun `should retrieve location in findById`() {
        // given
        val locationId = UUID.randomUUID()
        val nameParam = "Name of Location "
        val addressParam = "Address of Location"
        val creationDateParam = LocalDateTime.now()
        val locationEntity = LocationEntity(locationId, nameParam, addressParam, creationDateParam)

        `when`(locationRepository.findById(locationId)).thenReturn(Optional.of(locationEntity))

        // when
        val findById = locationService.findById(locationId)

        //then
        assertThat(findById).isPresent
        findById.get().run {
            assertThat(id).isNotNull
            assertThat(name).isNotNull
            assertThat(address).isNotNull
            assertThat(creationDate).isNotNull
            assertThat(updateDate).isNull()
        }
    }

    @Test
    fun `should retrieve a empty location in findById`() {
        // given
        val locationId = UUID.randomUUID()
        `when`(locationRepository.findById(locationId)).thenReturn(Optional.empty())

        // when
        val findById = locationService.findById(locationId)

        //then
        assertThat(findById).isEmpty
    }


    @Test
    fun `should retrieve location in findOrCreate`() {
        // given
        val locationId = UUID.randomUUID()
        val nameDb = "Name of Location in data base"
        val nameParam = "Name of Location"
        val addressDb = "Address of Location in data base"
        val addressParam = "Address of Location"
        val creationDateDb = LocalDateTime.now()
        val creationDateParam = LocalDateTime.now()
        val locationEntityParam = LocationEntity(locationId, nameParam, addressParam, creationDateParam)
        val locationEntityDbMock = LocationEntity(locationId, nameDb, addressDb, creationDateDb)

        `when`(locationRepository.findById(locationId)).thenReturn(Optional.of(locationEntityDbMock))

        // when
        val findById = locationService.findOrCreate(locationEntityParam)

        //then
        assertThat(findById).isPresent
        findById.get().run {
            assertThat(id).isEqualTo(locationId)
            assertThat(name).isEqualTo(nameDb)
            assertThat(address).isEqualTo(addressDb)
            assertThat(creationDate).isEqualTo(creationDateDb)
            assertThat(updateDate).isNull()
        }

        verify(locationRepository).findById(any())
        verify(locationRepository, never()).save(any())
    }

    @Test
    fun `should create location in findOrCreate`() {
        // given
        val locationId = UUID.randomUUID()
        val nameDb = "Name of Location in data base"
        val nameParam = "Name of Location"
        val addressDb = "Address of Location in data base"
        val addressParam = "Address of Location"
        val creationDateDb = LocalDateTime.now()
        val creationDateParam = LocalDateTime.now()
        val locationEntityParam = LocationEntity(id = null, nameParam, addressParam, creationDateParam)
        val locationEntityDbMock = LocationEntity(locationId, nameDb, addressDb, creationDateDb)

        `when`(locationRepository.findById(locationId)).thenReturn(Optional.empty())
        `when`(locationRepository.save(locationEntityParam)).thenReturn(locationEntityDbMock)


        // when
        val findById = locationService.findOrCreate(locationEntityParam)

        //then
        assertThat(findById).isPresent
        findById.get().run {
            assertThat(id).isEqualTo(locationId)
            assertThat(name).isEqualTo(nameDb)
            assertThat(address).isEqualTo(addressDb)
            assertThat(creationDate).isEqualTo(creationDateDb)
            assertThat(updateDate).isNull()
        }

        verify(locationRepository, never()).findById(any())
        verify(locationRepository).save(any())
    }

}