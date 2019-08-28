package github.antontortosa.kuichi

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.time.LocalDate
import java.time.Month


@DataJpaTest
class RepositoriesTests @Autowired constructor(
        val entityManager: TestEntityManager,
        val clientRepository: ClientRepository,
        val addressRepository: AddressRepository) {

    @Test
    fun `When findByFullName then return Client`() {
        val address = Address(street = "2345 MyPlace", street_cont = "Apt 666",city = "Chicago", state = StateEnum.IL, zip =60616 )
        val self = Client("Antonio", "Tortosa", birthdate = LocalDate.of(1994,Month.NOVEMBER,17), address = address, login = "antontortosa")
        entityManager.persist(self)
        entityManager.flush()
        val found = clientRepository.findByLogin("antontortosa")
        assertThat(found).isEqualTo(self)
    }

    @Test
    fun `When findByZip then return collection with correct zip`() {
        val addres1 = Address(street = "2345 MyPlace", street_cont = "Apt 666",city = "Chicago", state = StateEnum.IL, zip =60616 )
        val addres2 = Address(street = "123 YourPlace", street_cont = "Apt 333",city = "Chicago", state = StateEnum.IL, zip =60617 )
        entityManager.persist(addres1)
        entityManager.persist(addres2)
        entityManager.flush()
        val result = addressRepository.findByZip(60616)
        assertThat(result.firstOrNull()).isEqualTo(addres1)
    }
}