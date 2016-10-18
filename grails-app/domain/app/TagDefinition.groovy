package app

class TagDefinition {

    static mapWith = "neo4j"

    String definition
    Counter counter

    static mapping = {
        dynamicAssociations true
    }

}
