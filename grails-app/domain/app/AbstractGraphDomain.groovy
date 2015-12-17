package app

import org.grails.datastore.gorm.neo4j.GraphPersistentEntity

abstract class AbstractGraphDomain extends AbstractBaseDomain {

    static mapWith = "neo4j"

    static mapping = {
        dynamicAssociations true
        labels { GraphPersistentEntity pe, instance ->
            "`aaaa`"
        }
    }
}
