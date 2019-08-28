package github.antontortosa.kuichi

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Client(
        var name: String,
        var surname: String,
        var birthdate: LocalDateTime,
        var signdate: LocalDateTime = LocalDateTime.now(),
        @ManyToOne var address: Address,
        @Id @GeneratedValue var id: Long)

@Entity
class Address(
        @Id @GeneratedValue var id: Long,
        var street: String,
        var street_cont: String,
        var city: String,
        @Enumerated(EnumType.STRING) var state: StateEnum,
        var zip: Long
)