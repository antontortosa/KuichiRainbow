package github.antontortosa.kuichi

import org.springframework.data.repository.CrudRepository

interface ClientRepository : CrudRepository<Client, Long> {
    //fun findByFullName(fullName: String): Client?
    fun findByLogin(login:String): Client?
}

interface AddressRepository : CrudRepository<Address, Long> {
    fun findByZip(zip: Long): Iterable<Address>
}