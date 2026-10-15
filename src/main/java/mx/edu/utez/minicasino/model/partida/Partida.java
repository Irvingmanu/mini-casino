package mx.edu.utez.minicasino.model.partida;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.minicasino.model.jugador.Jugador;
import java.util.Date;

@Entity
@Table(name = "partidas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Enumerated(EnumType.STRING)
    private EstadoPartida estado;

    @ManyToOne
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    private double apuesta;
}