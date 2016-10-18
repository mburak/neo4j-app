package app

class Tag {

    static mapWith = "neo4j"

    String name
    TagDefinition defintion

    static constraints = {
        name blank: false, unique: true
        defintion nullable: true
    }

    static mapping = {
        dynamicAssociations true
    }

}
