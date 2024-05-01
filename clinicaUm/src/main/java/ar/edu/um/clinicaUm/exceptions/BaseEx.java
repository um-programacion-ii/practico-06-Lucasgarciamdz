package ar.edu.um.clinicaUm.exceptions;

import java.io.Serial;

public class BaseEx extends Exception {

  @Serial private static final long serialVersionUID = 1L;

  private final CodigosEx codigoEx;
  private String infoExtra;

  public BaseEx(CodigosEx codigoEx) {
    super(codigoEx.getMessage());
    this.codigoEx = codigoEx;
  }

  public BaseEx(CodigosEx codigoEx, Throwable cause) {
    super(codigoEx.getMessage(), cause);
    this.codigoEx = codigoEx;
  }

  public BaseEx(CodigosEx codigoEx, String infoExtra) {
    super(codigoEx.getMessage());
    this.codigoEx = codigoEx;
    this.infoExtra = infoExtra;
  }

  public BaseEx(CodigosEx codigoEx, String infoExtra, Throwable cause) {
    super(codigoEx.getMessage(), cause);
    this.codigoEx = codigoEx;
    this.infoExtra = infoExtra;
  }

  public CodigosEx getCodigoEx() {
    return this.codigoEx;
  }

  public String getInfoExtra() {
    return this.infoExtra;
  }
}
