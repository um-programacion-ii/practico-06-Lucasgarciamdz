package ar.edu.um.clinicaUm.repositories;

import ar.edu.um.clinicaUm.dtos.PacienteDto;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class PacienteRepo {
  private static final Logger LOGGER = Logger.getLogger(PacienteRepo.class.getName());

  private static PacienteRepo instance;
  private final Map<Integer, PacienteDto> pacientes = new ConcurrentHashMap<>();

  private PacienteRepo() {
    LOGGER.info("Se ha creado una nueva instancia de PacienteRepo");
  }

  public static synchronized PacienteRepo getInstance() {
    if (instance == null) {
      instance = new PacienteRepo();
    }
    return instance;
  }

  public void save(PacienteDto paciente) {
    this.pacientes.put(paciente.hashCode(), paciente);
    LOGGER.info("Se ha guardado un nuevo paciente: " + paciente);
  }

  public PacienteDto findByHash(Integer hash) {
    LOGGER.info("Buscando paciente con hash: " + hash);
    return this.pacientes.get(hash);
  }

  public void delete(Integer hash) {
    this.pacientes.remove(hash);
    LOGGER.info("Se ha eliminado el paciente con hash: " + hash);
  }

  public Map<Integer, PacienteDto> findAll() {
    LOGGER.info("Obteniendo todos los pacientes");
    return this.pacientes;
  }
}