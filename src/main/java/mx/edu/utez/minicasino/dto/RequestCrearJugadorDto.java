package mx.edu.utez.minicasino.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCrearJugadorDto {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El saldo es obligatorio")
    @Min(value = 0, message = "El saldo no puede ser negativo")
    private Double saldo;
}