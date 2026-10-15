package mx.edu.utez.minicasino.repository.partida;

import mx.edu.utez.minicasino.model.jugador.Jugador;
import mx.edu.utez.minicasino.model.partida.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByJugador(Jugador jugador);
}