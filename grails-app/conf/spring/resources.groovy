import app.GormEventsListener
import app.SessionListener

// Place your Spring DSL code here
beans = {
    sessionListener(SessionListener)

    gormEventsListener(GormEventsListener)
}
