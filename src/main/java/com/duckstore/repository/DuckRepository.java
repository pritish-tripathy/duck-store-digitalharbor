package com.duckstore.repository;

import com.duckstore.entity.Duck;
import com.duckstore.entity.DuckColor;
import com.duckstore.entity.DuckSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface DuckRepository extends JpaRepository<Duck, Integer> {

    @Modifying
    @Query(
            value = "INSERT INTO ducks (color, size, price, quantity, deleted) " +
                    "VALUES (:color, :size, :price, :quantity, false) " +
                    "ON CONFLICT (color, size, price) " +
                    "WHERE deleted = FALSE " +
                    "DO UPDATE SET quantity = ducks.quantity + EXCLUDED.quantity",
            nativeQuery = true
    )
    void addOrMergeDuck(
            @Param("color") String color,
            @Param("size") String size,
            @Param("price") BigDecimal price,
            @Param("quantity") Integer quantity
    );

    List<Duck> findByDeletedFalseOrderByQuantityAsc();

    @Modifying
    @Query(
            "UPDATE Duck d " +
                    "SET d.deleted = true " +
                    "WHERE d.id = :id"
    )
    int softDeleteById(@Param("id") Integer id);

    @Query(
            "SELECT d " +
                    "FROM Duck d " +
                    "WHERE d.color = :color " +
                    "AND d.size = :size " +
                    "AND d.deleted = false " +
                    "ORDER BY d.price ASC"
    )
    List<Duck> findActiveDucksByColorAndSize(
            @Param("color") DuckColor color,
            @Param("size") DuckSize size
    );
}