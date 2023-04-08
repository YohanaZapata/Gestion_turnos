package com.example.YohanaZapataEnd.service;


import com.example.YohanaZapataEnd.domain.Odontologo;
import com.example.YohanaZapataEnd.domain.Paciente;
import com.example.YohanaZapataEnd.domain.Turno;
import com.example.YohanaZapataEnd.dto.TurnoDTO;
import com.example.YohanaZapataEnd.exeptions.ResourceNotFoundException;
import com.example.YohanaZapataEnd.repository.TurnoRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TurnoService {

    private static final Logger LOGGER = Logger.getLogger(TurnoService.class);

    private TurnoRepository turnoRepository;

    @Autowired

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }
    public TurnoDTO registrarTurno(TurnoDTO turnodto){
        LOGGER.info("Comenzo el registo del TurnoDTO con ID: " + turnodto.getId());
        Turno turnoGuardado=turnoRepository.save(turnoDTOATurno(turnodto));
        return turnoATurnoDTO(turnoGuardado);
    }

    public Optional<TurnoDTO> buscarTurnoId(Long id){
       LOGGER.info("Comenzo la búsqueda del TurnoDTO con ID: " + id);
        Optional<Turno> turnoBuscado=turnoRepository.findById(id);
        if (turnoBuscado.isPresent()){
            //existe el turno
            return Optional.of(turnoATurnoDTO(turnoBuscado.get()));
        }
        else{
            //no existe turno, es nulo
            return Optional.empty();
        }
    }

    public void actualizarTurno(TurnoDTO turnodto){
       LOGGER.info("Se comenzo a actualizar el TurnoDTO con ID: " + turnodto.getId());
        turnoRepository.save(turnoDTOATurno(turnodto));
    }

    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoEliminar = buscarTurnoId(id);
        if (turnoEliminar.isPresent()) {
            turnoRepository.deleteById(id);
           LOGGER.warn("Eliminado el TurnoDTO con ID: " + id);
        } else {
            throw new ResourceNotFoundException("No encontramos el odontologo con id= " + id +
                    "que se desea eliminar.");
        }
    }

    private TurnoDTO turnoATurnoDTO(Turno turno){
        //convertir el turno a un turnoDTO
        TurnoDTO respuesta= new TurnoDTO();
        //cargar la información de turno al turno DTO
        respuesta.setId(turno.getId());
        respuesta.setPacienteId(turno.getPaciente().getId());
        respuesta.setNombrePaciente(turno.getPaciente().getNombre());
        respuesta.setOdontologoId(turno.getOdontologo().getId());
        respuesta.setNombreOdontologo(turno.getOdontologo().getNombre());
        respuesta.setFecha(turno.getFecha());
        //devolución
        return respuesta;
    }


    private Turno turnoDTOATurno(TurnoDTO turnodto){
        Turno respuesta= new Turno();
        //cargar la información de turno DTO al turno
        Odontologo odontologo= new Odontologo();
        Paciente paciente= new Paciente();
        odontologo.setId(turnodto.getOdontologoId());
        odontologo.setNombre(turnodto.getNombreOdontologo());
        paciente.setId(turnodto.getPacienteId());
        paciente.setNombre(turnodto.getNombrePaciente());
        respuesta.setFecha(turnodto.getFecha());
        respuesta.setId(turnodto.getId());
        //no debemos olvidarnos de agregar ambos objetos
        respuesta.setOdontologo(odontologo);
        respuesta.setPaciente(paciente);
        //salida
        return respuesta;
    }

    public List<TurnoDTO> listarTurnos(){
        LOGGER.info("Se comenzo la búsqueda de los turnos.");
        List<Turno> turnosEncontrados=turnoRepository.findAll();
        //recorremos la lista para ir convirtiendo cada elemento
        List<TurnoDTO> respuesta= new ArrayList<>();
        for (Turno turno:turnosEncontrados) {
            respuesta.add(turnoATurnoDTO(turno));
        }
        return respuesta;
    }


}
