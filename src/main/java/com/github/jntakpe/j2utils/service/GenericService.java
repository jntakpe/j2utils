package com.github.jntakpe.j2utils.service;

import com.github.jntakpe.j2utils.domain.GenericDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implémentation des services usuels
 *
 * @author jntakpe
 */
public abstract class GenericService<T extends GenericDomain> {


    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private JpaRepository<T, Integer> jpaRepository;

    @Transactional(readOnly = true)
    public long count() {
        return jpaRepository.count();
    }

    @Transactional(readOnly = true)
    public T findOne(Integer id) {
        return jpaRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    @Transactional
    public void delete(Integer id) {
        jpaRepository.delete(id);
    }

    @Transactional
    public T save(T entity) {
        return jpaRepository.save(entity);
    }
}
