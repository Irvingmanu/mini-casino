package mx.edu.utez.minicasino.model.jugador;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "jugadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double saldo;
    private boolean activo;
}