package BaseUtil.cucumber.runner;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import cucumber.api.event.Event;
import cucumber.api.event.TestRunFinished;

public class CanonicalOrderEventPublisher extends AbstractEventPublisher {

    private final List<Event> queue = new LinkedList<>();

    public void handle(final Event event) {
        queue.add(event);
        if (event instanceof TestRunFinished) {
            Collections.sort(queue, Event.CANONICAL_ORDER);
            sendAll(queue);
            queue.clear();
        }
    }

}
