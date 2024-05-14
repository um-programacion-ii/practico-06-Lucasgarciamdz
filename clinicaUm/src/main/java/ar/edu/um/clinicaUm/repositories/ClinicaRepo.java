package ar.edu.um.clinicaUm.repositories;

import ar.edu.um.clinicaUm.dtos.MedicoDto;
import ar.edu.um.clinicaUm.dtos.TurnoDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ClinicaRepo {

  private static final Logger LOGGER = Logger.getLogger(ClinicaRepo.class.getName());

  private static ClinicaRepo instance;
  private final Map<Integer, TurnoDto> turnos = new ConcurrentHashMap<>();
  private final Map<Integer, MedicoDto> medicos = new ConcurrentHashMap<>();
  private final Map<Integer, MedicoDto> medicosAtendiendo = new ConcurrentHashMap<>();

  private ClinicaRepo() {}

  public static synchronized ClinicaRepo getInstance() {
    if (instance == null) {
      instance = new ClinicaRepo();
      LOGGER.info("Se ha creado una nueva instancia de ClinicaRepo");
    }
    return instance;
  }

  public synchronized void addMedico(MedicoDto medico) {
    medicos.put(medico.hashCode(), medico);
    LOGGER.info("Se ha añadido un nuevo médico: " + medico);
  }

  public synchronized void addTurno(TurnoDto turno) throws InterruptedException {
    turnos.put(turno.hashCode(), turno);
    LOGGER.info("Se ha añadido un nuevo turno: " + turno);
    while (medicosAtendiendo.containsKey(turno.medico().hashCode())) {
      wait();
    }
    medicosAtendiendo.put(turno.medico().hashCode(), turno.medico());
    LOGGER.info("El médico " + turno.medico() + " ha comenzado a atender");
  }

  public synchronized void finishTurno(TurnoDto turno) {
    medicosAtendiendo.remove(turno.medico().hashCode());
    turnos.remove(turno.hashCode());
    LOGGER.info("El turno " + turno + " ha finalizado");
    notifyAll();
  }

  public synchronized boolean isTurnoEnProceso(TurnoDto turno) {
    boolean enProceso = medicosAtendiendo.containsKey(turno.medico().hashCode());
    LOGGER.info("El turno " + turno + " está en proceso: " + enProceso);
    return enProceso;
  }

  public synchronized Map<Integer, MedicoDto> getMedicos() {
    LOGGER.info("Obteniendo la lista de médicos");
    return medicos;
  }
}