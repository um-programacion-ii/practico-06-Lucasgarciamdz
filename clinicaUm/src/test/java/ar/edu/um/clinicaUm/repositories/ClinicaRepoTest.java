package ar.edu.um.clinicaUm.repositories;

import ar.edu.um.clinicaUm.dtos.ObraSocialDto;
import ar.edu.um.clinicaUm.dtos.PacienteDto;
import java.util.HashMap;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import ar.edu.um.clinicaUm.dtos.MedicoDto;
import ar.edu.um.clinicaUm.dtos.TurnoDto;
import java.util.Map;

public class ClinicaRepoTest {

	@Test
	public void addMedico() {
		ClinicaRepo c = ClinicaRepo.getInstance();
		Map<Integer, ObraSocialDto> obrasSocialesAcceptas = new HashMap<>();
		MedicoDto medico = new MedicoDto("lucas", "garcia", "9872534", "compu", true, obrasSocialesAcceptas);
		c.addMedico(medico);
		assertTrue(c.getMedicos().containsValue(medico), "Medico should be added");
	}

	@Test
	public void addTurno() throws InterruptedException {
		ClinicaRepo c = ClinicaRepo.getInstance();
		Map<Integer, ObraSocialDto> obrasSocialesAcceptas = new HashMap<>();
		MedicoDto medico = new MedicoDto("lucas", "garcia", "9872534", "compu", true, obrasSocialesAcceptas);
		PacienteDto paciente = new PacienteDto("lucas", "garcia", null, null);
		TurnoDto turno = new TurnoDto(medico, paciente);
		c.addTurno(turno);
		assertTrue(c.isTurnoEnProceso(turno), "Turno should be in process");
	}

	@Test
	public void getMedicos() {
		ClinicaRepo c = ClinicaRepo.getInstance();
		Map<Integer, ObraSocialDto> obrasSocialesAcceptas = new HashMap<>();
		MedicoDto medico = new MedicoDto("lucas", "garcia", "9872534", "compu", true, obrasSocialesAcceptas);
		c.addMedico(medico);
		Map<Integer, MedicoDto> actual = c.getMedicos();
		assertTrue(actual.containsValue(medico), "Medicos should contain added medico");
	}
}