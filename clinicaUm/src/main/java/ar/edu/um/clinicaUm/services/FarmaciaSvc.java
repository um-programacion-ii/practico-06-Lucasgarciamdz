package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import ar.edu.um.clinicaUm.dtos.RecetaDto;
import ar.edu.um.clinicaUm.exceptions.CodigosEx;
import ar.edu.um.clinicaUm.exceptions.FarmaciaEx;
import ar.edu.um.clinicaUm.repositories.FarmaciaRepo;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

public class FarmaciaSvc {

  private static final Logger LOGGER = Logger.getLogger(FarmaciaSvc.class.getName());

  private static FarmaciaSvc instance = null;
  private DrogueriaSvc drogueriaSvc = DrogueriaSvc.getInstance();
  private FarmaciaRepo farmaciaRepository = FarmaciaRepo.getInstance();

  private FarmaciaSvc() {
    LOGGER.info("Se ha creado una nueva instancia de FarmaciaSvc");
  }

  public FarmaciaSvc(FarmaciaRepo farmaciaRepo, DrogueriaSvc drogueriaSvc) {
    this.farmaciaRepository = farmaciaRepo;
    this.drogueriaSvc = drogueriaSvc;
  }

  public static synchronized FarmaciaSvc getInstance() {
    if (instance == null) {
      instance = new FarmaciaSvc();
    }
    return instance;
  }

  public List<MedicamentoDto> comprarMedicamento(RecetaDto receta)
      throws InterruptedException, ExecutionException, FarmaciaEx {
    if (receta == null || receta.medicamentos() == null || receta.medicamentos().isEmpty()) {
      throw new FarmaciaEx(
          CodigosEx.RECETA_SIN_MEDICAMENTOS,
          receta != null ? String.valueOf(receta.hashCode()) : "Receta is null");
    }

    List<MedicamentoDto> medicamentosComprados = new ArrayList<>();

    for (MedicamentoDto medicamento : receta.medicamentos().values()) {
      SimpleEntry<MedicamentoDto, Integer> stockMedicamentos =
          farmaciaRepository.getMedicamento(medicamento);
      if (stockMedicamentos != null && stockMedicamentos.getValue() > 0) {
        farmaciaRepository.updateMedicamento(medicamento, stockMedicamentos.getValue() - 1);
        medicamentosComprados.add(stockMedicamentos.getKey());
        LOGGER.info("Medicamento comprado de la farmacia: " + medicamento);
      } else {
        medicamentosComprados.add(drogueriaSvc.solicitarMedicamento(medicamento));
        LOGGER.info("Medicamento solicitado a la droguería: " + medicamento);
      }
    }
    return medicamentosComprados;
  }
}