package app

class Player extends AbstractGraphDomain {

    String name
    String strNationality
    Nationality nationality

    static transients = ["nationality"]

    public Nationality getNationality() {
        return new NativeNationality(name: strNationality)
    }

    public void setNationality(Nationality nationality) {
        this.strNationality = nationality.name
    }

}
