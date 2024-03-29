package com.example.machallenge.controllers;

import com.example.machallenge.models.BaseEntity;
import com.example.machallenge.repositories.BaseRepository;
import com.example.machallenge.services.BaseService;
import com.example.machallenge.services.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Slf4j
public abstract class BaseController<T extends BaseEntity> {

    private BaseService<T> baseService;

    public BaseController(BaseRepository<T> baseRepository) {
        this.baseService = new BaseServiceImpl<T>(baseRepository){};
    }

    public List<T> list() {
        return baseService.findAll();
    }

    public T create(@RequestBody T entity) {
        return baseService.save(entity);
    }

    public T update(@PathVariable(value = "id") long id, @RequestBody T entity) {
        return baseService.save(entity);
    }

    public void delete(@PathVariable(value = "id") Long id) {
        baseService.deleteById(id);
    }

    public Optional<T> get(@PathVariable(value = "id") Long id) {
        return baseService.findById(id);
    }

    protected Map<String, Object> validate(BindingResult result){
        Map<String, Object> validations = new HashMap<>();
        result.getFieldErrors()
                .forEach(error -> validations.put(error.getField(), error.getDefaultMessage()));
        return validations;
    }

    @ExceptionHandler({ Exception.class})
    public ResponseEntity<?> handleException(final Exception ex) {
        final String error = "Application specific error handling";
        log.error(error, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}

