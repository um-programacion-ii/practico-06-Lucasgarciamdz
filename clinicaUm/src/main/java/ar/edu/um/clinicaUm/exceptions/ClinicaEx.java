package ar.edu.um.clinicaUm.exceptions;

public class ClinicaEx extends BaseEx {

  public ClinicaEx(CodigosEx codigoEx) {
    super(codigoEx);
  }

  public ClinicaEx(CodigosEx codigoEx, Throwable cause) {
    super(codigoEx, cause);
  }

  public ClinicaEx(CodigosEx codigoEx, String infoExtra) {
    super(codigoEx, infoExtra);
  }

  public ClinicaEx(CodigosEx codigoEx, String infoExtra, Throwable cause) {
    super(codigoEx, infoExtra, cause);
  }
}
