package app

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.datastore.mapping.core.Datastore
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEventListener
import org.grails.datastore.mapping.engine.event.EventType
import org.grails.datastore.mapping.engine.event.PostInsertEvent
import org.grails.datastore.mapping.engine.event.PostUpdateEvent
import org.grails.datastore.mapping.engine.event.PreInsertEvent

import org.springframework.context.ApplicationEvent

import grails.util.Holders

class OtherListener extends AbstractPersistenceEventListener {

    private static final Log log = LogFactory.getLog(OtherListener)

    protected OtherListener(Datastore datastore) {
        super(datastore)
    }

    @Override
    protected void onPersistenceEvent(AbstractPersistenceEvent event) {
        log.debug("[on Event ${event.eventType.name()}]")
//        if (event.eventType == EventType.PostInsert) {
//            log.debug("[on Event ${event.eventType.name()}] Creating counter...")
//            uefaService.createCounter()
//            log.debug("[on Event ${event.eventType.name()}] counter created.")
//        } else
        if ((event.eventType == EventType.PostInsert || event.eventType == EventType.PostUpdate) && !(event.entityObject instanceof Counter)) {
            log.debug("[on Event ${event.eventType.name()}] Creating counter for ${event.entity.name}...")
            Counter.withNewSession {
                Counter.withTransaction {
                    Counter counter = new Counter()
                    counter.name = "PreInsert ${event.entity.name}"
                    counter.count = new Date().time
                    counter.save()
                }
            }
        }
    }

    @Override
    boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return PostInsertEvent.isAssignableFrom(aClass) || PreInsertEvent.isAssignableFrom(aClass) || PostUpdateEvent.isAssignableFrom(aClass)
    }

    private static UefaService getUefaService() {
        return Holders.applicationContext.getBean("uefaService")
    }
}
