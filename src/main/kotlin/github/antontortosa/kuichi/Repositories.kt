package github.antontortosa.kuichi

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import com.sun.deploy.util.SearchPath.findOne
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.*


@Transactional(Transactional.TxType.MANDATORY)
internal interface ClientRepository : JpaRepository <ClientEntity,Long>{
    @Query("""SELECT c FROM ClientEntity c WHERE LOWER(c.login) = LOWER(:login)""")
    fun findByLogin(@Param("login") login: String): Optional<ClientEntity>
}

interface ClientService {

    fun retrieve(clientId: Long): ClientEntity?

    fun retrieveAll(): List<ClientEntity>

    fun add(client: CreateClientDto): ClientEntity

    fun update(id: Long, client: UpdateClientDto): ClientEntity?

    fun retrieveByLogin(login: String): ClientEntity?
}

@Service
@Transactional
internal class JpaClientService(val clientRepo: ClientRepository, val addressRepo: AddressRepository) : ClientService {

    override fun retrieveByLogin(login: String): ClientEntity? {
        val optionalClient = clientRepo.findByLogin(login)
        return if(optionalClient.isPresent) optionalClient.get() else null
    }

    override fun retrieve(clientId: Long) : ClientEntity? {
        val optionalClient  = clientRepo.findById(clientId)
        return if(optionalClient.isPresent) optionalClient.get() else null
    }

    override fun retrieveAll() : List<ClientEntity> {
        return clientRepo.findAll()
    }

    override fun add(client: CreateClientDto) : ClientEntity {
        val clientToAdd = ClientEntity(
                name = client.name,
                surname = client.surname,
                birthDate = client.birthDate,
                signDate = LocalDateTime.now(),
                login = client.login,
                address = addressRepo.getOne(client.address)
        )
        return clientRepo.save(clientToAdd)
    }

    override fun update(id: Long, client: UpdateClientDto): ClientEntity? {
        val currentClient  = clientRepo.findById(id).takeIf { it.isPresent }?.get()
        currentClient?.let {
            it.name = client.name?:it.name
            it.surname = client.surname?:it.surname
            it.birthDate = client.birthDate?:it.birthDate
            it.login = client.login?:it.login
            it.address = addressRepo.findByIdOrNull(client.address)?:it.address
        }
        return if (currentClient != null) clientRepo.save(currentClient) else null
    }
}

@Transactional(Transactional.TxType.MANDATORY)
internal interface AddressRepository : JpaRepository<AddressEntity, Long>

interface AddressService {

    fun retrieve(addressId: Long): AddressEntity?

    fun retrieveAll(): List<AddressEntity>

    fun add(address: CreateAddressDto): AddressEntity

    fun update(id: Long, address: UpdateAddressDto): AddressEntity?
}

@Service
@Transactional
internal class JpaAddressService(val addressRepo: AddressRepository) : AddressService {
    override fun retrieve(addressId: Long) : AddressEntity? {
        val optionalAddress  = addressRepo.findById(addressId)
        return if(optionalAddress.isPresent) optionalAddress.get() else null
    }

    override fun retrieveAll() : List<AddressEntity> {
        return addressRepo.findAll()
    }

    override fun add(address: CreateAddressDto) : AddressEntity {
        val addressToAdd = AddressEntity(
                street = address.street,
                streetCont = address.streetCont,
                city = address.city,
                state = address.state,
                zip = address.zip
        )
        return addressRepo.save(addressToAdd)
    }

    override fun update(id: Long, address: UpdateAddressDto): AddressEntity? {
        val currentAddress  = addressRepo.findById(id).takeIf { it.isPresent }?.get()
        currentAddress?.let {
            it.street = address.street?:it.street
            it.streetCont = address.streetCont?:it.streetCont
            it.city = address.city?:it.city
            it.state = address.state?:it.state
            it.zip = address.zip?:it.zip
        }
        return if (currentAddress != null) addressRepo.save(currentAddress) else null
    }
}

@Transactional(Transactional.TxType.MANDATORY)
internal interface CollectionRepository : JpaRepository<CollectionEntity, Long>

interface CollectionService {

    fun retrieve(collectionId: Long): CollectionEntity?

    fun retrieveAll(): List<CollectionEntity>

    fun add(collection: CreateCollectionDto): CollectionEntity

