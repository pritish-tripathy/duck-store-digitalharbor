package com.duckstore.service;

import com.duckstore.dto.AddDuckRequest;
import com.duckstore.dto.DuckResponse;
import com.duckstore.dto.UpdateDuckRequest;
import com.duckstore.entity.Duck;
import com.duckstore.exception.DuckNotFoundException;
import com.duckstore.repository.DuckRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DuckService {

    private final DuckRepository duckRepository;

    public DuckService(DuckRepository duckRepository) {
        this.duckRepository = duckRepository;
    }

    @Transactional
    public void addDuck(AddDuckRequest request) {

        duckRepository.addOrMergeDuck(
                request.getColor().name(),
                request.getSize().name(),
                request.getPrice(),
                request.getQuantity()
        );
    }

    public List<DuckResponse> getAllDucks() {

        List<Duck> ducks =
                duckRepository.findByDeletedFalseOrderByQuantityAsc();

        return ducks.stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Transactional
    public void updateDuck(Integer id, UpdateDuckRequest request) {

        Duck duck = duckRepository.findById(id)
                .orElseThrow(() -> new DuckNotFoundException(id));

        duck.setPrice(request.getPrice());
        duck.setQuantity(request.getQuantity());

        duckRepository.save(duck);
    }

    @Transactional
    public void deleteDuck(Integer id) {

        int updatedRows = duckRepository.softDeleteById(id);

        if (updatedRows == 0) {
            throw new DuckNotFoundException(id);
        }
    }

    private DuckResponse convertToResponse(Duck duck) {

        return new DuckResponse(
                duck.getId(),
                duck.getColor(),
                duck.getSize(),
                duck.getPrice(),
                duck.getQuantity()
        );
    }
}