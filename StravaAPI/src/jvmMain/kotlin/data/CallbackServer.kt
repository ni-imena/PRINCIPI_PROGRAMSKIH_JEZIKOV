package data

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.install
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import io.ktor.server.routing.get
import java.util.UUID

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
