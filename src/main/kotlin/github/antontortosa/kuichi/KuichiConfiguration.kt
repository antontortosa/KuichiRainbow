package github.antontortosa.kuichi

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import java.time.Month

@Configuration
class BlogConfiguration {

    @Bean
    fun databaseInitializer(clientService: ClientService,
                            addressService: AddressService,
                            collectionService: CollectionService,
                            modelService: ModelService,
                            itemService: ItemService
    ) = ApplicationRunner {

        val address1 = addressService.add(CreateAddressDto(street = "2345 My Place", streetCont = "Apt 666", city = "Chicago", state = StateEnum.IL, zip = 60616))
        clientService.add(CreateClientDto(
                name = "Antonio",
                surname = "Tortosa",
                birthDate = LocalDate.of(1994,Month.NOVEMBER, 17),
                address = address1.id!!,
                login = "antontortosa"
        ))
        val address2 = addressService.add(CreateAddressDto(street = "123 Your Place", streetCont = "Apt 333", city = "Chicago", state = StateEnum.IL, zip = 60615))
        clientService.add(CreateClientDto(
                name = "Emilia",
                surname = "Rosales",
                birthDate = LocalDate.of(1998,Month.JULY, 10),
                address = address2.id!!,
                login = "emrose"
        ))

        val collection1 = collectionService.add(CreateCollectionDto(name = "Spring19" , description = "Spring 2019 Collection"))
        val model1 = modelService.add(CreateModelDto(name = "Earrings1", stock = 15))
        val jewel1 = itemService.add(CreateItemDto(price = 50.0, color = ColorEnum.RED, collection = collection1.id!!, model = model1.id!!))

    }
}