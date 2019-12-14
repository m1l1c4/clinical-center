package tim31.pswisa.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim31.pswisa.dto.RoomDTO;
import tim31.pswisa.model.Clinic;
import tim31.pswisa.model.Room;
import tim31.pswisa.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;

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
	

}
