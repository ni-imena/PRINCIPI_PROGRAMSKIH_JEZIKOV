import androidx.compose.ui.text.toUpperCase
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.extractIt
import it.skrape.fetcher.skrape
import java.util.*

enum class Cloudiness {
    Clear, ModCloudy, MostClear, PartCloud, PrevCloudy, Overcast, NotSpecified, ModRa, LightRa, ModSN, ModRASN, Overcast_modRA,;
}

fun getCloud(src: String?): Cloudiness {
    for (level in Cloudiness.values()) {
        if (src != null && level.toString().uppercase(Locale.getDefault()) in src.uppercase(Locale.getDefault()))
            return level
    }
    return Cloudiness.NotSpecified
}

data class Weather(
    val city: String,
    val temperature: String,
    val aproxCloud: String,
    val humidity: String
)

data class ExtractedDatas(var cities: List<Weather?> = listOf())

fun main() {
    val extractedDatas = skrape(HttpFetcher) {
        request {
            url = "https://meteo.arso.gov.si/uploads/probase/www/observ/surface/text/sl/observationAms_si_latest.html"
        }

        extractIt<ExtractedDatas> {
            htmlDocument {
                it.cities = findAll("tr").drop(1).map { trElement ->
                    try {
                        val tds = trElement.findAll("td")
                        val city = tds[0].text
                        val imgSrc: String = try {
                            tds[1].findFirst("img").attribute("src").substringAfterLast("/").dropLast(4)
                        } catch (e:Exception){
                            Cloudiness.NotSpecified.toString()
                        }
                        val temp = tds[2].text
                        val humidity = tds[3].text
                        Weather(city = city, temperature = temp, aproxCloud = imgSrc, humidity = humidity)
                    }
                    catch (e:Exception){
                        null
                    }
                }
            }
        }
    }

    println("-".repeat(100))
    println("---------------------------------------- extracted weather -----------------------------------------")
    println("-".repeat(100))
    println(String.format("%-40s%-25s%-25s%-25s", "CITY", "TEMPERATURE", "CLOUDINESS", "HUMIDITY"))
    println("-".repeat(120))
    extractedDatas.cities.forEach {
        if (it != null) {
            println(String.format("%-40s%-25s%-25s%-25s", it.city, it.temperature, it.aproxCloud.toString(), it.humidity))
        }
    }
    println("-".repeat(100))
}
