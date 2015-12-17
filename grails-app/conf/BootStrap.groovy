import app.Country
import app.League
import app.Club
import app.NativeNationality
import app.Player
import app.Tag

class BootStrap {

    def uefaService

    def init = { servletContext ->
        uefaService.initialize()
        Club.withTransaction({
            initUEFA()
            int totalClubs = uefa.collect { it.value.size() }.sum()
            log.debug("UEFA initialized with ${League.count()} leagues and ${Club.count()} clubs")
            assert Club.count() == totalClubs
        })
    }

    def destroy = {
        destroyUEFA()
    }

    private void initUEFA() {
        if (Tag.count() == 0) {
            tags.each { String tag ->
                new Tag(name: tag).save()
            }
        }

        if (League.count() == 0) {
            Country country = new Country(name: 'Argentina').save()
            uefa.each() { entry ->
                League league = new League(name: entry.key).addToTags(Tag.findByName("UEFA"))
                List<String> clubs = entry.value
                clubs?.each() { String name ->
                    Player player1 = new Player(name: "Messi", nationality: new NativeNationality(name: 'ARG'))
                    Player player2 = new Player(name: "Ronaldo", nationality: new NativeNationality(name: 'POR'))
                    Player player3 = new Player(name: "Neymar")
                    Club club = new Club(name: name, big: 'yeah', country: country)
                    club.addToPlayers(player1)
                    club.addToPlayers(player2)
                    club.addToPlayers(player3)
                    league.addToClubs(club)
                }
                league.save(failOnError: true)
                league.champion = league.clubs.first()
                league.save(failOnError: true)
            }

            Club barcelona = Club.findByName("FC Barcelona")
            barcelona.rival = Club.findByName("Real Madrid CF")
            barcelona.save(failOnError: true)
        }

//		String query = "MATCH (n:League) WHERE n.name = {1} RETURN n"
//		League spaLeague = League.find(query, ["Spanish Liga"])
//		log.debug("Fetched league: ${spaLeague.name}")
    }

    private void destroyUEFA() {
        Club.where {}.deleteAll()
    }

    def tags = ["UEFA"]

    def uefa = ["English Premier League": ["Chelsea FC", "Arsenal FC", "Manchester City FC", "Manchester United FC", "Tottenham Hotspur FC", "Liverpool FC", "Newcastle United FC", "Everton FC", "Stoke City FC", "Swansea City AFC", "Birmingham City FC", "Fulham FC", "Wigan Athletic FC", "Southampton FC", "Hull City AFC", "West Ham United FC"],
                "Spanish Liga"          : ["Real Madrid CF", "FC Barcelona", "Club Atlético de Madrid", "Valencia CF", "Sevilla FC", "Athletic Club", "Málaga CF", "Villarreal CF", "Levante UD", "Real Betis Balompié", "Real Sociedad de Fútbol"],
                "French Ligue 1"        : ["Paris Saint-Germain", "Olympique Lyonnais", "Olympique de Marseille", "AS Monaco FC", "LOSC Lille", "FC Girondins de Bordeaux", "AS Saint-Étienne", "EA Guingamp", "Montpellier Hérault SC", "Stade Rennais FC", "OGC Nice", "FC Sochaux-Montbéliard"],
                "German Bundesliga"     : ["FC Bayern München", "Borussia Dortmund", "FC Schalke 04", "Bayer 04 Leverkusen", "Hannover 96", "VfL Wolfsburg", "VfL Borussia Mönchengladbach", "Eintracht Frankfurt", "VfB Stuttgart", "SC Freiburg", "FC Augsburg", "1. FSV Mainz 05"]]
}