package com.superheroes.pruebatecnica.controllers;

import com.superheroes.pruebatecnica.aspect.Time;
import com.superheroes.pruebatecnica.dto.SuperHeroeDTO;
import com.superheroes.pruebatecnica.exceptions.NotFoundException;
import com.superheroes.pruebatecnica.repository.model.SuperHeroe;
import com.superheroes.pruebatecnica.services.SuperHeroeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public static final String MODIFICAR = "/modificar";
    public static final String ELIMINAR = "/eliminar";
    @Autowired
    private SuperHeroeService superHeroeService;

    @Time
    @GetMapping(LISTAR)

    public ResponseEntity<?> ListarTodos() {

        return new ResponseEntity<>(superHeroeService.ListarTodos(), HttpStatus.OK);
    }

    @Time
    @GetMapping(BUSCAR_POR_ID)

    public ResponseEntity<?> BuscarPorID(@PathVariable Integer id) {

        if (superHeroeService.BuscarPorId(id).isPresent())
            return new ResponseEntity<>(superHeroeService.BuscarPorId(id).get(), HttpStatus.OK);
        else
            throw new NotFoundException("SuperHeroe no encontrado");
    }

    @Time
    @GetMapping(BUSCAR_POR_NOMBRE)
    public ResponseEntity<?> BuscarPorNombre(@PathVariable String nombre) {

        if (!superHeroeService.ListarPorCoincidenciaTodos(nombre).isEmpty())
            return new ResponseEntity<>(superHeroeService.ListarPorCoincidenciaTodos(nombre), HttpStatus.OK);
        else
            throw new NotFoundException("SuperHeroe no encontrado");
    }

    @Time
    @PutMapping(MODIFICAR)
    public ResponseEntity<?> Modificar(@RequestBody SuperHeroeDTO superHeroeDTO) {

        SuperHeroe superHeroe = new SuperHeroe(superHeroeDTO.getId(), superHeroeDTO.getNombre());
        if (superHeroeService.Modificar(superHeroe).isPresent())
            return new ResponseEntity<>(superHeroeDTO, HttpStatus.OK);
        else
            throw new NotFoundException("SuperHeroe no encontrado");
    }

    @Time
    @DeleteMapping(ELIMINAR)
    public ResponseEntity<?> Eliminar(@RequestParam Integer id) {

        if (superHeroeService.Eliminar(id))
            return new ResponseEntity<>("Superheroe", HttpStatus.OK);
        else
            throw new NotFoundException("SuperHeroe no encontrado");
    }

}
