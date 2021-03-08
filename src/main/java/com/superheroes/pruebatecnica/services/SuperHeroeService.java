package com.superheroes.pruebatecnica.services;

import java.util.List;
import java.util.Optional;

import com.superheroes.pruebatecnica.repository.model.SuperHeroe;

public interface SuperHeroeService {

    public List<SuperHeroe> ListarTodos();

    public Optional<SuperHeroe> BuscarPorId(Integer id);

    public Optional<SuperHeroe> Modificar(SuperHeroe superHeroe);

    public Boolean Eliminar(Integer id);

    public List<SuperHeroe> ListarPorCoincidenciaTodos(String nombre);

}
