package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.*;
import ar.edu.um.clinicaUm.exceptions.ClinicaEx;
import ar.edu.um.clinicaUm.exceptions.FarmaciaEx;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class PacienteSvc implements Runnable {
  private static final Logger LOGGER = Logger.getLogger(PacienteSvc.class.getName());

  private final PacienteDto paciente;
  private final MedicoDto medico;
  private final ClinicaSvc clinicaSvc = ClinicaSvc.getInstance();
  private final FarmaciaSvc farmaciaSvc = FarmaciaSvc.getInstance();

  public PacienteSvc(PacienteDto paciente, MedicoDto medico) {
    this.paciente = paciente;
    this.medico = medico;
    LOGGER.info("Se ha creado una nueva instancia de PacienteSvc para el paciente: " + paciente + " y el médico: " + medico);
  }

  @Override
  public void run() {
    try {
      LOGGER.info("Solicitando turno para el paciente: " + paciente + " con el médico: " + medico);
      TurnoDto turno = clinicaSvc.solicitarTurno(paciente, medico);

      LOGGER.info("Esperando turno: " + turno);
      clinicaSvc.esperarTurno(turno);

      LOGGER.info("Finalizando turno: " + turno);
      clinicaSvc.finalizarTurno(turno);

      RecetaDto receta = paciente.getReceta();
      if (receta != null) {
        LOGGER.info("Comprando medicamentos para la receta: " + receta);
        List<MedicamentoDto> medicamentosComprados = farmaciaSvc.comprarMedicamento(receta);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.severe("El hilo fue interrumpido: " + e.getMessage());
      throw new RuntimeException("Thread was interrupted", e);
    } catch (ClinicaEx e) {
      LOGGER.severe("Ocurrió una excepción en la clínica: " + e.getMessage());
      throw new RuntimeException("Clinic exception occurred: " + e.getMessage(), e);
    } catch (ExecutionException e) {
      LOGGER.severe("Ocurrió una excepción de ejecución: " + e.getMessage());
      throw new RuntimeException("Execution exception occurred: " + e.getMessage(), e);
    } catch (FarmaciaEx e) {
      LOGGER.severe("Ocurrió una excepción en la farmacia: " + e.getMessage());
      throw new RuntimeException("Pharmacy exception occurred: " + e.getMessage(), e);
    }
  }
}