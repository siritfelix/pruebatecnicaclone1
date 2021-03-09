package com.superheroes.pruebatecnica.services;

import java.util.List;
import java.util.Optional;

import com.superheroes.pruebatecnica.aspect.Time;
import com.superheroes.pruebatecnica.repository.SuperHeroeRepository;
import com.superheroes.pruebatecnica.repository.model.SuperHeroe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperHeroeServiceImple implements SuperHeroeService {

    @Autowired
    private SuperHeroeRepository superHeroeRepository;

    @Override
    public List<SuperHeroe> ListarTodos() {

        return superHeroeRepository.findAll();

    }

    @Override
    public Optional<SuperHeroe> BuscarPorId(Integer id) {
        
        return superHeroeRepository.findById(id);
    }

    @Override
    public Optional<SuperHeroe> Modificar(SuperHeroe superHeroe) {

        if (BuscarPorId(superHeroe.getId()).isPresent())
            return Optional.of(superHeroeRepository.save(superHeroe));
        else
            return Optional.empty();

    }

    @Override
    public Boolean Eliminar(Integer id) {
        Optional<SuperHeroe> superHeroe = BuscarPorId(id);
        if (superHeroe.isPresent()) {
            superHeroeRepository.delete(superHeroe.get());
            return true;
        } else
            return false;

    }

    @Override
    public List<SuperHeroe> ListarPorCoincidenciaTodos(String nombre) {
        return superHeroeRepository.findByNombreContaining(nombre);
    }
}
