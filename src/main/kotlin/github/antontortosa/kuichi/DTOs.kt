package github.antontortosa.kuichi

import java.time.LocalDate
import java.time.LocalDateTime

/*
* CLIENTS
* */
data class CreateClientDto(
        val name: String,
        val surname: String,
        val login: String,
        val birthDate: LocalDate,
        val address: Long
)

data class UpdateClientDto(
        val id:Long,
        val name: String?,
        val surname: String?,
        val login: String?,
        val birthDate: LocalDate?,
        val signDate: LocalDateTime?,
        val address: Long?
)

/*
* ADDRESS
* */
data class CreateAddressDto(
        val street: String,
        val streetCont: String?,
        val city: String,
        val state: StateEnum,
        val zip: Long
)

data class UpdateAddressDto(
        val id:Long,
        val street: String?,
        val streetCont: String?,
        val city: String?,
        val state: StateEnum?,
        val zip: Long?
)

/*
* COLLECTION
* */
data class CreateCollectionDto(
        val name:String,
        val description:String
)

data class UpdateCollectionDto(
        val name:String?,
        val description:String?
)

/*
* MODEL
* */
data class CreateModelDto(
        val name:String,
        val stock:Long
)

data class UpdateModelDto(
        val name:String?,
        val stock:Long?
)

/*
* ITEM
* */
data class CreateItemDto(
        val price:Double,
        val color:ColorEnum,
        val model:Long,
        val collection:Long
)

data class UpdateItemDto(
        val price:Double?,
        val color:ColorEnum?,
        val model:Long?,
        val collection:Long?
)


