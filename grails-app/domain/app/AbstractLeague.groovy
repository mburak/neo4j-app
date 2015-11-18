package app

abstract class AbstractLeague extends AbstractGraphDomain {

    static constraints = {
    }

    static mapping = {
        labels { pe, instance -> "`ppp`"}
    }
}
