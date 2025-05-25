package com.ptbdams.apicrud.repository;

import com.ptbdams.apicrud.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}