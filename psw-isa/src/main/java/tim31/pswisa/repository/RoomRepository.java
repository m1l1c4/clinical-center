package tim31.pswisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim31.pswisa.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	List<Room> findAll();

	List<Room> findAllByClinicId(Long id);

	Room findOneById(Long id);
}
