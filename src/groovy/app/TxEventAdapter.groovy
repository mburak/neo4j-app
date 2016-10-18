package app

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.transaction.support.TransactionSynchronizationAdapter

class TxEventAdapter extends TransactionSynchronizationAdapter {

    private static final Logger log = LoggerFactory.getLogger(TxEventAdapter)

    @Override
    public void afterCommit() {
        log.debug("running after commit")
    }

}
