package ru.izelinov.draganddrop.adapters.outbound.item.persistence.spring;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.izelinov.draganddrop.adapters.outbound.item.persistence.dto.ItemEntity;

@Repository
public interface ItemJpaRepository extends JpaRepository<ItemEntity, UUID> {

  @Query(
      nativeQuery = true,
      value = """
            SELECT COUNT(*)
            FROM items
            WHERE position BETWEEN :from AND :to
          """
  )
  long countBetweenPositions(
      @Param("from") long from,
      @Param("to") long to
  );

  @Query(
      nativeQuery = true,
      value = """
            SELECT *
            FROM items
            WHERE position < :position
            ORDER BY position DESC
            LIMIT 1
            FOR UPDATE
          """
  )
  Optional<ItemEntity> findFirstByPositionUp(
      @Param("position") Long position
  );

  @Query(
      nativeQuery = true,
      value = """
            SELECT *
            FROM items
            WHERE position > :position
            ORDER BY position
            LIMIT 1
            FOR UPDATE
          """
  )
  Optional<ItemEntity> findFirstByPositionDown(
      @Param("position") Long position
  );

  @Query(
      nativeQuery = true,
      value = """
            SELECT *
            FROM items
            WHERE position > :lastPosition
            ORDER BY position
            LIMIT :limit
          """
  )
  List<ItemEntity> findAll(
      @Param("limit") long limit,
      @Param("lastPosition") long lastPosition
  );

  @Query(
      nativeQuery = true,
      value = """
            SELECT *
            FROM items
            WHERE position BETWEEN :from AND :to
            ORDER BY position
            FOR UPDATE
          """
  )
  List<ItemEntity> findAllBetweenPositions(
      @Param("from") long from,
      @Param("to") long to
  );
}
