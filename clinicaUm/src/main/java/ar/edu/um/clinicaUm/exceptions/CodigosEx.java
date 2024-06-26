package ar.edu.um.clinicaUm.exceptions;

public enum CodigosEx {
  RECETA_SIN_MEDICAMENTOS("5000F", "La receta no tiene medicamentos"),

  // ClinicaEx
  MEDICO_NO_ACEPTA_PARTICULAR("5000A", "El medico no acepta pacientes particulares"),
  MEDICO_NO_ACEPTA_OBRA_SOCIAL("5000B", "El medico no acepta la obra social del paciente"),
  MEDICO_NO_ENCONTRADO("5000C", "El medico no trabaja en la clinica");

  private final String message;
  private final String codigoEx;

  CodigosEx(String codigoEx, String message) {
    this.codigoEx = codigoEx;
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public String getCodigoEx() {
    return this.codigoEx;
  }
}
