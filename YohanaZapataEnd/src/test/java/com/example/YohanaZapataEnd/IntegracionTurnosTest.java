package com.example.YohanaZapataEnd;


import com.example.YohanaZapataEnd.domain.Domicilio;
import com.example.YohanaZapataEnd.domain.Odontologo;
import com.example.YohanaZapataEnd.domain.Paciente;
import com.example.YohanaZapataEnd.dto.TurnoDTO;
import com.example.YohanaZapataEnd.service.OdontologoService;
import com.example.YohanaZapataEnd.service.PacienteService;
import com.example.YohanaZapataEnd.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionTurnosTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;

    private void cargandoDatos(){
        Paciente agregarPaciente=pacienteService
                .registrarPaciente(new Paciente("Herrera","Bernardo","79298563",
                        LocalDate.of(2022,12,7),"peto@gmail.com",
                        new Domicilio("Santo Domingo", "La fontana", "Cali", "Valle")));
        Odontologo agregarOdontologo=odontologoService
                .registrarOdontologo(new Odontologo("Herrera","Emanuel","123456"));
        TurnoDTO turnoDTO= new TurnoDTO();
        turnoDTO.setFecha(LocalDate.of(2023,01,8));
        turnoDTO.setOdontologoId(agregarOdontologo.getId());
        turnoDTO.setPacienteId(agregarPaciente.getId());
        turnoService.registrarTurno(turnoDTO);
    }
    @Test
    public void listadoTurnosTest() throws Exception {
        cargandoDatos();
        MvcResult respuesta=mockMvc.perform
                (MockMvcRequestBuilders.get("/turnos").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }
}
