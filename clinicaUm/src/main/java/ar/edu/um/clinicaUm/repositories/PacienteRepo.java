package ar.edu.um.clinicaUm.repositories;

import ar.edu.um.clinicaUm.dtos.PacienteDto;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PacienteRepo {
  private static PacienteRepo instance;

  private final Map<Integer, PacienteDto> pacientes = new ConcurrentHashMap<>();

  public static synchronized PacienteRepo getInstance() {
    if (instance == null) {
      instance = new PacienteRepo();
    }
    return instance;
  }

  public void save(PacienteDto paciente) {
    this.pacientes.put(paciente.hashCode(), paciente);
  }

  public PacienteDto findByHash(Integer hash) {
    return this.pacientes.get(hash);
  }

  public void delete(Integer hash) {
    this.pacientes.remove(hash);
  }

  public Map<Integer, PacienteDto> findAll() {
    return this.pacientes;
  }
}
