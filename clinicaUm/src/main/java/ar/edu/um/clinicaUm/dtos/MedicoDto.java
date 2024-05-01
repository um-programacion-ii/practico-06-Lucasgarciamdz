package ar.edu.um.clinicaUm.dtos;

import java.util.Map;

public record MedicoDto(
    String nombre,
    String apellido,
    String matricula,
    String especialidad,
    boolean aceptaParticular,
    Map<Integer, ObraSocialDto> obrasSocialesAcepatadas) {}
