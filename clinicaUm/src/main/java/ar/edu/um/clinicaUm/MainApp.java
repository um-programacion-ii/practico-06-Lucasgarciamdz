package ar.edu.um.clinicaUm;

import ar.edu.um.clinicaUm.dtos.*;
import ar.edu.um.clinicaUm.services.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp {
  private static final int NUM_THREADS = 10;
  private static final int NUM_PATIENTS = 10;

  public static void main(String[] args) {
    MedicoDto medico = createMedico();
    RecetaDto receta = createReceta();

    ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

    for (int i = 0; i < NUM_PATIENTS; i++) {
      PacienteDto paciente = createPaciente(i, medico, receta);
      try {
        executor.submit(new PacienteSvc(paciente, medico));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    executor.shutdown();
  }

  private static PacienteDto createPaciente(int id, MedicoDto medico, RecetaDto receta) {
    return new PacienteDto("Patient " + id, "Doe", medico.obrasSocialesAcepatadas().values().iterator().next(), receta);
  }

  private static MedicoDto createMedico() {
    ObraSocialDto obraSocial = new ObraSocialDto("Obra Social 1", "OS1");
    Map<Integer, ObraSocialDto> obrasSociales = new HashMap<>();
    obrasSociales.put(obraSocial.hashCode(), obraSocial);
    return new MedicoDto("John", "Doe", "1234", "General", true, obrasSociales);
  }

  private static RecetaDto createReceta() {
    MedicamentoDto medicamento = new MedicamentoDto("Medicamento 1", "Droga 1", "Laboratorio 1", "Presentacion 1", "100");
    Map<Integer, MedicamentoDto> medicamentos = new HashMap<>();
    medicamentos.put(medicamento.hashCode(), medicamento);
    return new RecetaDto(1, medicamentos);
  }
}