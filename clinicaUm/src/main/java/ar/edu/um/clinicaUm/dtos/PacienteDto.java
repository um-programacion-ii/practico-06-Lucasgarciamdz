package ar.edu.um.clinicaUm.dtos;

public class PacienteDto {
  private String nombre;
  private String apellido;
  private ObraSocialDto obraSocial;
  private RecetaDto receta;

  public PacienteDto() {
  }

  public PacienteDto(String nombre, String apellido, ObraSocialDto obraSocial, RecetaDto receta) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.obraSocial = obraSocial;
    this.receta = receta;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public ObraSocialDto getObraSocial() {
    return obraSocial;
  }

  public void setObraSocial(ObraSocialDto obraSocial) {
    this.obraSocial = obraSocial;
  }

  public RecetaDto getReceta() {
    return receta;
  }

  public void setReceta(RecetaDto receta) {
    this.receta = receta;
  }
}