//package br.dfranco.learn.events.repositories
//
//import br.dfranco.learn.events.domain.entities.LocationEntity
//import br.dfranco.learn.events.infrastructure.persistence.LocationRepository
//import org.junit.jupiter.api.AfterEach
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//
//@SpringBootTest
//internal class LocationRepositoryTest {
//    @Autowired
//    lateinit var locationRepository: LocationRepository
//
//    @AfterEach
//    fun before() {
//        locationRepository.deleteAll()
//    }
//
//    @Test
//    fun `should salve one location`() {
//
//        // given
//        val locationName = "Draw your body"
//        val locationAddress = "Dream Street"
//
//        val locationEntity = LocationEntity(name = locationName, address = locationAddress)
//
//        // when
//        val locationSaved = locationRepository.save(locationEntity)
//
//        // then
//        Assertions.assertNotNull(locationSaved.id, "id not null")
//        Assertions.assertNotNull(locationSaved.creationDate, "creationDate not null")
//        Assertions.assertEquals(locationName, locationSaved.name)
//        Assertions.assertEquals(locationAddress, locationSaved.address)
//
//    }
//
//    @Test
//    fun `should salve three locations`() {
//
//        // given
//        val locationName = "Draw your body"
//        val locationAddress = "Dream Street"
//
//        val locationEntityOne = LocationEntity(name = locationName, address = locationAddress)
//        val locationEntityTwo = LocationEntity(name = locationName, address = locationAddress)
//        val locationEntityThree = LocationEntity(name = locationName, address = locationAddress)
//
//        // when
//        locationRepository.save(locationEntityOne)
//        locationRepository.save(locationEntityTwo)
//        locationRepository.save(locationEntityThree)
//
//        // then
//        val allLocation = locationRepository.findAll()
//        Assertions.assertEquals(3, allLocation.size)
//    }
//
//}