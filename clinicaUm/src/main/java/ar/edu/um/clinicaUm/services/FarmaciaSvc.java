package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import ar.edu.um.clinicaUm.dtos.RecetaDto;
import ar.edu.um.clinicaUm.exceptions.CodigosEx;
import ar.edu.um.clinicaUm.exceptions.FarmaciaEx;
import ar.edu.um.clinicaUm.repositories.DrogueriaRepo;
import ar.edu.um.clinicaUm.repositories.FarmaciaRepo;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FarmaciaSvc {

  private static FarmaciaSvc instance = null;
  private final DrogueriaRepo drogueriaRepo = DrogueriaRepo.getInstance();
  private final FarmaciaRepo farmaciaRepository = FarmaciaRepo.getInstance();

  private FarmaciaSvc() {}

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
      SimpleEntry<MedicamentoDto, Integer> stockMedicamento =
          farmaciaRepository.getMedicamento(medicamento);
      if (stockMedicamento != null && stockMedicamento.getValue() > 0) {
        farmaciaRepository.updateMedicamento(medicamento, stockMedicamento.getValue() - 1);
        medicamentosComprados.add(stockMedicamento.getKey());
      } else {
        medicamentosComprados.add(drogueriaRepo.pedirMedicamento(medicamento));
      }
    }
    return medicamentosComprados;
  }
}
