package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.PacienteDto;
import ar.edu.um.clinicaUm.dtos.RecetaDto;
import ar.edu.um.clinicaUm.dtos.TurnoDto;
import ar.edu.um.clinicaUm.repositories.FarmaciaRepo;

public class ClinicaSvc {

  private static ClinicaSvc instance = null;
  private final FarmaciaRepo farmaciaRepo = FarmaciaRepo.getInstance();

  private ClinicaSvc() {}

  public static synchronized ClinicaSvc getInstance() {
    if (instance == null) {
      instance = new ClinicaSvc();
    }
    return instance;
  }

  public TurnoDto solicitarTurno(PacienteDto paciente, String especialidad, boolean conObraSocial) {
    // Logic to create and return a new TurnoDto
  }

  public RecetaDto iniciarTurno(TurnoDto turno) {
    // Logic to start a Turno and possibly return a RecetaDto
  }

  public void finalizarTurno(TurnoDto turno) {
    // Logic to finalize a Turno
  }
}