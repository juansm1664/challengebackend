package com.juandavid.springboot.challengebackend.service;


/*
*  Servicio para retornar un valor externo de porcentaje
 */

import org.springframework.stereotype.Service;

@Service
public interface ExternalPercentageService {
    double  getPercentage() throws RuntimeException;

}
