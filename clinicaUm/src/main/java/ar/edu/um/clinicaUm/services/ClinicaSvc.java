package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.MedicoDto;
import ar.edu.um.clinicaUm.dtos.PacienteDto;
import ar.edu.um.clinicaUm.dtos.TurnoDto;
import ar.edu.um.clinicaUm.repositories.ClinicaRepo;

public class ClinicaSvc {

  private static ClinicaSvc instance = null;
  private final ClinicaRepo clinicaRepo = ClinicaRepo.getInstance();

  private ClinicaSvc() {}

  public static synchronized ClinicaSvc getInstance() {
    if (instance == null) {
      instance = new ClinicaSvc();
    }
    return instance;
  }

  public synchronized TurnoDto solicitarTurno(PacienteDto paciente, MedicoDto medico) throws InterruptedException {
    TurnoDto turno = new TurnoDto(medico, paciente);
    clinicaRepo.addTurno(turno);
    return turno;
  }

  public void finalizarTurno(TurnoDto turno) {
    clinicaRepo.finishTurno(turno);
  }
}