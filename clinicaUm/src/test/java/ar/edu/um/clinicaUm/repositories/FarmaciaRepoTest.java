package ar.edu.um.clinicaUm.repositories;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import ar.edu.um.clinicaUm.dtos.MedicamentoDto;

public class FarmaciaRepoTest {

	@BeforeEach
	public void resetSingleton() {
		FarmaciaRepo.resetInstance();
	}

	@Test
	public void getInstance() {
		FarmaciaRepo firstInstance = FarmaciaRepo.getInstance();
		FarmaciaRepo secondInstance = FarmaciaRepo.getInstance();

		assertNotNull(firstInstance);
		assertSame(firstInstance, secondInstance);
	}

	@Test
	public void getStock() {
		FarmaciaRepo farmaciaRepo = FarmaciaRepo.getInstance();
		Map<Integer, SimpleEntry<MedicamentoDto, Integer>> stock = farmaciaRepo.getStock();

		assertNotNull(stock);
		assertTrue(stock.isEmpty());
	}

	@Test
	public void addAndGetMedicamento() {
		FarmaciaRepo farmaciaRepo = FarmaciaRepo.getInstance();
				MedicamentoDto medicamento = new MedicamentoDto("Ibupirac", "Ibuprofeno", "Bayer", "200mg", "100");

		farmaciaRepo.addMedicamento(medicamento);

		SimpleEntry<MedicamentoDto, Integer> stockItem = farmaciaRepo.getMedicamento(medicamento);

		assertNotNull(stockItem);
		assertSame(medicamento, stockItem.getKey());
		assertEquals(1, stockItem.getValue());
	}

	@Test
	public void updateMedicamento() {
		FarmaciaRepo farmaciaRepo = FarmaciaRepo.getInstance();
				MedicamentoDto medicamento = new MedicamentoDto("Ibupirac", "Ibuprofeno", "Bayer", "200mg", "100");

		farmaciaRepo.addMedicamento(medicamento);
		farmaciaRepo.updateMedicamento(medicamento, 5);

		SimpleEntry<MedicamentoDto, Integer> stockItem = farmaciaRepo.getMedicamento(medicamento);

		assertNotNull(stockItem);
		assertSame(medicamento, stockItem.getKey());
		assertEquals(5, stockItem.getValue());
	}
}