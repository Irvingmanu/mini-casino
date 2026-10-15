package mx.edu.utez.minicasino.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPartidaIdDto {

    @NotNull(message = "El id de la partida es obligatorio")
    private Long partidaId;
}