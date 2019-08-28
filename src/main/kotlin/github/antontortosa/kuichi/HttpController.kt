package github.antontortosa.kuichi

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.time.LocalDate
import java.time.LocalDateTime

@Controller
class HtmlController(private val repository: ClientRepository) {

    @GetMapping("/")
    fun kuichi(model: Model): String {
        model["title"] = "Kuichi"
        model["clients"] = repository.findAll().map { it.render() }
        return "kuichi"
    }

    @GetMapping("/client/{login}")
    fun article(@PathVariable login: String, model: Model): String {
        model["title"] = "Client Info"
        val client = repository
                .findByLogin(login)
                ?.render()
                ?: throw IllegalArgumentException("Wrong article slug provided")
        model["name"] = client.name
        model["surname"] = client.surname
        model["login"] = client.login
        model["birthdate"] = client.birthdate
        return "client"
    }

    fun Client.render() = RenderedClient(
            name,
            surname,
            birthdate,
            login,
            signdate,
            address
    )


    data class RenderedClient(
            val name: String,
            val surname: String,
            val birthdate: LocalDate,
            val login: String,
            val signdate: LocalDateTime,
            val addreess: Address)

}
