package com.ryanlab.contentbase.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.ryanlab.contentbase.core.entity.ICoreEntity;

@NoRepositoryBean
public interface ICoreJpaRepository<E extends ICoreEntity<E>, ID>
  extends JpaRepository<E, ID> {
  /**
   * Find One By Id
   * 
   * @param entityId
   * @return
   */
  Optional<E> findOneById(ID entityId);

  /**
   * Delete All By IDs
   * 
   * @param entityIds
   */
  @Modifying
  @Query("DELETE FROM #{#entityName} e WHERE e.id IN :ids")
  void deleteAllByIds(@Param("ids") List<ID> entityIds);
}
