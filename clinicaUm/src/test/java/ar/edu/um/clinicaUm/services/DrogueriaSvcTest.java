package ar.edu.um.clinicaUm.services;

import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import ar.edu.um.clinicaUm.repositories.DrogueriaRepo;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;

public class DrogueriaSvcTest {

	@Mock
	private DrogueriaRepo drogueriaRepo;

	@InjectMocks
	private DrogueriaSvc drogueriaSvc = DrogueriaSvc.getInstance();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getInstance() {
		DrogueriaSvc firstInstance = DrogueriaSvc.getInstance();
		DrogueriaSvc secondInstance = DrogueriaSvc.getInstance();

		assertNotNull(firstInstance);
		assertSame(firstInstance, secondInstance);
	}

	@Test
	public void solicitarMedicamento() throws InterruptedException, ExecutionException {
		MedicamentoDto medicamento = new MedicamentoDto("Ibupirac", "Ibuprofeno", "Bayer", "200mg", "100");
		Mockito.when(drogueriaRepo.getMedicamento(medicamento)).thenReturn(medicamento);

		MedicamentoDto returnedMedicamento = drogueriaSvc.solicitarMedicamento(medicamento);

		assertSame(medicamento, returnedMedicamento);
	}
}