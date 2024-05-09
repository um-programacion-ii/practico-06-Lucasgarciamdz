package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import ar.edu.um.clinicaUm.repositories.DrogueriaRepo;
import java.util.concurrent.ExecutionException;

public class DrogueriaSvc {

  private static DrogueriaSvc instance = null;
  private final DrogueriaRepo drogueriaRepo = DrogueriaRepo.getInstance();

  private DrogueriaSvc() {}

  public static synchronized DrogueriaSvc getInstance() {
    if (instance == null) {
      instance = new DrogueriaSvc();
    }
    return instance;
  }

  public MedicamentoDto solicitarMedicamento(MedicamentoDto medicamento)
      throws InterruptedException, ExecutionException {
    return drogueriaRepo.getMedicamento(medicamento);
  }
}