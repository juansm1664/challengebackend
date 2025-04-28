package com.juandavid.springboot.challengebackend.service;


import com.juandavid.springboot.challengebackend.entity.CallHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private static final Logger log = LoggerFactory.getLogger(CalculatorServiceImpl.class);

    private static final String PERCENTAGE_CACHE = "percentageCache";

    private static final String PERCENTAGE_CACHE_KEY = "externalercentage";

    private final ExternalPercentageService externalPercentageService;

    private final HistoryService historyService;

    private final CacheManager cacheManager;

    // Variable para almacenar el último porcentaje válido conocido fuera de la caché principal
    // Esto es un fallback simple. Una solución más robusta podría usar un caché secundario o persistencia.
    private Double lastKnownGoodPercentage = null;

    @Autowired
    public CalculatorServiceImpl(ExternalPercentageService externalPercentageService,
                                 HistoryService historyService, CacheManager cacheManager) {
        this.externalPercentageService = externalPercentageService;
        this.historyService = historyService;
        this.cacheManager = cacheManager;
    }

    @Override
    public double calculateSumPercentage(double num1, double num2) {
        log.info("Calculo requerido para num1: {} and num2:{}", num1, num2);
        String parameters = String.format("num1: %s, num2: %s", num1, num2);
        double percentage = 0;
        String erroMessage = null;
        double result = Double.NaN; // Se usa NAN como indicador inicial de no calculado
        int statusCode = 200; //Se asume el éxito inicial

        try {
            //1. Obtener el porcentaje de la cache
            percentage = getPercentageValue();
            log.info("Usando el porcentaje:{}", percentage);

            //2. Calcular la suma
            double sum = num1 + num2;
            result = sum * (1 + percentage / 100.0);
            log.info("Resultado de la suma:{}", result);


        } catch (Exception e) {   /*
         *  Error interno del servidor
         * En caso de error, el 'result' permanecerá como NaN o el último valor antes del error
         */
            log.error("Se ha presentado un error al calcular la suma: {}", e.getMessage(), e);
            erroMessage = "Error procesando la solicitud" + e.getMessage();
            statusCode = 500;


        } finally {

            //3. Registrar la llamada de forma asíncrona
            log.debug("Preparando para guardar el llamado historico asíncrono..");
            CallHistory history = new CallHistory();
            history.setTimestamp(LocalDateTime.now());
            history.setEndpoint("/api/calculator/sum");
            history.setParameters(parameters);
            history.setStatusCode(statusCode);

            history.setHttpMethod("POST");

            if (erroMessage != null) {
                history.setError(erroMessage.substring(0, Math.min(erroMessage.length(), 255)));
            } else if (!Double.isNaN(result)) {
                history.setResponse(String.format("%.2f", result));
            } else {
                history.setError("El Calculo no se ha realizado");
            }

            historyService.saveCallAsync(history);

        }

        if (erroMessage != null) {
            throw new CalculationException("El calculo ha fallado" + erroMessage);
        }

        return result;

    }

    /**
     * Obtiene el valor del porcentaje, priorizando la caché.
     * Si la caché está vacía o expirada, intenta obtenerlo del servicio externo.
     * Si el servicio externo falla, usa el último valor bueno conocido (si existe).
     * Actualiza la caché con el valor obtenido (incluso si es el de fallback).
     *
     * return El valor del porcentaje a utilizar.
     * throws RuntimeException Si no se puede obtener ningún valor (ni caché, ni externo, ni fallback).
     */
    private double getPercentageValue() {

        Cache percentageCache = cacheManager.getCache(PERCENTAGE_CACHE);
        if (percentageCache == null) {
            log.error("Cache '{}' not found. Check cache configuration.", PERCENTAGE_CACHE);
            // Podrías intentar obtenerlo directamente del servicio externo como último recurso
            // o lanzar un error de configuración. Por simplicidad, lanzamos error.
            throw new IllegalStateException("Required cache not configured: " + PERCENTAGE_CACHE);
        }

        // Intentar obtener de la caché primero
        // Nota: La clave debe coincidir con la usada en @Cacheable/@CachePut si las usaras,
        // aquí usamos la constante definida. Necesitamos el valor de la clave SpEL.
        String cacheKey = "externalPercentage"; // La clave real sin las comillas SpEL
        Cache.ValueWrapper cachedValue = percentageCache.get(cacheKey);

        if (cachedValue != null) {
            log.info("Percentage found in cache: {}", cachedValue.get());
            return (Double) cachedValue.get();
        }
        // Cache miss: Intentar obtener del servicio externo
        log.info("Cache miss. Attempting to fetch percentage from external service...");
        try {
            double freshPercentage = externalPercentageService.getPercentage();
            log.info("Successfully fetched fresh percentage: {}", freshPercentage);
            this.lastKnownGoodPercentage = freshPercentage; // Actualizar el último valor bueno
            percentageCache.put(cacheKey, freshPercentage); // Poner en caché manualmente
            return freshPercentage;
        } catch (Exception e) {
            log.warn("Failed to fetch percentage from external service: {}. Attempting fallback.", e.getMessage());
            // Fallo del servicio externo: Intentar usar el último valor bueno conocido
            if (this.lastKnownGoodPercentage != null) {
                log.warn("Using last known good percentage as fallback: {}", this.lastKnownGoodPercentage);
                // ¡Importante! También ponemos el valor de fallback en la caché
                // para que esté disponible durante los próximos 30 minutos (o TTL configurado)
                // incluso si el servicio externo sigue fallando.
                percentageCache.put(cacheKey, this.lastKnownGoodPercentage);
                return this.lastKnownGoodPercentage;
            } else {
                // Fallo externo Y no hay valor de fallback
                log.error("External service failed and no fallback percentage available.");
                throw new RuntimeException("Unable to retrieve percentage: External service failed and no fallback value exists.", e);
            }
        }

    }
        public static class CalculationException extends RuntimeException {
            public CalculationException(String message) {
                super(message);
            }
        }

}