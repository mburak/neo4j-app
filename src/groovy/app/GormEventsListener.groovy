package app

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.PostDeleteEvent
import org.grails.datastore.mapping.engine.event.PostInsertEvent
import org.grails.datastore.mapping.engine.event.PostUpdateEvent
import org.grails.datastore.mapping.engine.event.PreDeleteEvent
import org.grails.datastore.mapping.engine.event.PreInsertEvent
import org.grails.datastore.mapping.engine.event.PreUpdateEvent

import groovy.transform.CompileStatic

import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener

@CompileStatic
class GormEventsListener implements ApplicationListener<ApplicationEvent> {

    private static final Log log = LogFactory.getLog(GormEventsListener)

    final Map<Class, String> translateTable = [
        (PreInsertEvent) : 'beforeInsert', (PreUpdateEvent): 'beforeUpdate', /*(PreLoadEvent): 'beforeLoad',*/
        (PreDeleteEvent) : 'beforeDelete'/*,(ValidationEvent): 'beforeValidate'*/, (PostInsertEvent): 'afterInsert',
        (PostUpdateEvent): 'afterUpdate', (PostDeleteEvent): 'afterDelete'/*, (PostLoadEvent): 'afterLoad',
        (SaveOrUpdateEvent): 'onSaveOrUpdate'*/
    ]

    @Override
    void onApplicationEvent(final ApplicationEvent applicationEvent) {

        String topic = translateTable[applicationEvent.class]
        if (topic) {
            AbstractPersistenceEvent persistenceEvent = ((AbstractPersistenceEvent) applicationEvent)
            def entity = persistenceEvent.entityObject ?: persistenceEvent.entityAccess?.entity

            if (log.isDebugEnabled()) {
                log.debug("Listening to GORM event: topic = ${topic}, entity = ${entity}")
            }

        }

    }

}
