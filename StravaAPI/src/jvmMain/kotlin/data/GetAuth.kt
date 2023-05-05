package data.data

import data.CallbackServer
import java.awt.Desktop
import java.net.URI

class AuthenticationManager {


    fun authenticateUser(
        domain: String,
        clientId: String,
        scope: String
    ): String {
        val server = CallbackServer()
        val callbackUrl = server.startServer()
        val url = createLoginUrl(
            domain = domain,
            clientId = clientId,
            redirectUri = callbackUrl,
            scope = scope
        )
        Desktop.getDesktop().browse(URI(url))

        // Wait for the authorization code to be received
        while (true) {
            val code = server.getCode()
            if (code != null) {
                server.stopServer()
                println(code)
                return code
            }
            Thread.sleep(100)
        }
    }


    private fun createLoginUrl(
        domain: String,
        clientId: String,
        redirectUri: String,
        scope: String
    ): String {
        return "https://$domain/authorize?client_id=$clientId&response_type=code&" +
                "redirect_uri=$redirectUri&scope=activity:$scope"
    }
}