package app

import org.grails.datastore.gorm.neo4j.GraphPersistentEntity

class League extends AbstractLeague {

    String name

    Date dateCreated
    Date lastUpdated

    List<Club> clubs = []

    static hasMany = [clubs: Club, tags: Tag]

    static constraints = {
        name blank: false//, unique: true
    }

    //static mapping = {
    //	tags fetch: "eager"
    //}
    static mapping = {
        labels { GraphPersistentEntity pe, instance ->
            "`${instance?.tags?.findAll()?.collect { it.name }?.join(',')}`"
        }
    }
}