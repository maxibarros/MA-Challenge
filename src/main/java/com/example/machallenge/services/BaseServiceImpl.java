package com.example.machallenge.services;

import com.example.machallenge.models.entities.BaseEntity;
import com.example.machallenge.repositories.BaseRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;



public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    private BaseRepository<T> baseRepository;

    public BaseServiceImpl(BaseRepository<T> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional
    public T save(T entity) {
        return (T) baseRepository.save(entity);
    }

    @Override
    public List<T> findAll() {
        return baseRepository.findAll();
    }

    @Override
    public Optional<T> findById(Long id) {
        return baseRepository.findById(id);
    }

    @Override
    @Transactional
    public T update(T entity) {
        return (T) baseRepository.save(entity);
    }

    @Override
    @Transactional
    public T updateById(T entity, Long id) {
        Optional<T> optional = baseRepository.findById(id);
        if(optional.isPresent()){
            return (T) baseRepository.save(entity);
        }else{
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(T entity) {
        baseRepository.delete(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        baseRepository.deleteById(id);
    }

    @Override
    public T reload(T entity, int depth) {
        /*
        if (entity instanceof BaseEntity) {
            Optional<T> optional = baseRepository.findById(((BaseEntity) entity).getId());
            optional.isPresent();
            entity = (T) findById(((BaseEntity) entity).getId());
        } else {
            entity = (T) save(entity);
        }
        return Collections.reload(entity, depth);
         */
        return null;
    }

    @Override
    public List<T> reloadList(List<T> entities, int depth) {
        /*
        List<S> result = new ArrayList<S>();
        for (S entity : entities) {
            result.add(reload(entity,depth));
        }
        return result;
         */
        return null;
    }
}
