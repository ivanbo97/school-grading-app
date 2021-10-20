package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkListDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.service.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.MARK_BASE_URL;

@RestController
@RequestMapping(MARK_BASE_URL)
public class MarkController {

    private MarkService markService;

    @Autowired
    public MarkController(MarkService markService) {
        this.markService = markService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    MarkListDTO findAllMarks() {
        return markService.findAllMarks();
    }

    @GetMapping("/{markId}")
    @ResponseStatus(HttpStatus.OK)
    MarkDTO findMarkById(@PathVariable String markId) {
        return markService.findMarkById(Long.valueOf(markId));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    MarkDTO saveMark(@RequestBody MarkDTO markDTO) throws EntityValidationException {
        return markService.saveMark(markDTO);
    }

    @PutMapping("/{markId}")
    @ResponseStatus(HttpStatus.OK)
    MarkDTO updateMark(@PathVariable String markId,
                       @RequestBody MarkDTO markDTO) throws EntityValidationException {

        return markService.updateMark(Long.valueOf(markId), markDTO);
    }

    @DeleteMapping("/{markId}")
    @ResponseStatus(HttpStatus.OK)
    void deleteMark(@PathVariable String markId) {
        markService.deleteMarkById(Long.valueOf(markId));
    }
}
