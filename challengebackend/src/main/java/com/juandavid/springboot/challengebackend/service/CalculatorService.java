package com.juandavid.springboot.challengebackend.service;

import org.springframework.stereotype.Service;

@Service
public interface CalculatorService {

    double calculateSumPercentage(double num1, double num2);
}