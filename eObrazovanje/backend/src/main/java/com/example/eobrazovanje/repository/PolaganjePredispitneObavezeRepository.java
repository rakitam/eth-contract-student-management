package com.example.eobrazovanje.repository;

import com.example.eobrazovanje.model.PolaganjePredispitneObaveze;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolaganjePredispitneObavezeRepository extends JpaRepository<PolaganjePredispitneObaveze, Long> {
    Page<PolaganjePredispitneObaveze> findAllByStudentUsername(Pageable pageable, String username);

}
