package ar.edu.um.clinicaUm;

import ar.edu.um.clinicaUm.dtos.MedicoDto;
import ar.edu.um.clinicaUm.dtos.PacienteDto;
import ar.edu.um.clinicaUm.services.PacienteSvc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainApp {
  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(10);

    for (int i = 0; i < 10; i++) {
      PacienteDto paciente = new PacienteDto();
      MedicoDto medico = new MedicoDto();
      executor.submit(new PacienteSvc(paciente, medico));
    }

    executor.shutdown();
  }
}
