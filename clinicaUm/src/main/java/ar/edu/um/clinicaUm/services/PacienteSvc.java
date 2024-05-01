package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.*;
import ar.edu.um.clinicaUm.exceptions.ClinicaEx;
import ar.edu.um.clinicaUm.exceptions.FarmaciaEx;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class PacienteSvc implements Runnable {
  private final PacienteDto paciente;
  private final MedicoDto medico;
  private final ClinicaSvc clinicaSvc = ClinicaSvc.getInstance();
  private final FarmaciaSvc farmaciaSvc = FarmaciaSvc.getInstance();

  public PacienteSvc(PacienteDto paciente, MedicoDto medico) {
    this.paciente = paciente;
    this.medico = medico;
  }

  @Override
  public void run() {
    try {
      List<MedicoDto> medicos = clinicaSvc.getMedicos();

      Random rand = new Random();
      MedicoDto randomMedico = medicos.get(rand.nextInt(medicos.size()));

      TurnoDto turno = clinicaSvc.solicitarTurno(paciente, randomMedico);

      clinicaSvc.esperarTurno(turno);

      clinicaSvc.finalizarTurno(turno);

      RecetaDto receta = paciente.getReceta();
      if (receta != null) {
        List<MedicamentoDto> medicamentosComprados = farmaciaSvc.comprarMedicamento(receta);
      }
    } catch (InterruptedException | ClinicaEx | ExecutionException | FarmaciaEx e) {
      throw new RuntimeException("An error occurred: " + e.getMessage(), e);
    }
  }
}
