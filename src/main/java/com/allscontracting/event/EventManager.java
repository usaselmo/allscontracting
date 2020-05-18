package com.allscontracting.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {
	
  Map<Event, List<EventListener>> listeners = new HashMap<>();

  public EventManager(Event... events) {
  	for (Event event : events) {
  		this.listeners.put(event, new ArrayList<>());
		}
  }

  public void subscribe(Event event, EventListener listener) {
      List<EventListener> users = listeners.get(event);
      users.add(listener);
  }

  public void unsubscribe(Event event, EventListener listener) {
      List<EventListener> users = listeners.get(event);
      users.remove(listener);
  }

  public void notifyAll(Event event, Object arg) {
      List<EventListener> users = listeners.get(event);
      for (EventListener listener : users) {
          listener.listen(event, arg);
      }
  }
}
