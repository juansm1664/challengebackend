package com.juandavid.springboot.challengebackend.service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import com.juandavid.springboot.challengebackend.entity.CallHistory;
import com.juandavid.springboot.challengebackend.repository.CallHistoryRepository;


@Service
public class HistoryServiceImpl implements HistoryService {

    //Logger para registrar información y errores
    private static final Logger log = LoggerFactory.getLogger(HistoryServiceImpl.class);

    private final CallHistoryRepository callHistoryRepository;

    //Inyeccion de dependencias
    public HistoryServiceImpl(CallHistoryRepository callHistoryRepository) {
        this.callHistoryRepository = callHistoryRepository;
    }

    /*
     * Guarda un registro de historial de llamadas asincrónicamente.
     * Marcado con @async para ejecutar en un grupo de hilos separado.
     * Marcado con @Transactional para garantizar la atomicidad de la operación de guardar
     */

    @Async
    @Transactional
    @Override
    public void saveCallAsync(CallHistory callHistory) {
        log.info("Guardando el historial de llamadas para el endpoint: {}", callHistory.getEndpoint());
        try{
            if (callHistory == null) {
                log.warn("El objeto CallHistory es nulo");

            }
            else {
                callHistoryRepository.save(callHistory);
                log.info("Historial de llamadas guardado correctamente");
                log.debug("Historial de llamadas asíncrono a sido guardado con ID: {}", callHistory.getId());
            }
        }
        catch (Exception e){
            log.error("Error al guardar el historial de llamadas: {}", e.getMessage(), e);
        }
    }

    /*
    * Recupera una lista paginada de registros de historial de llamadas.
    * Marcado con @Transactional (ReadOnly = True)
      para la optimización del rendimiento en las operaciones de lectura.
    */

    @Transactional(readOnly = true)
    @Override
    public Page<CallHistory> getHistory(Pageable pageable) {
            log.info("Obteniendo el historial de llamadas - Pagina {}. size {}, sort {}",
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSort());

        return callHistoryRepository.findAll(pageable);
    }

}
