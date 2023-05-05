package data

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

class CallbackServer {
    private lateinit var state: String
    private var code: String? = null
    private val server = embeddedServer(Netty, port = 5789) {
        install(DefaultHeaders)
        routing {
            get("/callback") {
                code = call.request.queryParameters["code"]
                call.respond(HttpStatusCode.OK, "Success!")
            }
        }
    }

    fun startServer(): String {
        state = UUID.randomUUID().toString()
        server.start(wait = false)
        return "http://localhost:5789/callback"
    }

    fun stopServer() {
        server.stop(0, 0)
    }

    fun getCode(): String? = code
}
