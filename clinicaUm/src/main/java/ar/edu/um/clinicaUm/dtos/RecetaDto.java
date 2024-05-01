package ar.edu.um.clinicaUm.dtos;

import java.util.Map;

public record RecetaDto(int id, Map<Integer, MedicamentoDto> medicamentos) {}
