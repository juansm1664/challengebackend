package com.juandavid.springboot.challengebackend.dto;

public class CalculationResponseDTO {

    private Double result;

    public CalculationResponseDTO() {
    }

    public CalculationResponseDTO(Double result) {
        this.result = result;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }


}
