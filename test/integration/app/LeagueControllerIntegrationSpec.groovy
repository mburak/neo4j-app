package app

import org.springframework.beans.factory.annotation.Autowired

import grails.test.spock.IntegrationSpec

class LeagueControllerIntegrationSpec extends IntegrationSpec {

    @Autowired
    LeagueController controller

    def setup() {
        League league = new League(name: 'Liga 1111')
        league.save(flush: true)

        League league2 = League.build()
    }

    void "test something"() {
        when:
        controller.index(10)

        then:
        true
    }

    void "test something2"() {
        when:
        controller.index(10)

        then:
        true
    }

    void "test something3"() {
        when:
        controller.throwEx()

        then:
        true
    }

    void "test something4"() {
        when:
        controller.index(10)

        then:
        true
    }

    void "test something5"() {
        when:
        controller.index(10)

        then:
        true
    }

    void "test something6"() {
        when:
        controller.index(10)

        then:
        true
    }
    void "test something7"() {
        when:
        controller.index(10)

        then:
        true
    }
    void "test something8"() {
        when:
        controller.index(10)

        then:
        true
    }

}
