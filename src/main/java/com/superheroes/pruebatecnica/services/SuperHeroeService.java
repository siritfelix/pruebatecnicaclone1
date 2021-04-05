package com.superheroes.pruebatecnica.services;

import java.util.List;
import java.util.Optional;

import com.superheroes.pruebatecnica.repository.model.SuperHeroe;

public interface SuperHeroeService {

    public List<SuperHeroe> listarTodos();

    public Optional<SuperHeroe> buscarPorId(Integer id);

    public Optional<SuperHeroe> modificar(SuperHeroe superHeroe);

    public Boolean eliminar(Integer id);

    public List<SuperHeroe> listarPorCoincidenciaTodos(String nombre);

}
