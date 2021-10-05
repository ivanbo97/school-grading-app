package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkListDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;

public interface MarkService {

    MarkListDTO findAllMarks();

    MarkDTO findMarkById(Long id);

    MarkDTO saveMark(MarkDTO markDTO) throws EntityValidationException;

    MarkDTO updateMark(Long id, MarkDTO markDTO) throws EntityValidationException;

    void deleteMarkById(Long id);

}
