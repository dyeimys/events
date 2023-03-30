package br.dfranco.learn.events.infrastructure.persistence

import br.dfranco.learn.events.domain.entities.LocationEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
internal class LocationRepositoryTest {

    @Autowired
    lateinit var locationRepository: LocationRepository


    @AfterEach
    fun before() {
        locationRepository.deleteAll()
    }

    @Test
    fun `should return true when a location with the same name and address exists`() {
        // Given
        val location = LocationEntity(name = "Location A", address = "123 Main St")
        locationRepository.save(location)

        // When
        val exists = locationRepository.existsByNameAndAddress(location.name, location.address)

        // Then
        assertThat(exists).isTrue
    }

    @Test
    fun `should return false when a location with the same name and different address exists`() {
        // Given
        val location = LocationEntity(name = "Location A", address = "123 Main St")
        locationRepository.save(location)

        // When
        val exists = locationRepository.existsByNameAndAddress(location.name, "456 Second St")

        // Then
        assertThat(exists).isFalse
    }

    @Test
    fun `should return false when a location with the same address and different name exists`() {
        // Given
        val location = LocationEntity(name = "Location A", address = "123 Main St")
        locationRepository.save(location)

        // When
        val exists = locationRepository.existsByNameAndAddress("Location B", location.address)

        // Then
        assertThat(exists).isFalse
    }

    @Test
    fun `should return false when no location with the same name and address exists`() {
        // Given
        val location = LocationEntity(name = "Location A", address = "123 Main St")
        locationRepository.save(location)

        // When
        val exists = locationRepository.existsByNameAndAddress("Location B", "456 Second St")

        // Then
        assertThat(exists).isFalse
    }
}