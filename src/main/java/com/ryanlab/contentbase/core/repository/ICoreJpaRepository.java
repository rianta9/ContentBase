package com.ryanlab.contentbase.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ryanlab.contentbase.core.entity.ICoreEntity;

public interface ICoreJpaRepository<E extends ICoreEntity<E>, ID>
  extends JpaRepository<E, ID> {
  /**
   * Save Entity
   * 
   * @param entity
   * @return
   */
  E save(E entity);

  /**
   * Save And Flush
   * 
   * @param entity
   * @return
   */
  E saveAndFlush(E entity);

  /**
   * Save List Entity
   * 
   * @param entities
   * @return
   */
  List<E> saveAll(List<E> entities);

  /**
   * Save All And Flush
   * 
   * @param entities
   * @return
   */
  List<E> saveAllAndFlush(List<E> entities);

  /**
   * Find By Id
   * 
   * @param entityId
   * @return
   */
  Optional<E> findById(ID entityId);

  /**
   * Find All
   * 
   * @return
   */
  List<E> findAll();

  /**
   * Delete By Entity
   * 
   * @param entity
   */
  void delete(E entity);

  /**
   * Delete All
   * 
   * @param entities
   */
  void deleteAll(List<E> entities);

  /**
   * Delete All By IDs
   * 
   * @param entityIds
   */
  void deleteAllByIds(List<ID> entityIds);
}
