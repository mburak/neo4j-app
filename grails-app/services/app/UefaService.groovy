package app

public class UefaService {

    static transactional = true

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
}