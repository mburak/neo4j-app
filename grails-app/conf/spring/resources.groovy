import app.GormEventsListener
import app.OtherListener
import app.SessionListener
import app.TxEventAdapter

// Place your Spring DSL code here
beans = {
    sessionListener(SessionListener)

    gormEventsListener(GormEventsListener)
    otherListener(OtherListener, ref("neo4jDatastore"))

    txEventAdapter(TxEventAdapter)
}
