package data

import data.data.model.Weather
import org.jsoup.Jsoup
import java.net.URL
import java.util.*

enum class Cloudiness {
    Clear, ModCloudy, MostClear, PartCloud, PrevCloudy, Overcast, NotSpecified, ModRa, LightRa, ModSN, ModRASN, Overcast_modRA
}

fun getCloud(src: String?): Cloudiness {
    for (level in Cloudiness.values()) {
        if (src != null && level.toString().uppercase(Locale.getDefault()) in src.uppercase(Locale.getDefault()))
            return level
    }
    return Cloudiness.NotSpecified
}

var WeatherList = mutableListOf<Weather>()

fun getWeather() {
    val url = URL("https://meteo.arso.gov.si/uploads/probase/www/observ/surface/text/sl/observationAms_si_latest.html")
    val connection = url.openConnection()
    val html = connection.getInputStream().bufferedReader().use { it.readText() }
    val document = Jsoup.parse(html)
    val cities = document.select("tr").drop(1).mapNotNull { trElement ->
        try {
            val tds = trElement.select("td")
            val city = tds[0].text()
            val imgSrc = try {
                tds[1].selectFirst("img").attr("src").substringAfterLast("/").dropLast(4)
            } catch (e: Exception) {
                Cloudiness.NotSpecified.toString()
            }
            val temp = tds[2].text()
            val humidity = tds[3].text()
            Weather(city = city, temperature = temp, aproxCloud = imgSrc, humidity = humidity)
        } catch (e: Exception) {
            null
        }
    }
    WeatherList.addAll(cities.filterNotNull())
}