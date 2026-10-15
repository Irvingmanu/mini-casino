package mx.edu.utez.minicasino.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestRecargarSaldoDto {

    @NotNull(message = "El id del jugador es obligatorio")
    private Long jugadorId;

    @NotNull(message = "El monto es obligatorio")
    @Min(value = 1, message = "El monto a recargar debe ser mayor a 0")
    private Double monto;
}