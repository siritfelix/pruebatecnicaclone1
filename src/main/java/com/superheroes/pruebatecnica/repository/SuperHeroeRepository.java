package com.superheroes.pruebatecnica.repository;

import java.util.List;

import com.superheroes.pruebatecnica.repository.model.SuperHeroe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperHeroeRepository extends JpaRepository<SuperHeroe, Integer> {

    public List<SuperHeroe> findByNombreContaining(String nombre);
}
