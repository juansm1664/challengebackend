package com.juandavid.springboot.challengebackend.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ExternalPercentageServiceImpl implements ExternalPercentageService {

    private final Logger log = LoggerFactory.getLogger(ExternalPercentageServiceImpl.class);

    //Variable para almacenar el valor fijo de porcentaje - NO es posible cambiarlo
    private static final double PERCENTAGE = 10.0; // Valor fijo de porcentaje

    private final Random random = new Random();

    @Override
    public double getPercentage() throws RuntimeException {

        log.info("Obteniendo el porcentaje externo");
        if (random.nextInt(100 ) < 20){
            log.error("Error al obtener el porcentaje externo");
            throw new RuntimeException("Error al obtener el porcentaje externo");
        }
        log.info("Porcentaje externo obtenido correctamente:{}", PERCENTAGE);
        try {
            Thread.sleep(1000); // Simula un retraso de 1 segundo
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("El servicio del porcentaje externo fue interrumpido");
        }
        return PERCENTAGE;
    }



}
