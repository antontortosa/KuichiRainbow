package github.antontortosa.kuichi

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.Month

@Configuration
class BlogConfiguration {

    @Bean
    fun databaseInitializer(clientRepository: ClientRepository,
                            addressRepository: AddressRepository) = ApplicationRunner {

        val address1 = addressRepository.save(Address(street = "2345 My Place", street_cont = "Apt 666", city = "Chicago", state = StateEnum.IL, zip = 60616))
        clientRepository.save(Client(
                name = "Antonio",
                surname = "Tortosa",
                birthdate = LocalDate.of(1994,Month.NOVEMBER, 17),
                address = address1,
                login = "antontortosa"
        ))
        val address2 = addressRepository.save(Address(street = "123 Your Place", street_cont = "Apt 333", city = "Chicago", state = StateEnum.IL, zip = 60615))
        clientRepository.save(Client(
                name = "Emilia",
                surname = "Rosales",
                birthdate = LocalDate.of(1998,Month.JULY, 10),
                address = address2,
                login = "emrose"
        ))
    }
}