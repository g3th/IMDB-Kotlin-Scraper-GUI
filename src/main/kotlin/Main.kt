import androidx.compose.ui.window.Window
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.unit.dp
import org.jsoup.*

class ImdbScraper(var query: String){
		
	var Agent:String = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.5112.101 Safari/537.36"
	var pageQuery:String = "https://www.imdb.com/find?q=${query}"

	fun getActor():String {
		var rawHtml = Jsoup.connect(pageQuery).userAgent(Agent).get()
		var parsedPage = rawHtml.select("a[class='ipc-metadata-list-summary-item__t']")
		var searchLink:List<String> = emptyList()
		for (element in parsedPage){
			searchLink = searchLink.plus(element.attr("href"))
			}
		var actorQuery:String = searchLink[0]
		return actorQuery
		}
	
	fun getFilms(): List<String>{
		var actorLink:String = getActor()
		var filmsQuery = "https://www.imdb.com/${actorLink}"
		var rawFilms = Jsoup.connect(filmsQuery).userAgent(Agent).get()
		var parsedFilms = rawFilms.select("div[class='filmo-row odd']")
		var filmList:List<String> = emptyList()
		for (element in parsedFilms){
			filmList = filmList.plus(element.text())
			}
		filmList = filmList.sortedBy{it}
		return filmList
		}
	
}
	
fun main()= application {
	Window( 
		onCloseRequest = ::exitApplication,
		title = "Movie Database Scraper Example",
		state = rememberWindowState(width=600.dp, height=300.dp)
	) { window.isResizable = false
	
	}
	/*var Query = ImdbScraper("roberto+benigni")
	print(Query.getActor())
	println(Query.getFilms())*/	
}
