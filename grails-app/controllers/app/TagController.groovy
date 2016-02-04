package app

import java.util.concurrent.atomic.AtomicInteger

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE
import static org.springframework.http.HttpStatus.NOT_FOUND

class TagController {

    AtomicInteger saveCount = new AtomicInteger()

    static responseFormats = ["json"]

    static allowedMethods = [save: "POST", update: "PUT", patch: "PATCH", delete: "DELETE"]

    def index(Integer max) {
        //params.max = Math.min(max ?: 50, 100)
        respond Tag.list(params), model: [count: Tag.count()]
    }

//    def count(Integer max) {
//        respond [count: Tag.count()]
//    }

    def show(Tag tag) {
        respond tag
    }

    @Transactional
    def save() {
        def instance = createResource()
        instance.save flush: true, validate: false

        String msg = "${saveCount.incrementAndGet()} - Saving tag ${instance.id}"
        log.debug(msg)

        respond instance, [status: CREATED]

    }

    @Transactional
    def update(Tag tag) {
        if (tag == null) {
            render status: NOT_FOUND
            return
        }

        tag.validate()
        if (tag.hasErrors()) {
            render status: NOT_ACCEPTABLE
            return
        }

        tag.save flush: true, validate: false
        respond tag, [status: CREATED]
    }

    protected Tag createResource() {
        Tag instance = Tag.newInstance()
        bindData instance, getObjectToBind()
        instance
    }

    protected getObjectToBind() {
        request
    }
}
