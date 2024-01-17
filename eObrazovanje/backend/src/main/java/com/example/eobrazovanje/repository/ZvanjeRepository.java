package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.Zvanje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZvanjeRepository extends JpaRepository<Zvanje, Long> {
}
