package app

import org.springframework.beans.factory.annotation.Autowired

import grails.test.spock.IntegrationSpec

class ClubControllerIntegrationSpec extends IntegrationSpec {

    @Autowired
    ClubController controller

    def setup() {
        Club club = new Club(name: 'River Plate')
        club.save(flush: true)

        Club club2 = Club.build()
    }

    void "test something"() {
        when:
        controller.index(10)

        then:
        true
    }
}
