package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.MarkMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkListDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityNotFoundCustomException;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.MarkRepository;
import com.ivanboyukliev.schoolgradingapp.validation.BaseNamedEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ERROR_MARK_NOT_FOUND;

@Service
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    private final MarkMapper markMapper;

    private final BaseNamedEntityValidator entityValidator;

    @Autowired
    public MarkServiceImpl(MarkRepository markRepository, MarkMapper markMapper, BaseNamedEntityValidator entityValidator) {
        this.markRepository = markRepository;
        this.markMapper = markMapper;
        this.entityValidator = entityValidator;
    }

    @Override
    public MarkListDTO findAllMarks() {
        return new MarkListDTO(markRepository.findAll()
                .stream()
                .map(markMapper::markToMarkDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public MarkDTO findMarkById(Long id) {
        return markRepository.findById(id)
                .map(markMapper::markToMarkDTO)
                .orElseThrow(()-> new EntityNotFoundCustomException(
                        String.format(ERROR_MARK_NOT_FOUND,id)
                ));
    }

    @Override
    public MarkDTO saveMark(MarkDTO markDTO) throws EntityValidationException {
        return null;
    }

    @Override
    public MarkDTO updateMark(Long id, MarkDTO markDTO) throws EntityValidationException {
        return null;
    }

    @Override
    public void deleteMarkById(Long id) {

    }
}
