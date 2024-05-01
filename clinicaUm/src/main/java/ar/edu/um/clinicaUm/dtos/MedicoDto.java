package ar.edu.um.clinicaUm.dtos;

import java.util.List;

public record MedicoDto(
    String nombre,
    String apellido,
    String matricula,
    String especialidad,
    Boolean aceptaParticular,
    List<ObraSocialDto> obrasSocialesAcepatadas) {}
