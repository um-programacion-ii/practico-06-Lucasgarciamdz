package ar.edu.um.clinicaUm.repositories;

import ar.edu.um.clinicaUm.dtos.MedicoDto;
import ar.edu.um.clinicaUm.dtos.TurnoDto;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClinicaRepo {

  private static ClinicaRepo instance;
  private final Map<Integer, TurnoDto> turnos = new ConcurrentHashMap<>();
  private final Map<Integer, MedicoDto> medicos = new ConcurrentHashMap<>();
  private final Map<Integer, MedicoDto> medicosAtendiendo = new ConcurrentHashMap<>();

  private ClinicaRepo() {}

  public static synchronized ClinicaRepo getInstance() {
    if (instance == null) {
      instance = new ClinicaRepo();
    }
    return instance;
  }

  public synchronized void addMedico(MedicoDto medico) {
    medicos.put(medico.hashCode(), medico);
  }

  public synchronized void addTurno(TurnoDto turno) throws InterruptedException {
    turnos.put(turno.hashCode(), turno);
    while (medicosAtendiendo.containsKey(turno.medico().hashCode())) {
      wait();
    }
    medicosAtendiendo.put(turno.medico().hashCode(), turno.medico());
  }

  public synchronized void finishTurno(TurnoDto turno) {
    medicosAtendiendo.remove(turno.medico().hashCode());
    turnos.remove(turno.hashCode());
    notifyAll();
  }
  public synchronized boolean isTurnoEnProceso(TurnoDto turno) {
    return medicosAtendiendo.containsKey(turno.medico().hashCode());
  }

  public synchronized Map<Integer, MedicoDto> getMedicos() {
    return medicos;
  }
}
