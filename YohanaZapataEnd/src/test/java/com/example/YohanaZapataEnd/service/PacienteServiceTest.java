package com.example.YohanaZapataEnd.service;

import com.example.YohanaZapataEnd.domain.Domicilio;
import com.example.YohanaZapataEnd.domain.Paciente;
import com.example.YohanaZapataEnd.exeptions.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class PacienteServiceTest {
    @Autowired
    private PacienteService pacienteService;

    @Test
    @Order(1)
    public void registrarPacienteTest(){
        Paciente guardarPaciente= new Paciente("Bedoya",
                "Liliana","123654", LocalDate.of(2023,03,31),
                "yohas@gmail.com",new Domicilio("Av santo Domingo","445","Jamundi",
                "Valle"));
        Paciente pacienteGuardado=pacienteService.registrarPaciente(guardarPaciente);
        assertEquals(1L,pacienteGuardado.getId());
    }

    @Test
    @Order(2)
    public void buscarPacienteIDTest(){
        Long buscarId=1L;
        Optional<Paciente> pacienteBuscado=pacienteService.buscarPacienteId(buscarId);
        assertNotNull(pacienteBuscado.get());
    }

    @Test
    @Order(3)
    public void listarPacientesTest(){
        List<Paciente> pacientes=pacienteService.listarPacientes();
        assertEquals(1,pacientes.size());
    }

    @Test
    @Order(4)
    public void actualizarPacienteTest(){
        Paciente actualizarPaciente= new Paciente("Zapata", "Yohana","1130681593",LocalDate.of(2023,03,28),"yohaskj22@gmail.com", new Domicilio("Av Santo Domingo", "Boulevar Alfaguara", "Jamundi", "Valle del Cauca"));
        pacienteService.actualizarPaciente(actualizarPaciente);
        Optional<Paciente> pacienteActualizado=pacienteService.buscarPacienteId(2L);
        assertEquals("Yohana",pacienteActualizado.get().getNombre());
    }

    @Test
    @Order(5)
    public void eliminarPacienteTest() throws ResourceNotFoundException {
        Long eliminarId=1L;
        pacienteService.eliminarPacienteId(eliminarId);
        Optional<Paciente> pacienteEliminado=pacienteService.buscarPacienteId(eliminarId);
        assertFalse(pacienteEliminado.isPresent());
    }
}