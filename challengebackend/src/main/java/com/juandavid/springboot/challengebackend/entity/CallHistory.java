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
}
