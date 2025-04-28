package com.juandavid.springboot.challengebackend.controller;


import com.juandavid.springboot.challengebackend.dto.CalculationRequestDTO;
import com.juandavid.springboot.challengebackend.dto.CalculationResponseDTO;
import com.juandavid.springboot.challengebackend.dto.ErrorResponse;
import com.juandavid.springboot.challengebackend.service.CalculatorService;
import com.juandavid.springboot.challengebackend.service.CalculatorServiceImpl.CalculationException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private static final Logger log = LoggerFactory.getLogger(CalculatorController.class);

    private final CalculatorService calculatorService;


    @Autowired
    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/sum")
    public ResponseEntity<CalculationResponseDTO> calculateSum(@RequestBody CalculationRequestDTO calculationRequestDTO) {
        log.info("Recibida solicitud de cálculo: num1: {}, num2:{}", calculationRequestDTO.getNum1(), calculationRequestDTO.getNum2());

        if(calculationRequestDTO.getNum1() == null || calculationRequestDTO.getNum2() == null) {
            log.warn("Entada inválida: num1 o num2 son nulos");

            throw new IllegalArgumentException("Los parámetros num1 y num2 son requeridos");

        }
        double result = calculatorService.calculateSumPercentage(calculationRequestDTO.getNum1(), calculationRequestDTO.getNum2());

        CalculationResponseDTO response = new CalculationResponseDTO(result);
        log.info("Resultado del cálculo: {}", response);

        return ResponseEntity.ok(response);
    }

    /**
     * Manejador de excepciones específico para CalculationException.
     * Devuelve una respuesta de error estructurada con estado 500.
     */
    @ExceptionHandler(CalculationException.class)
    public ResponseEntity<ErrorResponse> hadleCalculationException(CalculationException ex, HttpServletRequest httpRequest){
        log.error("Error en el cálculo: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Cálculo fallido", ex.getMessage(), httpRequest.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejador de excepciones para argumentos inválidos (ej. num1/num2 nulos).
     * Devuelve una respuesta de error estructurada con estado 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> hadleCalculationException(IllegalArgumentException ex, HttpServletRequest httpRequest){
        log.warn("El argumento es incorrecto: {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Entrada es invalida", ex.getMessage(), httpRequest.getRequestURI());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manejador de excepciones genérico para cualquier otra excepción no capturada.
     * Devuelve una respuesta de error estructurada con estado 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest httpRequest) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error inesperado",
                "Ocurrió un error inesperado en el servidor.",
                httpRequest.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
