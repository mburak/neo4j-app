package app

import org.springframework.validation.Errors

import grails.transaction.Transactional
import grails.validation.ValidationException

@Transactional
public class UefaService {

    public initialize() {
        log.debug "initializing UEFA..."
    }

    public dummy() {
        log.debug "Dummy service..."
    }

    public createCounter() {
        Counter counter = new Counter()
        counter.count = new Date().time
        counter.save()
    }

    public createLeague(String name, Collection ids) {
        League league = new League()
        league.name = name
        league.otherProp = "Test"
        Tag t = Tag.get(1550769598749392896)
        league.tags = []
        league.tags << t

        league.tagDefCount = league.tags?.first()?.dynamicDefinition?.aTag

        league.save(validate: false)
    }

    public Club saveClub(Club club) {
        if (club?.validate()) {
            return club.save(validate: false)
        }
        else {
            log.error("ERROR: Error saving club: ${club}")
            throw new ValidationException('invalid club', (Errors) club.errors)
        }
    }
}
