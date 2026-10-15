package mx.edu.utez.minicasino.repository.tiro;

import mx.edu.utez.minicasino.model.tiro.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiroRepository extends JpaRepository<Tiro, Long> {
}