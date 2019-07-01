package software.jevera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import software.jevera.domain.Position;
import software.jevera.domain.ProfessionalityLevel;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query(value = "SELECT e FROM Position e")
    List<Position> findAll();
    Optional<Position> findById(Long id);
    Position save(Position position);
    Position findByNameAndProfessionalLevel(String name, ProfessionalityLevel professionalityLevel);
}
