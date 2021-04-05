package com.superheroes.pruebatecnica.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.superheroes.pruebatecnica.repository.SuperHeroeRepository;
import com.superheroes.pruebatecnica.repository.model.SuperHeroe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class SuperHeroeServiceTest {

    @InjectMocks
    private SuperHeroeServiceImple superHeroeService;
    @Mock
    private SuperHeroeRepository superHeroeRepository;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void load() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(superHeroeRepository).build();
        List<SuperHeroe> superHeroes = new ArrayList<>();
        superHeroes.add(new SuperHeroe(1, "Superman"));
        superHeroes.add(new SuperHeroe(2, "Spiderman"));
        superHeroes.add(new SuperHeroe(3, "Thor"));
        superHeroes.add(new SuperHeroe(4, "Ironman"));
        when(superHeroeRepository.findAll()).thenReturn(superHeroes);
        when(superHeroeRepository.findById(1)).thenReturn(Optional.of(new SuperHeroe(1, "Superman")));
        when(superHeroeRepository.findById(10)).thenReturn(Optional.empty());
        List<SuperHeroe> superHeroes2 = new ArrayList<>();
        superHeroes2.add(new SuperHeroe(1, "Superman"));
        superHeroes2.add(new SuperHeroe(2, "Spiderman"));
        superHeroes2.add(new SuperHeroe(4, "Ironman"));
        when(superHeroeRepository.findByNombreContaining("man")).thenReturn(superHeroes2);
    }

    @Test
    public void ListarTodosTest() {
        List<SuperHeroe> superHeroes = superHeroeService.listarTodos();
        assertEquals(superHeroes.size(), 4);
    }

    @Test
    public void BuscarPorIdTest() {
        Optional<SuperHeroe> superHeroes = superHeroeService.buscarPorId(1);
        assertEquals(superHeroes.get().getNombre(), "Superman");
    }

    @Test
    public void BuscarPorIdTestNot() {
        Optional<SuperHeroe> superHeroes = superHeroeService.buscarPorId(10);
        assertEquals(superHeroes.isEmpty(), true);
    }

    @Test
    public void ModificarTest() {
        SuperHeroe superHeroe = new SuperHeroe(1, "Supergirls");
        when(superHeroeRepository.save(superHeroe)).thenReturn(superHeroe);
        Optional<SuperHeroe> superHeroes = superHeroeService.modificar(superHeroe);
        assertEquals(superHeroes.get().getNombre(), "Supergirls");
    }

    @Test
    public void EliminarTest() {

        assertEquals(superHeroeService.eliminar(1), true);
        assertEquals(superHeroeService.eliminar(10), false);
    }

    @Test
    public void ListarPorCoincidenciaTodosTest() {
        List<SuperHeroe> superHeroes = superHeroeService.listarPorCoincidenciaTodos("man");
        assertEquals(superHeroes.size(), 3);
        assertEquals(superHeroes.get(0).getNombre(), "Superman");
    }

}
