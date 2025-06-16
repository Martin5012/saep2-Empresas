package com.juan.curso.springboot.webapp.saep.repository;

import com.juan.curso.springboot.webapp.saep.model.Aprendices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AprendicesRepository extends JpaRepository<Aprendices,Long> {
}
