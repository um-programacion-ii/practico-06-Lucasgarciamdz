package ar.edu.um.clinicaUm.repositories;

import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class FarmaciaRepo {

  private static final Logger LOGGER = Logger.getLogger(FarmaciaRepo.class.getName());

  private static FarmaciaRepo instance = null;
  private final Map<Integer, SimpleEntry<MedicamentoDto, Integer>> stock =
      new ConcurrentHashMap<>();

  public static void resetInstance() {
    instance = null;
    LOGGER.info("Se ha reseteado la instancia de FarmaciaRepo");
  }

  public static synchronized FarmaciaRepo getInstance() {
    if (instance == null) {
      instance = new FarmaciaRepo();
      LOGGER.info("Se ha creado una nueva instancia de FarmaciaRepo");
    }
    return instance;
  }

  public Map<Integer, SimpleEntry<MedicamentoDto, Integer>> getStock() {
    LOGGER.info("Obteniendo el stock de medicamentos");
    return Collections.unmodifiableMap(this.stock);
  }

  public void addMedicamento(MedicamentoDto medicamento) {
    int hash = medicamento.hashCode();
    this.stock.merge(
        hash,
        new SimpleEntry<>(medicamento, 1),
        (oldVal, newVal) -> new SimpleEntry<>(medicamento, oldVal.getValue() + 1));
    LOGGER.info("Se ha añadido un nuevo medicamento: " + medicamento);
  }

  public SimpleEntry<MedicamentoDto, Integer> getMedicamento(MedicamentoDto medicamento) {
    int hash = medicamento.hashCode();
    LOGGER.info("Obteniendo el medicamento: " + medicamento);
    return this.stock.get(hash);
  }

  public void updateMedicamento(MedicamentoDto medicamento, int quantity) {
    int hash = medicamento.hashCode();
    this.stock.put(hash, new SimpleEntry<>(medicamento, quantity));
    LOGGER.info("Se ha actualizado el medicamento: " + medicamento + " con la cantidad: " + quantity);
  }
}