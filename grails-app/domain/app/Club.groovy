package app

import org.grails.datastore.gorm.neo4j.GraphPersistentEntity

import grails.persistence.Entity

@Entity
class Club extends AbstractGraphDomain {

    //static mapWith = "neo4j"

    String name
    Date dateCreated
    Date lastUpdated
    String big
    Country country
    String address

    static belongsTo = [league: League]

    static hasMany = [players   : Player,
                      attributes: Attribute]

    static constraints = {
        name blank: false, unique: true
        country nullable: true
        attributes nullable: true
        address nullabel: true
    }

    static transients = ['big']

    public transient String getBig() {
        return 'bigtext' + name
    }

    public transient void setBig(String big) {
        this.big = big
    }

    static mapping = {
        dynamicAssociations true
        labels '__Club__'
    }
//    static mapping = {
//        labels { GraphPersistentEntity pe, Club instance ->
//            if (instance.country) {
//                "`country_${instance.country.name}`"
//            }
//        }
//    }
}