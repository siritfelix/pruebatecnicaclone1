package com.superheroes.pruebatecnica.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.superheroes.pruebatecnica.repository.SuperHeroeRepository;
import com.superheroes.pruebatecnica.repository.model.SuperHeroe;
import com.superheroes.pruebatecnica.services.SuperHeroeService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SuperHeroeControllerTest {
    public static final String URI = "/api/v1";
    public static final String LISTAR = "/listar-todos";
    public static final String BUSCAR_POR_ID = "/buscar-por-id/";
    public static final String BUSCAR_POR_NOMBRE = "/buscar-por-nombre/";
    public static final String MODIFICAR = "/modificar";
    public static final String ELIMINAR = "/eliminar";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private SuperHeroeService superHeroeService;
    @MockBean
    private SuperHeroeRepository superHeroeRepository;

    @BeforeEach
    public void load() {
        List<SuperHeroe> superHeroes = new ArrayList<>();
        superHeroes.add(new SuperHeroe(1, "Superman"));
        superHeroes.add(new SuperHeroe(2, "Spiderman"));
        superHeroes.add(new SuperHeroe(3, "Thor"));
        superHeroes.add(new SuperHeroe(4, "Ironman"));
        when(superHeroeRepository.findAll()).thenReturn(superHeroes);
        when(superHeroeRepository.findById(1)).thenReturn(Optional.of(new SuperHeroe(1, "Superman")));
        when(superHeroeRepository.findById(10)).thenReturn(Optional.empty());
        when(superHeroeRepository.save(new SuperHeroe(1, "Ironman"))).thenReturn(new SuperHeroe(1, "Ironman"));

        List<SuperHeroe> superHeroes2 = new ArrayList<>();
        superHeroes2.add(new SuperHeroe(1, "Superman"));
        superHeroes2.add(new SuperHeroe(2, "Spiderman"));
        superHeroes2.add(new SuperHeroe(4, "Ironman"));
        when(superHeroeRepository.findByNombreContaining("man")).thenReturn(superHeroes2);
    }

    @Test
    public void Exepcion() throws Exception {

        when(superHeroeService.ListarTodos()).thenThrow(new InternalError());
        this.mockMvc.perform(get(URI + LISTAR).header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ==")).andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje", Matchers.is("Error Interno del servidor")));
    }

    @Test
    public void ListarTodos() throws Exception {

        this.mockMvc.perform(get(URI + LISTAR).header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ==")).andDo(print())
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].nombre", Matchers.is("Superman")));
    }

    @Test
    public void BuscarPorID() throws Exception {

        this.mockMvc.perform(get(URI + BUSCAR_POR_ID + "1").header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ=="))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre", Matchers.is("Superman")));
    }

    @Test
    public void BuscarPorIDNot() throws Exception {

        this.mockMvc.perform(get(URI + BUSCAR_POR_ID + 10).header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ=="))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje", Matchers.is("SuperHeroe no encontrado")));

    }

    @Test
    public void BuscarPorNombre() throws Exception {

        this.mockMvc
                .perform(get(URI + BUSCAR_POR_NOMBRE + "man").header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ=="))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].nombre", Matchers.is("Superman")));
    }

    @Test
    public void BuscarPorNombreNot() throws Exception {

        this.mockMvc
                .perform(get(URI + BUSCAR_POR_NOMBRE + "xxx").header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ=="))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje", Matchers.is("SuperHeroe no encontrado")));
    }

    @Test
    public void ModificarNot() throws Exception {

        this.mockMvc.perform(put(URI + MODIFICAR).header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ=="))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje", Matchers.is("Error en los parametros")));

    }

    @Test
    public void ModificarNotFound() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc
                .perform(put(URI + MODIFICAR).header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ==")
                        .content(mapper.writeValueAsString(new SuperHeroe(10, "Superman")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje", Matchers.is("SuperHeroe no encontrado")));

    }

    @Test
    public void Eliminar() throws Exception {

        this.mockMvc.perform(
                delete(URI + ELIMINAR).header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ==").param("id", "1"))
                .andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void EliminarNot() throws Exception {
        this.mockMvc.perform(
                delete(URI + ELIMINAR).header("Authorization", "Basic c2lyaXRmZWxpeDoxMjM0NQ==").param("id", "10"))
                .andDo(print()).andExpect(status().isNotFound());

    }
}
