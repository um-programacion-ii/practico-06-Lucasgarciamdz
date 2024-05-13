package ar.edu.um.clinicaUm.repositories;

import ar.edu.um.clinicaUm.dtos.MedicamentoDto;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DrogueriaRepo {

  private static final Logger LOGGER = Logger.getLogger(DrogueriaRepo.class.getName());

  private static DrogueriaRepo instance;
  private final Random random = new Random();
  private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

  private DrogueriaRepo() {
    LOGGER.info("Se ha creado una nueva instancia de DrogueriaRepo");
  }

  public static synchronized DrogueriaRepo getInstance() {
    if (instance == null) {
      instance = new DrogueriaRepo();
    }
    return instance;
  }

  public MedicamentoDto getMedicamento(MedicamentoDto medicamento)
      throws InterruptedException, ExecutionException {
    long sleepTime = random.nextInt(5000);
    LOGGER.info("Obteniendo medicamento: " + medicamento + ". Esto puede tardar hasta " + sleepTime + " milisegundos.");
    executorService.schedule(() -> {}, sleepTime, TimeUnit.MILLISECONDS).get();
    LOGGER.info("Medicamento obtenido: " + medicamento);
    return medicamento;
  }
}