package ua.runningbet.scheduleds;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.runningbet.models.Event;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.StatusRepository;

@Component
public class ScheduledEvent {
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private StatusRepository statusRepository;

	@Scheduled(fixedRate = 1000)
	public void reportCurrentTime() {
		List<Event> events = eventRepository.findAll();
		for (int i = 0; i < events.size(); i++) {
			long nowTime = convertDate(new Date());
			Date nowDate = new Date(nowTime);
			long eventTime = convertDate(events.get(i).getStartDate());
			Date eventDate = new Date(eventTime);
			if (events.get(i).getStatus().getName() != "FINISHED") {
				if (eventDate.equals(nowDate)) {
					events.get(i).setStatus(statusRepository.findByName("LIVE"));
				} else if (eventDate.after(nowDate)) {
					events.get(i).setStatus(statusRepository.findByName("FUTURE"));
				}
				if (events.get(i).getSlots().isEmpty()) {
					events.get(i).setStatus(statusRepository.findByName("NOT READY"));
				}
			}
			eventRepository.save(events.get(i));
		}
	}

	private long convertDate(Date date) {
		Calendar cal = Calendar.getInstance(); // locale-specific
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long time = cal.getTimeInMillis();
		return time;

	}
}