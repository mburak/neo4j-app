import app.Club
import app.Counter
import app.Country
import app.League
import app.NativeNationality
import app.Player
import app.Tag
import app.TagDefinition
import app.TxEventAdapter

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

import org.springframework.transaction.support.TransactionSynchronizationAdapter
import org.springframework.transaction.support.TransactionSynchronizationManager

//import org.neo4j.graphdb.event.TransactionData
//import org.neo4j.graphdb.event.TransactionEventHandler

class BootStrap {

    def graphDatabaseService

    AtomicInteger befCmCount = new AtomicInteger()
    AtomicInteger aftCmCount = new AtomicInteger()
    AtomicInteger aftRbCount = new AtomicInteger()

    def uefaService

    def init = { servletContext ->
        uefaService.initialize()
        Club.withTransaction({
            initUEFA()
            int totalClubs = uefa.collect { it.value.size() }.sum()
            log.debug("UEFA initialized with ${League.count()} leagues and ${Club.count()} clubs")
            assert Club.count() == totalClubs
        })

//        graphDatabaseService.registerTransactionEventHandler(
//            new TransactionEventHandler<Void>() {
//
//                @Override
//                public Void beforeCommit(TransactionData data) throws Exception {
//                    log.info("${befCmCount.incrementAndGet()} - Committing transaction.")
//                    return null
//                }
//
//                @Override
//                public void afterCommit(TransactionData data, Void state) {
//                    log.info("${aftCmCount.incrementAndGet()} - Committed with transaction")
//                }
//
//                @Override
//                public void afterRollback(TransactionData data, Void state) {
//                    log.info("${aftRbCount.incrementAndGet()} - Transaction rolled back")
//                }
//            })
    }

    def destroy = {
        destroyUEFA()
    }

    private void initUEFA() {
        def tagIds = []
        if (Tag.count() == 0) {
            tags.each { String tag ->
                Counter counter = new Counter(count: 5).save()
                TagDefinition tagDef = new TagDefinition(definition: "def", counter: counter)
                tagDef.otherCount = counter
                tagDef.save()
                Tag t = new Tag(name: tag, defintion: tagDef)
                t.dynamicDefinition = tagDef
                t.save(flush: true)
                tagIds << t.id
            }
            Tag.findByName("UEFA").dynamicDefinition.aTag = new Tag(name: "MASTER_TAG").save()

        }

        Country country = null
        if (Country.count() == 0) {
            country = new Country(name: 'Argentina').save()
        }

        if (League.count() == 0) {
            uefa.each() { entry ->
                League league = new League(name: entry.key).addToTags(Tag.findByName("UEFA"))
                League.withTransaction {
                    List<String> clubs = entry.value
                    clubs?.eachWithIndex{ String name, int i ->
                        Player player1 = new Player(name: "Messi", nationality: new NativeNationality(name: 'ARG'))
                        Player player2 = new Player(name: "Ronaldo", nationality: new NativeNationality(name: 'POR'))
                        Player player3 = new Player(name: "Neymar")
                        Club club = new Club(name: name, big: 'yeah', country: country)
                        if (i % 2 == 0) {
                            club.captain = player1
                        }
                        else {
                            club.captain = player2
                        }
                        club.addToPlayers(player1)
                        club.addToPlayers(player2)
                        club.addToPlayers(player3)

                        club.captains = [player1, player2]
                        league.addToClubs(club)
                    }
                    league.save(flush: true, failOnError: true)
                }
                league.discard()
                League otherLeage = League.get(league.id)
                assert otherLeage != null
                log.debug("otherLeague found in db: ${league}")

                TransactionSynchronizationManager.registerSynchronization(new TxEventAdapter())

//                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {

//                    @Override
//                    public void afterCommit() {
//                        log.debug("running after commit")
//                        new GetNode(league.id, league.class).run()
//                        Executor executor = Executors.newFixedThreadPool(1)
//                        executor.execute(new GetNode(league.id, league.class))
//                    }
//                })

                //league.champion = league.clubs.first()
                //league.save(failOnError: true)
            }

            Club barcelona = Club.findByName("FC Barcelona")

            barcelona.rival = Club.findByName("Real Madrid CF")
            barcelona.captains.clear()
            barcelona.save(failOnError: true)
            barcelona.discard()
            Club nonCachedBarcelona = Club.findByName("FC Barcelona")
            //assert nonCachedBarcelona.rival == Club.findByName("Real Madrid CF")
        }
        else {
            Club barcelona = Club.findByName("FC Barcelona")
            barcelona.country = Country.findByName("Argentina")
            barcelona.save()
        }

//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
//
//            @Override
//            public void afterCommit() {
//                uefaService.createLeague("Liga de Argentina", tagIds)
//            }
//        })


//		String query = "MATCH (n:League) WHERE n.name = {1} RETURN n"
//		League spaLeague = League.find(query, ["Spanish Liga"])
//		log.debug("Fetched league: ${spaLeague.name}")
    }

    private void destroyUEFA() {
        Club.where {}.deleteAll()
    }

    def tags = ["UEFA", "MORE", "SOCCER"]

    def uefa = ["English Premier League": ["Chelsea FC", "Arsenal FC", "Manchester City FC", "Manchester United FC", "Tottenham Hotspur FC", "Liverpool FC", "Newcastle United FC", "Everton FC", "Stoke City FC", "Swansea City AFC", "Birmingham City FC", "Fulham FC", "Wigan Athletic FC", "Southampton FC", "Hull City AFC", "West Ham United FC"],
                "Spanish Liga"          : ["Real Madrid CF", "FC Barcelona", "Club Atlético de Madrid", "Valencia CF", "Sevilla FC", "Athletic Club", "Málaga CF", "Villarreal CF", "Levante UD", "Real Betis Balompié", "Real Sociedad de Fútbol"],
                "French Ligue 1"        : ["Paris Saint-Germain", "Olympique Lyonnais", "Olympique de Marseille", "AS Monaco FC", "LOSC Lille", "FC Girondins de Bordeaux", "AS Saint-Étienne", "EA Guingamp", "Montpellier Hérault SC", "Stade Rennais FC", "OGC Nice", "FC Sochaux-Montbéliard"],
                "German Bundesliga"     : ["FC Bayern München", "Borussia Dortmund", "FC Schalke 04", "Bayer 04 Leverkusen", "Hannover 96", "VfL Wolfsburg", "VfL Borussia Mönchengladbach", "Eintracht Frankfurt", "VfB Stuttgart", "SC Freiburg", "FC Augsburg", "1. FSV Mainz 05"]]
}

public class GetNode implements Runnable {

    private Long id
    private Class clazz

    public GetNode(Long id, Class clazz) {
        this.id = id
        this.clazz = clazz
    }

    public void run() {
        Club.withTransaction(readOnly: true) {
            log.debug("Looking node with ${id} of class ${clazz}")
            def nodeById = clazz.get(this.id)
            assert nodeById != null
            log.debug("Found node with ${id} of class ${clazz}")
        }
    }
}

