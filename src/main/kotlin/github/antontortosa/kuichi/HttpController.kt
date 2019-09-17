package github.antontortosa.kuichi

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDate
import java.time.LocalDateTime

@Controller
class HtmlController(private val clientService: ClientService, private val addressService: AddressService) {

    @GetMapping("/")
    fun kuichi(model: Model): String {
        model["title"] = "Kuichi"
        model["clients"] = clientService.retrieveAll()
        return "kuichi"
    }

    @GetMapping("/client/{login}")
    fun article(@PathVariable login: String, model: Model): String {
        model["title"] = "Client Info"
        val client = clientService
                .retrieveByLogin(login)
                ?: throw IllegalArgumentException("Wrong login provided")
        model["name"] = client.name ?: ""
        model["surname"] = client.surname ?: ""
        model["login"] = client.login ?: ""
        model["birthdate"] = client.birthDate ?: ""
        return "client"
    }
}
