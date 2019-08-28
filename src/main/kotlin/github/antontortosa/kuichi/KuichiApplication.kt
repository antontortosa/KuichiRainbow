package github.antontortosa.kuichi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KuichiApplication

fun main(args: Array<String>) {
    runApplication<KuichiApplication>(*args)
}
