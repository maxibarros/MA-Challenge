package com.example.machallenge.services;

import com.example.machallenge.models.entities.BaseEntity;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity> {

    @Transactional
    public abstract T save(T entity);
    public abstract List<T> findAll();
    public abstract Optional<T> findById(Long id);
    @Transactional
    public abstract T update(T entity);
    @Transactional
    public abstract T updateById(T entity, Long id);
    @Transactional
    public abstract void delete(T entity);
    @Transactional
    public abstract void deleteById(Long id);
    public T reload(T entity, int depth);
    public List<T> reloadList(List<T> entities, int depth);

}
