package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.PredispitnaObaveza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PredispitnaObavezaRepository extends JpaRepository<PredispitnaObaveza, Long> {

    List<PredispitnaObaveza> getAllByPredmetId(Long predmetId);
}
