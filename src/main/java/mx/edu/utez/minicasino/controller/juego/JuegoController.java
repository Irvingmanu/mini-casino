package mx.edu.utez.minicasino.controller.juego;

import jakarta.validation.Valid;
import mx.edu.utez.minicasino.dto.RequestCrearJugadorDto;
import mx.edu.utez.minicasino.dto.RequestIniciarPartidaDto;
import mx.edu.utez.minicasino.dto.RequestPartidaIdDto;
import mx.edu.utez.minicasino.dto.RequestRecargarSaldoDto;
import mx.edu.utez.minicasino.model.jugador.Jugador;
import mx.edu.utez.minicasino.model.partida.Partida;
import mx.edu.utez.minicasino.model.tiro.Tiro;
import mx.edu.utez.minicasino.service.juego.JuegoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/juego")
@CrossOrigin("*")
public class JuegoController {

    private final JuegoService juegoService;

    public JuegoController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PostMapping("/jugador")
    public ResponseEntity<Jugador> crearJugador(@RequestBody @Valid RequestCrearJugadorDto payload) {
        return ResponseEntity.status(201).body(this.juegoService.crearJugador(payload));
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Partida> iniciarPartida(@RequestBody @Valid RequestIniciarPartidaDto payload) {
        return ResponseEntity.status(201).body(this.juegoService.iniciarPartida(payload));
    }

    @PostMapping("/tiro")
    public ResponseEntity<Tiro> realizarTiro(@RequestBody @Valid RequestPartidaIdDto payload) {

        return ResponseEntity.status(201).body(this.juegoService.realizarTiro(payload));
    }

    @PutMapping("/finalizar")
    public ResponseEntity<Partida> finalizarPartidaManual(@RequestBody @Valid RequestPartidaIdDto payload) {
        return ResponseEntity.status(200).body(this.juegoService.finalizarPartidaManual(payload));
    }

    @PutMapping("/recargar")
    public ResponseEntity<Jugador> recargarSaldo(@RequestBody @Valid RequestRecargarSaldoDto payload) {
        return ResponseEntity.status(200).body(this.juegoService.recargarSaldo(payload));
    }


    @GetMapping("/historial/{jugadorId}")
    public ResponseEntity<List<Partida>> obtenerHistorialPartida(@PathVariable Long jugadorId) {
        return ResponseEntity.status(200).body(this.juegoService.obtenerHistorialPartida(jugadorId));
    }
}