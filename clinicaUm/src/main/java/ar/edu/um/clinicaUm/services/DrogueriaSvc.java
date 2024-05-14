package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import ar.edu.um.clinicaUm.repositories.DrogueriaRepo;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class DrogueriaSvc {

  private static final Logger LOGGER = Logger.getLogger(DrogueriaSvc.class.getName());

  private static DrogueriaSvc instance = null;
  private final DrogueriaRepo drogueriaRepo = DrogueriaRepo.getInstance();

  private DrogueriaSvc() {
    LOGGER.info("Se ha creado una nueva instancia de DrogueriaSvc");
  }

  public static synchronized DrogueriaSvc getInstance() {
    if (instance == null) {
      instance = new DrogueriaSvc();
    }
    return instance;
  }

  public MedicamentoDto solicitarMedicamento(MedicamentoDto medicamento)
      throws InterruptedException, ExecutionException {
    LOGGER.info("Solicitando medicamento: " + medicamento);
    MedicamentoDto result = drogueriaRepo.getMedicamento(medicamento);
    LOGGER.info("Medicamento obtenido: " + result);
    return result;
  }
}