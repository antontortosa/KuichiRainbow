package github.antontortosa.kuichi

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Client(
        var name: String,
        var surname: String,
        var login: String,
        var birthdate: LocalDate,
        var signdate: LocalDateTime = LocalDateTime.now(),
        @ManyToOne var address: Address,
        @Id @GeneratedValue var id: Long?=null)

@Entity
class Address(
        @Id @GeneratedValue var id: Long?=null,
        var street: String,
        var street_cont: String,
        var city: String,
        @Enumerated(EnumType.STRING) var state: StateEnum,
        var zip: Long
)