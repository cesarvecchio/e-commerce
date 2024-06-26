package org.com.adjt.gestaoitens.utils;

import org.com.adjt.gestaoitens.application.controller.request.ItemRequest;
import org.com.adjt.gestaoitens.application.controller.response.ItemResponse;
import org.com.adjt.gestaoitens.domain.entity.ItemEntity;

public class BuildItem {
    public static ItemRequest itemRequest() {
        return new ItemRequest(
                "Nome",
                "Descricao",
                "Categoria",
                10.00,
                "UrlImagem",
                10
        );
    }

    public static ItemEntity itemEntity() {
        return new ItemEntity(
                "Nome",
                "Descricao",
                "Categoria",
                10.00,
                "UrlImagem",
                10
        );
    }

    public static ItemResponse itemResponse() {
        return new ItemResponse(
                id(),
                "Nome",
                "Descricao",
                "Categoria",
                10.00,
                "UrlImagem",
                10
        );
    }

    public static String id() {
        return "667a0d090d6f3383040bb4df";
    }
}
