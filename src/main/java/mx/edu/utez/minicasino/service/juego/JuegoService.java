package mx.edu.utez.minicasino.service.juego;

import mx.edu.utez.minicasino.dto.RequestCrearJugadorDto;
import mx.edu.utez.minicasino.dto.RequestIniciarPartidaDto;
import mx.edu.utez.minicasino.dto.RequestPartidaIdDto;
import mx.edu.utez.minicasino.dto.RequestRecargarSaldoDto;
import mx.edu.utez.minicasino.exception.customExceptions.BadRequestException;
import mx.edu.utez.minicasino.model.jugador.Jugador;
import mx.edu.utez.minicasino.model.partida.EstadoPartida;
import mx.edu.utez.minicasino.model.partida.Partida;
import mx.edu.utez.minicasino.model.tiro.Tiro;
import mx.edu.utez.minicasino.repository.jugador.JugadorRepository;
import mx.edu.utez.minicasino.repository.partida.PartidaRepository;
import mx.edu.utez.minicasino.repository.tiro.TiroRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class JuegoService {

    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private final TiroRepository tiroRepository;

    public JuegoService(JugadorRepository jugadorRepository, PartidaRepository partidaRepository, TiroRepository tiroRepository) {
        this.jugadorRepository = jugadorRepository;
        this.partidaRepository = partidaRepository;
        this.tiroRepository = tiroRepository;
    }

    public Jugador crearJugador(RequestCrearJugadorDto payload) {
        Jugador nuevo = new Jugador();
        nuevo.setNombre(payload.getNombre());
        nuevo.setSaldo(payload.getSaldo());
        nuevo.setActivo(true);
        return jugadorRepository.save(nuevo);
    }

    public Partida iniciarPartida(RequestIniciarPartidaDto payload) {
        Jugador jugador = jugadorRepository.findById(payload.getJugadorId())
                .orElseThrow(() -> new BadRequestException("El jugador no existe"));

        if (!jugador.isActivo()) {
            throw new BadRequestException("El jugador no esta activo");
        }

        if (jugador.getSaldo() < payload.getApuesta()) {
            throw new BadRequestException("Saldo insuficiente");
        }

        jugador.setSaldo(jugador.getSaldo() - payload.getApuesta());
        jugadorRepository.save(jugador);

        Partida partida = new Partida();
        partida.setFecha(new Date());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        partida.setJugador(jugador);
        partida.setApuesta(payload.getApuesta());

        return partidaRepository.save(partida);
    }

    public Tiro realizarTiro(RequestPartidaIdDto payload) {
        Partida partida = partidaRepository.findById(payload.getPartidaId()).
                orElseThrow(() -> new BadRequestException("La partida no existe"));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new BadRequestException("La partida no esta en juego");
        }

        Random random = new Random();
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;
        boolean esGanador = false;

        if (suma == 7 || suma == 11) {
            esGanador = true;
            partida.setEstado(EstadoPartida.FINALIZADA);
            Jugador jugador = partida.getJugador();
            jugador.setSaldo(jugador.getSaldo() + (partida.getApuesta() * 2));
            jugadorRepository.save(jugador);
            partidaRepository.save(partida);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);
        }

        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(esGanador);

        return tiroRepository.save(tiro);
    }

    public Partida finalizarPartidaManual(RequestPartidaIdDto payload) {
        Partida partida = partidaRepository.findById(payload.getPartidaId()).
                orElseThrow(() -> new BadRequestException("La partida no existe"));

        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new BadRequestException("La partida ya esta finalizada");
        }

        partida.setEstado(EstadoPartida.FINALIZADA);
        return partidaRepository.save(partida);
    }

    public Jugador recargarSaldo(RequestRecargarSaldoDto payload) {
        Jugador jugador = jugadorRepository.findById(payload.getJugadorId()).
                orElseThrow(() -> new BadRequestException("El jugador no existe"));

        jugador.setSaldo(jugador.getSaldo() + payload.getMonto());
        return jugadorRepository.save(jugador);
    }

    public List<Partida> obtenerHistorialPartida(Long jugadorId) {
        Jugador jugador = jugadorRepository.findById(jugadorId).
                orElseThrow(() -> new BadRequestException("El jugador no existe"));

        return partidaRepository.findByJugador(jugador);
    }
}