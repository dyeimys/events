package br.dfranco.learn.events.application.usecases

import br.dfranco.learn.events.application.dtos.Location
import br.dfranco.learn.events.application.mappers.LocationEntityMapper
import br.dfranco.learn.events.domain.entities.LocationEntity
import br.dfranco.learn.events.domain.exceptions.LocationAlreadyExistsException
import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class CreateLocationUseCaseTest {


    @InjectMocks
    private lateinit var createLocationUseCase: CreateLocationUseCase

    @Mock
    private lateinit var locationRepository: LocationRepository

    @Mock
    private lateinit var locationEntityMapper: LocationEntityMapper

    @Test
    fun `should create location when does not exist`() {


        // Arrange
        val locationId = UUID.randomUUID()
        val creationDate = LocalDateTime.now()
        val locationName = "Test Location"
        val locationAddress = "Test Address"

        val locationInput = Location.CreateLocationInput(locationName, locationAddress)
        val locationEntity = LocationEntity(locationId, locationName, locationAddress, creationDate)
        val locationOutput = Location.CreateLocationOutput(locationName, locationAddress, creationDate)

        `when`(locationRepository.existsByNameAndAddress(locationInput.name, locationInput.address)).thenReturn(false)
        `when`(locationEntityMapper.toEntity(locationInput)).thenReturn(locationEntity)
        `when`(locationRepository.save(locationEntity)).thenReturn(locationEntity)
        `when`(locationEntityMapper.toLocationOutput(locationEntity)).thenReturn(locationOutput)

        // Act
        val result = createLocationUseCase.execute(locationInput)

        // Assert
        assertNotNull(result)
        assertEquals(locationOutput.name, result.name)
        assertEquals(locationOutput.address, result.address)

        verify(locationRepository).existsByNameAndAddress(locationInput.name, locationInput.address)
        verify(locationEntityMapper).toEntity(locationInput)
        verify(locationRepository).save(locationEntity)
        verify(locationEntityMapper).toLocationOutput(locationEntity)
    }

    @Test
    fun `should throw LocationAlreadyExistsException when location already exists`() {
        // Arrange
        val locationInput = Location.CreateLocationInput("Test Location", "Test Address")

        `when`(locationRepository.existsByNameAndAddress(locationInput.name, locationInput.address)).thenReturn(true)

        // Act and Assert
        assertThrows(LocationAlreadyExistsException::class.java) {
            createLocationUseCase.execute(locationInput)
        }

        verify(locationRepository).existsByNameAndAddress(locationInput.name, locationInput.address)
        verifyNoMoreInteractions(locationEntityMapper, locationRepository)
    }

}