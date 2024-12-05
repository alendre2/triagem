package com.upa.triagem_hospitalar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import com.upa.triagem_hospitalar.entity.Paciente;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT p FROM Paciente p WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Paciente> findByNome(@Param("nome") String nome);

    boolean existsByNome(String nome);
}

