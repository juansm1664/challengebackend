package com.juandavid.springboot.challengebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import com.juandavid.springboot.challengebackend.entity.CallHistory;
import com.juandavid.springboot.challengebackend.service.HistoryService;


@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private final HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }
    /**
     * Este método se obtiene el historial de llamadas
     *  Usando Pageable para la paginación
     */

    @GetMapping
    public ResponseEntity<Page<CallHistory>> getCallHistory(
           @PageableDefault(size = 10, sort = "timestamp") Pageable pageable) {
        Page<CallHistory> callHistoryPage = historyService.getHistory(pageable);

        return ResponseEntity.ok(callHistoryPage);
    }








}
