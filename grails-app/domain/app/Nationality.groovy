package app

abstract class Nationality implements Serializable {

    static mapWith = "neo4j"

    String name

    static constraints = {
    }
}
