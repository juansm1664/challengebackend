package com.juandavid.springboot.challengebackend.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "call_history")
public class CallHistory    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;


    @Column(nullable = false)
    private String endpoint;

    //parametros de la petición sirve para
    @Column
    private String parameters;

    @Column
    private String response;

    @Column
    private String error;

    @Column(name = "http_method", nullable = false)
    private String httpMethod;

    @Column(name = "status_code", nullable = false)
    private Integer statusCode;

    //Constructor por defecto que es necesario vacio para JPA
    public CallHistory() {

    }
    public CallHistory(Long id, LocalDateTime timestamp,
                       String endpoint, String parameters,
                       String error, String response,
                       String httpMethod, Integer statusCode) {
        this.id = id;
        this.timestamp = timestamp;
        this.endpoint = endpoint;
        this.parameters = parameters;
        this.error = error;
        this.response = response;
        this.httpMethod = httpMethod;
        this.statusCode = statusCode;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }


}
