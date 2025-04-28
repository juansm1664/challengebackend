package com.juandavid.springboot.challengebackend.service;

import com.juandavid.springboot.challengebackend.entity.CallHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface HistoryService {

    // void: No devuelve nada porque su propósito es realizar una acción (guardar
    void saveCallAsync(CallHistory callHistory);

    /*
     Define el método para obtener el historial.
    Page<CallHistory>: Especifica que el método devolverá una página de objetos CallHistory.
    getHistory: Nombre descriptivo de la acción.
    Pageable pageable: Recibe como parámetro un objeto Pageable que le indica cómo debe realizarse la paginación y
    el ordenamiento. Spring Boot puede construir este objeto automáticamente a partir de los parámetros
    pasados en la solicitud de la URL en un controlador (ej: ?page=0&size=10&sort=timestamp,desc).
     */
    Page<CallHistory> getHistory(Pageable pageable);
}