    fun update(id: Long, collection: UpdateCollectionDto): CollectionEntity?
}
@Service
@Transactional
internal class JpaCollectionService(val collectionRepo: CollectionRepository):CollectionService{
    override fun retrieve(collectionId: Long): CollectionEntity? {
        val optionalCollection  = collectionRepo.findById(collectionId)
        return if(optionalCollection.isPresent) optionalCollection.get() else null
    }

    override fun retrieveAll(): List<CollectionEntity> =
            collectionRepo.findAll()

    override fun add(collection: CreateCollectionDto): CollectionEntity {
        val collectionToAdd = CollectionEntity(
                name = collection.name,
                description = collection.description
        )
        return collectionRepo.save(collectionToAdd)
    }

    override fun update(id: Long, collection: UpdateCollectionDto): CollectionEntity? {
        val currentCollection  = collectionRepo.findById(id).takeIf { it.isPresent }?.get()
        currentCollection?.let {
            it.name = collection.name?:it.name
            it.description = collection.description?:it.description
        }
        return if (currentCollection != null) collectionRepo.save(currentCollection) else null
    }

}


@Transactional(Transactional.TxType.MANDATORY)
internal interface ItemRepository : JpaRepository<ItemEntity, Long>
interface ItemService {

    fun retrieve(itemId: Long): ItemEntity?

    fun retrieveAll(): List<ItemEntity>

    fun add(item: CreateItemDto): ItemEntity

    fun update(id: Long, client: UpdateItemDto): ItemEntity?
}

@Service
@Transactional
internal class JpaItemService(val itemRepo: ItemRepository,
                              val collectionRepo: CollectionRepository,
                              val modelRepo: ModelRepository) : ItemService {
    override fun retrieve(itemId: Long): ItemEntity? {
        val optionalItem  = itemRepo.findById(itemId)
        return if(optionalItem.isPresent) optionalItem.get() else null
    }

    override fun retrieveAll(): List<ItemEntity> =
        itemRepo.findAll()

    override fun add(item: CreateItemDto): ItemEntity {
        val itemToAdd = ItemEntity(
                price = item.price,
                color = item.color,
                collection = collectionRepo.findByIdOrNull(item.collection),
                model = modelRepo.findByIdOrNull(item.model)
        )
        return itemRepo.save(itemToAdd)
    }

    override fun update(id: Long, item: UpdateItemDto): ItemEntity? {
        val currentItem = itemRepo.findByIdOrNull(id)
        currentItem?.let {
            it.price = item.price?:it.price
            it.color = item.color?:it.color
            it.model = modelRepo.findByIdOrNull(item.model)?:it.model
            it.collection = collectionRepo.findByIdOrNull(item.collection)?:it.collection
        }
        return if(currentItem!=null) itemRepo.save(currentItem) else null
    }

}

@Transactional(Transactional.TxType.MANDATORY)
internal interface ModelRepository : JpaRepository<ModelEntity, Long>
interface ModelService {

    fun retrieve(modelId: Long): ModelEntity?

    fun retrieveAll(): List<ModelEntity>

    fun add(client: CreateModelDto): ModelEntity

    fun update(id: Long, model: UpdateModelDto): ModelEntity?
}

@Service
@Transactional
internal class JpaModelService(val modelRepo: ModelRepository) : ModelService {
    override fun retrieve(modelId: Long): ModelEntity? {
        val optionalModel  = modelRepo.findById(modelId)
        return if(optionalModel.isPresent) optionalModel.get() else null
    }

    override fun retrieveAll(): List<ModelEntity> =
            modelRepo.findAll()

    override fun add(model: CreateModelDto): ModelEntity {
        val modelToAdd = ModelEntity(
                name = model.name,
                stock = model.stock
        )
        return modelRepo.save(modelToAdd)
    }

    override fun update(id: Long, model: UpdateModelDto): ModelEntity? {
        val currentModel = modelRepo.findByIdOrNull(id)
        currentModel?.let {
            it.name = model.name?:it.name
            it.stock = model.stock?:it.stock
        }
        return if(currentModel!=null) modelRepo.save(currentModel) else null
    }

}

@Transactional(Transactional.TxType.MANDATORY)
internal interface FavoritesRepository : JpaRepository<FavoritesEntity, Long>


