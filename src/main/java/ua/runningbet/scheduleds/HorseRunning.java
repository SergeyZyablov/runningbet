package ua.runningbet.scheduleds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ua.runningbet.models.Event;
import ua.runningbet.models.Slot;
import ua.runningbet.models.User;
import ua.runningbet.repositpries.EventRepository;
import ua.runningbet.repositpries.SlotRepository;
import ua.runningbet.repositpries.StatusRepository;
import ua.runningbet.repositpries.UserRepository;

@Component
public class HorseRunning {
	@Autowired
	private StatusRepository statusRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private SlotRepository slotRepository;
	@Autowired
	private UserRepository userRepository;

	@Scheduled(fixedRate = 60000)
	public void runningResult() {

		if (eventRepository.existsByStatusName("LIVE")) {
			Event event = eventRepository.findOneByStatusName("LIVE");
			event.setStatus(statusRepository.findByName("FINISHED"));
			List<Slot> slots = new ArrayList<>();
			List<Slot> userSlots = event.getSlots();

			for (int j = 0; j < userSlots.size() * 2; j++) {
				for (int i = 0; i < userSlots.size(); i++) {
					if (userSlots.get(i).getBet() == null) {
						slots.add(userSlots.get(i));
						userSlots.remove(userSlots.get(i));
					}
				}
			}

			List<Integer> nums = new ArrayList<>();
			while (nums.size() < slots.size()) {
				int rnd = new Random().nextInt(slots.size());
				rnd++;
				if (!nums.contains(rnd)) {
					nums.add(rnd);
				}
			}
			for (int i = 0; i < slots.size(); i++) {
				slots.get(i).setStatus(nums.get(i).toString());
				slotRepository.save(slots.get(i));
			}
			for (int i = 0; i < nums.size(); i++) {
				Slot slot = slots.get(i);
				for (int j = 0; j < userSlots.size(); j++) {
					if (slot.getHorse().getName().equals(userSlots.get(j).getHorse().getName())) {
						List<User> users = userSlots.get(j).getUsers();
						Float coefficient = userSlots.get(j).getCoefficient();
						Float bet = userSlots.get(j).getBet();
						if (userSlots.get(j).getPosition() == nums.get(i)) {
							userSlots.get(j).setStatus("WIN");
							users.stream().forEach(e -> e.setMony(e.getMony() + (bet * coefficient)));
							users.stream().forEach(e -> e.setWins(e.getWins() + 1));
						} else {
							userSlots.get(j).setStatus("LOOS");
							/* users.stream().forEach(e -> e.setMony(e.getMony() - bet)); */
						}
						users.stream().forEach(e -> userRepository.save(e));
						slotRepository.save(userSlots.get(j));
						eventRepository.save(event);

					}
				}
			}
		}

	}
}
