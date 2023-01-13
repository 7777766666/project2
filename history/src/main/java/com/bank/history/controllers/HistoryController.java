package com.bank.history.controllers;

import com.bank.history.models.History;
import com.bank.history.models.dto.HistoryDTO;
import com.bank.history.services.abstracts.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getHistoryById(@PathVariable("id") Long id) {
        HistoryDTO historyDTO = historyService.getById(id);
        if (historyDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("История не найдена!");
        }
        return ResponseEntity.ok(historyDTO);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> getAll() {
        List<HistoryDTO> historyList = historyService.getAllHistories();
        if (historyList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Истории не найдены!");
        }
        return ResponseEntity.ok(historyList);
    }

    @PostMapping(path = "/new")
    public void addHistory(@RequestBody History history) {
        historyService.save(history);

    }

    @PatchMapping(path = "/{id}/update")
    public ResponseEntity<?> updateHistory(@PathVariable("id") Long id, @RequestBody History history) {
        boolean update = historyService.update(id, history);
        if (update) {
            return new ResponseEntity<>("История успешно обновлена!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Данная история не найдена!", HttpStatus.BAD_REQUEST);
    }


}
