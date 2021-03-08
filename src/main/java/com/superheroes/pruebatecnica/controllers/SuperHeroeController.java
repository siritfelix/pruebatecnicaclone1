package com.superheroes.pruebatecnica.controllers;

import com.superheroes.pruebatecnica.services.SuperHeroeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SuperHeroeController.URI)
public class SuperHeroeController {

    public static final String URI = "api/v1";
    public static final String LISTAR = "/listar-todos";
    public static final String BUSCAR_POR_ID = "/buscar-por-id/{id}";
    public static final String BUSCAR_POR_NOMBRE = "/buscar-por-nombre/{nombre}";
    @Autowired
    private SuperHeroeService superHeroeService;

    @GetMapping(LISTAR)
    public ResponseEntity<?> ListarTodos() {

        return new ResponseEntity<>(superHeroeService.ListarTodos(), HttpStatus.OK);
    }

    @GetMapping(BUSCAR_POR_ID)
    public ResponseEntity<?> BuscarPorID(@PathVariable Integer id) {

        if (superHeroeService.BuscarPorId(id).isPresent())
            return new ResponseEntity<>(superHeroeService.BuscarPorId(id).get(), HttpStatus.OK);
        else
            return new ResponseEntity<>("Superheroe no encontrado", HttpStatus.NOT_FOUND);
    }

    @GetMapping(BUSCAR_POR_NOMBRE)
    public ResponseEntity<?> BuscarPorNombre(@PathVariable String nombre) {

        if (!superHeroeService.ListarPorCoincidenciaTodos(nombre).isEmpty())
            return new ResponseEntity<>(superHeroeService.ListarPorCoincidenciaTodos(nombre), HttpStatus.OK);
        else
            return new ResponseEntity<>("Superheroe no encontrado", HttpStatus.NOT_FOUND);
    }

}
