package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.MedicoDto;
import ar.edu.um.clinicaUm.dtos.PacienteDto;
import ar.edu.um.clinicaUm.dtos.TurnoDto;
import ar.edu.um.clinicaUm.exceptions.ClinicaEx;
import ar.edu.um.clinicaUm.exceptions.CodigosEx;
import ar.edu.um.clinicaUm.repositories.ClinicaRepo;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ClinicaSvc {

  private static final Logger LOGGER = Logger.getLogger(ClinicaSvc.class.getName());

  private static ClinicaSvc instance = null;
  private final ClinicaRepo clinicaRepo = ClinicaRepo.getInstance();

  private ClinicaSvc() {
    LOGGER.info("Se ha creado una nueva instancia de ClinicaSvc");
  }

  public static synchronized ClinicaSvc getInstance() {
    if (instance == null) {
      instance = new ClinicaSvc();
    }
    return instance;
  }

  public synchronized TurnoDto solicitarTurno(PacienteDto paciente, MedicoDto medico)
      throws InterruptedException, ClinicaEx {
    if (!medico.aceptaParticular() && paciente.getObraSocial() == null) {
      throw new ClinicaEx(CodigosEx.MEDICO_NO_ACEPTA_PARTICULAR);
    }

    if (!medico.obrasSocialesAcepatadas().containsKey(paciente.getObraSocial().hashCode())) {
      throw new ClinicaEx(
          CodigosEx.MEDICO_NO_ACEPTA_OBRA_SOCIAL,
          "Medico: " + medico.matricula() + "\nPaciente: " + paciente.getObraSocial().nombre());
    }

    LOGGER.info("Solicitando turno para " + paciente.getNombre() + " con " + medico.nombre());
    TurnoDto turno = new TurnoDto(medico, paciente);
    clinicaRepo.addTurno(turno);
    return turno;
  }

  public synchronized void finalizarTurno(TurnoDto turno) {
    clinicaRepo.finishTurno(turno);
    LOGGER.info("Turno finalizado: " + turno);
    notifyAll();
  }

  public synchronized void esperarTurno(TurnoDto turno) throws InterruptedException {
    while (clinicaRepo.isTurnoEnProceso(turno)) {
      LOGGER.info("Esperando turno: " + turno);
      wait();
    }
  }

  public synchronized List<MedicoDto> getMedicos() {
    Map<Integer, MedicoDto> medicos = clinicaRepo.getMedicos();
    LOGGER.info("Obteniendo la lista de médicos");
    return List.copyOf(medicos.values());
  }
}