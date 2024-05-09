package ar.edu.um.clinicaUm.repositories;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import org.mockito.*;

public class DrogueriaRepoTest {

	@Mock
	private ScheduledExecutorService executorService;

	@Mock
	private ScheduledFuture scheduledFuture;

	@InjectMocks
	private DrogueriaRepo drogueriaRepo = DrogueriaRepo.getInstance();

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getInstance() {
		DrogueriaRepo firstInstance = DrogueriaRepo.getInstance();
		DrogueriaRepo secondInstance = DrogueriaRepo.getInstance();

		assertNotNull(firstInstance);
		assertSame(firstInstance, secondInstance);
	}

	@Test
	public void getMedicamento() throws InterruptedException, ExecutionException {
		MedicamentoDto medicamento = new MedicamentoDto("Ibupirac", "Ibuprofeno", "Bayer", "200mg", "100");
		Mockito.when(executorService.schedule(Mockito.any(Runnable.class), Mockito.anyLong(), Mockito.any())).thenReturn(scheduledFuture);
		Mockito.when(scheduledFuture.get()).thenReturn(null);

		MedicamentoDto returnedMedicamento = drogueriaRepo.getMedicamento(medicamento);

		assertSame(medicamento, returnedMedicamento);
	}
}