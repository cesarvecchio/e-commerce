package org.com.adjt.gestaoitens.application.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.com.adjt.gestaoitens.domain.service.ItemService;
import org.com.adjt.gestaoitens.exceptions.ControllerExceptionHandler;
import org.com.adjt.gestaoitens.utils.BuildItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);

        ItemController itemController = new ItemController(itemService);

        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void deveCriarItem() throws Exception {
        var itemRequest = BuildItem.itemRequest();
        var itemResponse = BuildItem.itemResponse();

        when(itemService.criar(itemRequest)).thenReturn(itemResponse);

        mockMvc.perform(post("/itens")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRequest)))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    String json = result .getResponse().getContentAsString(StandardCharsets.UTF_8);
                    assertEquals(asJsonString(itemResponse), json);
                });
    }

    @Test
    void deveBuscarPorId() throws Exception {
        var id = BuildItem.id();
        var itemResponse = BuildItem.itemResponse();

        when(itemService.buscarPorId(id)).thenReturn(itemResponse);

        mockMvc.perform(get("/itens/{id}", id))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result .getResponse().getContentAsString(StandardCharsets.UTF_8);
                    assertEquals(asJsonString(itemResponse), json);
                });
    }

    @Test
    void deveBuscarTodos() throws Exception {
        var itemResponseList = List.of(BuildItem.itemResponse());

        when(itemService.buscarTodos()).thenReturn(itemResponseList);

        mockMvc.perform(get("/itens"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result .getResponse().getContentAsString(StandardCharsets.UTF_8);
                    assertEquals(asJsonString(itemResponseList), json);
                });
    }

    @Test
    void deveAtualizar() throws Exception {
        var id = BuildItem.id();
        var itemRequest = BuildItem.itemRequest();
        var itemResponse = BuildItem.itemResponse();

        when(itemService.atualizar(id, itemRequest)).thenReturn(itemResponse);

        mockMvc.perform(put("/itens/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(itemRequest)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    assertEquals(asJsonString(itemResponse), json);
                });
    }

    @Test
    void deveDeletarPorId() throws Exception {
        var id = BuildItem.id();
        var itemResponse = BuildItem.itemResponse();

        doNothing().when(itemService).deletarPorId(id);

        mockMvc.perform(delete("/itens/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    assertTrue(ObjectUtils.isEmpty(json));
                });
    }

    @Test
    void deveDarBaixaNoEstoque() throws Exception {
        var id = BuildItem.id();
        var quantidade = 1;
        var itemResponse = BuildItem.itemResponse();

        when(itemService.baixaEstoque(id, quantidade)).thenReturn(itemResponse);

        mockMvc.perform(put("/itens/baixa-estoque/{id}/{quantidade}", id, quantidade))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    assertEquals(asJsonString(itemResponse), json);
                });
    }

    @Test
    void deveDarEntradaNoEstoque() throws Exception {
        var id = BuildItem.id();
        var quantidade = 1;
        var itemResponse = BuildItem.itemResponse();

        when(itemService.entradaEstoque(id, quantidade)).thenReturn(itemResponse);

        mockMvc.perform(put("/itens/entrada-estoque/{id}/{quantidade}", id, quantidade))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
                    assertEquals(asJsonString(itemResponse), json);
                });
    }

    public static String asJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
