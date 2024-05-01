package ar.edu.um.clinicaUm.exceptions;

public enum CodigosEx {
  RECETA_SIN_MEDICAMENTOS("5000F", "La receta no tiene medicamentos");

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
