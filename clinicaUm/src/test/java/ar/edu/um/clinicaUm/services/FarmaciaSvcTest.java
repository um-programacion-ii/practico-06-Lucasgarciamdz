package ar.edu.um.clinicaUm.services;

import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import ar.edu.um.clinicaUm.dtos.RecetaDto;
import ar.edu.um.clinicaUm.exceptions.FarmaciaEx;
import ar.edu.um.clinicaUm.repositories.FarmaciaRepo;
import ar.edu.um.clinicaUm.repositories.DrogueriaRepo;

import java.util.*;

public class FarmaciaSvcTest {

  private FarmaciaSvc farmaciaSvc;
  private FarmaciaRepo farmaciaRepoMock;
  private DrogueriaSvc drogueriaSvcMock;

  @BeforeEach
  public void setUp() {
    farmaciaRepoMock = mock(FarmaciaRepo.class);
    drogueriaSvcMock = mock(DrogueriaSvc.class);
    farmaciaSvc = new FarmaciaSvc(farmaciaRepoMock, drogueriaSvcMock);
  }

  @Test
  public void comprarMedicamento() throws FarmaciaEx, InterruptedException, ExecutionException {
    MedicamentoDto medicamento =
        new MedicamentoDto("Ibupirac", "Ibuprofeno", "Bayer", "200mg", "100");
    Map<Integer, MedicamentoDto> medicamentos = new HashMap<>();
    medicamentos.put(2, medicamento);

    RecetaDto receta = new RecetaDto(1, medicamentos);

    when(farmaciaRepoMock.getMedicamento(medicamento))
        .thenReturn(new AbstractMap.SimpleEntry<>(medicamento, 1));

    List<MedicamentoDto> expected = Collections.singletonList(medicamento);
    List<MedicamentoDto> actual = farmaciaSvc.comprarMedicamento(receta);

    assertEquals(expected, actual);
  }

  @Test
  public void comprarMedicamentoFromDrogueria()
      throws FarmaciaEx, InterruptedException, ExecutionException {
    MedicamentoDto medicamento =
        new MedicamentoDto("Ibupirac", "Ibuprofeno", "Bayer", "200mg", "100");
    Map<Integer, MedicamentoDto> medicamentos = new HashMap<>();
    medicamentos.put(1, medicamento);

    RecetaDto receta = new RecetaDto(1, medicamentos);

    when(farmaciaRepoMock.getMedicamento(medicamento)).thenReturn(null);
    when(drogueriaSvcMock.solicitarMedicamento(medicamento)).thenReturn(medicamento);

    List<MedicamentoDto> expected = Collections.singletonList(medicamento);
    List<MedicamentoDto> actual = farmaciaSvc.comprarMedicamento(receta);

    assertEquals(expected, actual);
  }
}
