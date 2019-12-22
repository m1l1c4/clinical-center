package tim31.pswisa.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.model.Checkup;
import tim31.pswisa.model.Room;
import tim31.pswisa.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private CheckUpService checkUpService;

	public List<Room> findAll() {
		return roomRepository.findAll();
	}

	public List<Room> findAllByClinicId(Long id) {
		return roomRepository.findAllByClinicId(id);
	}

	public Room findOneById(Long id) {
		return roomRepository.findOneById(id);
	}

	public Room save(Room room) {
		return roomRepository.save(room);
	}

	public Room update(Room ct) {
		return roomRepository.save(ct);
	}

	public Room findOneByClinicAndNumber(Long clinicId, int number) {
		return roomRepository.findOneByClinicIdAndNumber(clinicId, number);
	}

	public List<Room> findAllByClinicIdAndTypeRoom(Long id, String type, String d) {
		List<Room> rooms = roomRepository.findAllByClinicIdAndTypeRoom(id, type);
		List<Room> ret = new ArrayList<>();
		for (Room room : rooms) {
			LocalDate date = LocalDate.parse(d);
			boolean found = false;
			while (!found) {
				List<Checkup> checkups = checkUpService.findAllByRoomIdAndScheduledAndDate(room.getId(), true, date);
				if (checkups == null || checkups.size() < 13) {
					found = true;
					room.setFirstFreeDate(date);
					ret.add(room);
				}
				date = date.plusDays(1);
			}
		}
		return ret;
	}

	public ArrayList<Integer> findRoomAvailability(Long id, String date) {
		List<Checkup> checkups = checkUpService.findAllByRoomIdAndScheduledAndDate(id, true, LocalDate.parse(date));
		ArrayList<Integer> ret = new ArrayList<>();
		ArrayList<Integer> temp = new ArrayList<>();
		for (Checkup checkup : checkups) {
			temp.add(Integer.parseInt(checkup.getTime()));
		}
		for (int i = 8; i < 21; i++) {
			if (!temp.contains(i)) {
				ret.add(i);
			}
		}
		return ret;
	}
}
