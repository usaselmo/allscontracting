package com.allscontracting.model;

import static com.allscontracting.event.Event.SEND_ESTIMATE;
import static com.allscontracting.event.Event.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.allscontracting.event.Event;
import com.allscontracting.event.State;

public class EventDispatcher {

	List<Event> getNextEvents(State state) {

		Event[] ESTIMATE_SCHEDULED = { SEND_ESTIMATE, WASTE_LEAD, END_LEAD };
		Event[] VISITED = { SEND_ESTIMATE, WASTE_LEAD, END_LEAD };
		Event[] ESTIMATE_SENT = { BEGIN_WORK, WASTE_LEAD, END_LEAD };
		Event[] WORK_IN_PROGRESS = { FINISH_WORK, END_LEAD };
		Event[] WORK_DONE = { SEND_INVOICE, END_LEAD };
		Event[] INVOICE_SENT = { Event.RECEIVE_PAYMENT, END_LEAD };
		Event[] PAYED = { Event.END_LEAD };
		Event[] DONE = {};

		switch (state) {
		case ESTIMATE_SCHEDULED:
			return Arrays.asList(ESTIMATE_SCHEDULED);
		case VISITED:
			return Arrays.asList(VISITED);
		case ESTIMATE_SENT:
			return Arrays.asList(ESTIMATE_SENT);
		case WORK_IN_PROGRESS:
			return Arrays.asList(WORK_IN_PROGRESS);
		case WORK_DONE:
			return Arrays.asList(WORK_DONE);
		case INVOICE_SENT:
			return Arrays.asList(INVOICE_SENT);
		case PAYED:
			return Arrays.asList(PAYED);
		case DONE:
			return Arrays.asList(DONE);
		default:
			return Collections.emptyList();
		}
	}

}
