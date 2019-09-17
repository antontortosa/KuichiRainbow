package github.antontortosa.kuichi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.util.ProxyUtils
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*


@MappedSuperclass
abstract class AbstractJpaPersistable<T> {

    @Id
    @GeneratedValue
    internal var id: T? = null

    fun getId(): T? {
        return id
    }

    fun setID(id:T?){
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) return true

        if (javaClass != ProxyUtils.getUserClass(other)) return false

        other as AbstractJpaPersistable<*>

        return if (null == this.getId()) false else this.getId() == other.getId()
    }

    override fun hashCode(): Int {
        return 31
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"

}

@Entity
@Table(name = "Client")
data class ClientEntity(
        var name: String? = null,
        var surname: String? = null,
        var login: String? = null,
        var birthDate: LocalDate? = null,
        var signDate: LocalDateTime? = LocalDateTime.now(),
        @ManyToOne var address: AddressEntity? = null
) : AbstractJpaPersistable<Long>()

@Entity
@Table(name = "Address")
data class AddressEntity(
        var street: String? = null,
        var streetCont: String? = null,
        var city: String? = null,
        @Enumerated(EnumType.STRING) var state: StateEnum? = null,
        var zip: Long? = null
): AbstractJpaPersistable<Long>()

@Entity
@Table(name = "Collection")
data class CollectionEntity(
        var name: String?,
        var description: String?
):AbstractJpaPersistable<Long>()

@Entity
@Table(name = "Item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="item_type",
        discriminatorType = DiscriminatorType.STRING)
data class ItemEntity (
        var price: Double? = null,
        @Enumerated (EnumType.STRING) var color: ColorEnum? = null,
        @ManyToOne var model: ModelEntity? = null,
        @ManyToOne var collection: CollectionEntity? = null
): AbstractJpaPersistable<Long>()

@Entity
@Table(name = "Jewelry")
@DiscriminatorValue(value = "J")
class JewelryEntity(
        @Enumerated(EnumType.STRING) var type: JewelType? = null
):ItemEntity()

@Entity
@Table(name = "Accessories")
@DiscriminatorValue(value = "A")
class AccessoriesEntity(
        @Enumerated(EnumType.STRING) var type: AccessoryType? = null
):ItemEntity()

@Entity
@Table(name = "Model")
data class ModelEntity (
        var name: String?,
        var stock: Long?
):AbstractJpaPersistable<Long>()

@Entity
@Table(name = "Favorites")
data class FavoritesEntity(
       @ManyToOne var client:ClientEntity?=null,
       @ManyToOne var model:ModelEntity?=null
):AbstractJpaPersistable<Long>()
