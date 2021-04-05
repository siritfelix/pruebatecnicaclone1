package com.superheroes.pruebatecnica.services;

import java.util.List;
import java.util.Optional;

import com.superheroes.pruebatecnica.repository.SuperHeroeRepository;
import com.superheroes.pruebatecnica.repository.model.SuperHeroe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperHeroeServiceImple implements SuperHeroeService {

    @Autowired
    private SuperHeroeRepository superHeroeRepository;

    @Override
    public List<SuperHeroe> listarTodos() {

        return superHeroeRepository.findAll();

    }

    @Override
    public Optional<SuperHeroe> buscarPorId(Integer id) {

        return superHeroeRepository.findById(id);
    }

    @Override
    public Optional<SuperHeroe> modificar(SuperHeroe superHeroe) {

        if (buscarPorId(superHeroe.getId()).isPresent())
            return Optional.of(superHeroeRepository.save(superHeroe));
        else
            return Optional.empty();

    }

    @Override
    public Boolean eliminar(Integer id) {
        Optional<SuperHeroe> superHeroe = buscarPorId(id);
        if (superHeroe.isPresent()) {
            superHeroeRepository.deleteById(id);
            return true;
        } else
            return false;

    }

    @Override
    public List<SuperHeroe> listarPorCoincidenciaTodos(String nombre) {
        return superHeroeRepository.findByNombreContaining(nombre);
    }
}
