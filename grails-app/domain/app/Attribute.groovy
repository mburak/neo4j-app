package app

class Attribute {

    String name
    String description

    static constraints = {
        description nullable: true
    }
}
