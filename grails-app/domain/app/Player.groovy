package app

import org.grails.datastore.gorm.neo4j.GraphPersistentEntity

class Player extends AbstractGraphDomain {

    String name
    String strNationality
    Nationality nationality

    static transients = ["nationality"]

    static belongsTo = [club: Club]

    public Nationality getNationality() {
        return new NativeNationality(name: strNationality)
    }

    public void setNationality(Nationality nationality) {
        this.strNationality = nationality.name
    }

    static constraints = {
        strNationality nullable: true
    }

    static mapping = {
        labels { GraphPersistentEntity pe, Player instance ->
            if (instance.club) {
                "`club_${instance.club.name}`"
            }
        }
    }
}
