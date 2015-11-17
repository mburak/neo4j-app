package app

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
        strNationality nullable: false
    }

}
