package ru.job4j.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.service.PersonService;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PersonService persons;

    @Test
    void findAll() throws Exception {
        this.mockMvc
                .perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(persons).findAll();
    }

    @Test
    void findById() throws Exception {
        Person stub = new Person(1, "l1", "p1");
        when(persons.findById(1)).thenReturn(Optional.of(stub));
        String jsonResult = this.mockMvc
                .perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(persons).findById(1);
        MatcherAssert.assertThat(mapper.readValue(jsonResult, Person.class), is(stub));
    }

    @Test
    void notfoundById() throws Exception {
        this.mockMvc
                .perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(persons).findById(1);
    }

    @Test
    void create() throws Exception {
        Person stub = new Person(0, "l1", "p1");
        Person createdPerson = new Person(1, stub.getLogin(), stub.getPassword());
        when(persons.create(stub)).thenReturn(createdPerson);
        String jsonResult = this.mockMvc
                .perform(post("/person/")
                        .content(mapper.writeValueAsString(stub))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(persons).create(stub);
        MatcherAssert.assertThat(mapper.readValue(jsonResult, Person.class), is(createdPerson));
    }

    @Test
    void update() throws Exception {
        Person stub = new Person(1, "l2", "p1");
        this.mockMvc
                .perform(put("/person/")
                        .content(mapper.writeValueAsString(stub))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk());
        verify(persons).update(stub);
    }

    @Test
    void deleteById() throws Exception {
        this.mockMvc
                .perform(delete("/person/1"))
                .andDo(print())
                .andExpect(status().isOk());
        verify(persons).delete(1);
    }
}
