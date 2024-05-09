package ar.edu.um.clinicaUm.repositories;

import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FarmaciaRepo {

  private static FarmaciaRepo instance = null;
  private final Map<Integer, SimpleEntry<MedicamentoDto, Integer>> stock =
      new ConcurrentHashMap<>();

  public static void resetInstance() {
    instance = null;
  }

  public static synchronized FarmaciaRepo getInstance() {
    if (instance == null) {
      instance = new FarmaciaRepo();
    }
    return instance;
  }

  public Map<Integer, SimpleEntry<MedicamentoDto, Integer>> getStock() {
    return Collections.unmodifiableMap(this.stock);
  }

  public void addMedicamento(MedicamentoDto medicamento) {
    int hash = medicamento.hashCode();
    this.stock.merge(
        hash,
        new SimpleEntry<>(medicamento, 1),
        (oldVal, newVal) -> new SimpleEntry<>(medicamento, oldVal.getValue() + 1));
  }

  public SimpleEntry<MedicamentoDto, Integer> getMedicamento(MedicamentoDto medicamento) {
    int hash = medicamento.hashCode();
    return this.stock.get(hash);
  }

  public void updateMedicamento(MedicamentoDto medicamento, int quantity) {
    int hash = medicamento.hashCode();
    this.stock.put(hash, new SimpleEntry<>(medicamento, quantity));
  }
}
