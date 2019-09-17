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

        val address1 = addressService.add(AddressEntity(street = "2345 My Place", streetCont = "Apt 666", city = "Chicago", state = StateEnum.IL, zip = 60616))
        clientService.add(ClientEntity(
                name = "Antonio",
                surname = "Tortosa",
                birthDate = LocalDate.of(1994,Month.NOVEMBER, 17),
                address = address1,
                login = "antontortosa"
        ))
        val address2 = addressService.add(AddressEntity(street = "123 Your Place", streetCont = "Apt 333", city = "Chicago", state = StateEnum.IL, zip = 60615))
        clientService.add(ClientEntity(
                name = "Emilia",
                surname = "Rosales",
                birthDate = LocalDate.of(1998,Month.JULY, 10),
                address = address2,
                login = "emrose"
        ))

        val collection1 = collectionService.add(CollectionEntity(name = "Spring19" , description = "Spring 2019 Collection"))
        val model1 = modelService.add(ModelEntity(name = "Earrings1", stock = 15))
        val model2 = modelService.add(ModelEntity(name = "Belt1", stock = 15))
        val jewel1 = JewelryEntity(JewelType.Earrings)
        jewel1.price = 50.0
        jewel1.collection = collection1
        jewel1.model = model1
        jewel1.color = ColorEnum.RED
        itemService.add(jewel1)
        val accessory1 = AccessoriesEntity(AccessoryType.Belt)
        accessory1.price = 23.5
        accessory1.collection = collection1
        accessory1.model = model2
        accessory1.color = ColorEnum.GREEN
        itemService.add(accessory1)
        val client1 = clientService.retrieveByLogin("antontortosa")
        val clientUpdate = ClientEntity(surname = "Tortosa Garrido")
        clientService.update(client1?.id!!, clientUpdate)
    }
}