package ar.edu.um.clinicaUm;

import ar.edu.um.clinicaUm.dtos.*;
import ar.edu.um.clinicaUm.services.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp {
  public static void main(String[] args) {
    ObraSocialDto obraSocial = new ObraSocialDto("Obra Social 1", "OS1");
    Map<Integer, ObraSocialDto> obrasSociales = new HashMap<>();
    obrasSociales.put(obraSocial.hashCode(), obraSocial);
    MedicoDto medico = new MedicoDto("John", "Doe", "1234", "General", true, obrasSociales);
    MedicamentoDto medicamento =
        new MedicamentoDto("Medicamento 1", "Droga 1", "Laboratorio 1", "Presentacion 1", "100");
    Map<Integer, MedicamentoDto> medicamentos = new HashMap<>();
    medicamentos.put(medicamento.hashCode(), medicamento);
    RecetaDto receta = new RecetaDto(1, medicamentos);

    ExecutorService executor = Executors.newFixedThreadPool(10);

    for (int i = 0; i < 10; i++) {
      PacienteDto paciente = new PacienteDto("Jane" + i, "Doe", obraSocial, receta);

      executor.submit(new PacienteSvc(paciente, medico));
    }

    executor.shutdown();
  }
}
