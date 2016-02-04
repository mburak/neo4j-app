package app

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.grails.datastore.gorm.neo4j.SessionFlushedEvent

import org.springframework.context.ApplicationListener

class SessionListener implements ApplicationListener<SessionFlushedEvent> {

    private static final Log log = LogFactory.getLog(SessionListener)

    @Override
    void onApplicationEvent(SessionFlushedEvent event) {
        log.debug("Session flushed")
    }
}
