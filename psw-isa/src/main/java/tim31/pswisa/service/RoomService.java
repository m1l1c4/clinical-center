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

	/**
	 * This method servers for getting all room from database
	 * 
	 * @return - (List<Room>) This method returns all rooms
	 */
	public List<Room> findAll() {
		return roomRepository.findAll();
	}

	/**
	 * This method servers for getting all rooms in clinic
	 * 
	 * @param id - id of clinic
	 * @return - (List<Room>) This method returns all rooms in clinic
	 */
	public List<Room> findAllByClinicId(Long id) {
		return roomRepository.findAllByClinicId(id);
	}

	/**
	 * This method servers for getting one room by id
	 * 
	 * @param id - id of room
	 * @return - (Room) This method returns one room
	 */
	public Room findOneById(Long id) {
		return roomRepository.findOneById(id);
	}

	/**
	 * This method servers for saving room in clinic
	 * 
	 * @param room - room that has to be saved
	 * @return - (Room) This method returns saved room
	 */
	public Room save(Room room) {
		return roomRepository.save(room);
	}

	/**
	 * This method servers for updating room in clinic
	 * 
	 * @param room - room that has to be updated
	 * @return - (Room) This method returns updated room
	 */
	public Room update(Room ct) {
		return roomRepository.save(ct);
	}

	/**
	 * This method servers for saving room by clinic and room number
	 * 
	 * @param clinicId - id of clinic where room is
	 * @param number   - the number of room
	 * @return - (Room) This method returns searched room
	 */
	public Room findOneByClinicAndNumber(Long clinicId, int number) {
		return roomRepository.findOneByClinicIdAndNumber(clinicId, number);
	}

}
