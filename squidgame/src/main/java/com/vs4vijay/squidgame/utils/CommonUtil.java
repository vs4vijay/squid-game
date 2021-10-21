package com.vs4vijay.squidgame.utils;

import org.springframework.data.domain.Page;

import java.util.Map;

public class CommonUtil {

    public static Map buildMetadataFromPage(Page page) {
        Map metadata = Map.of(
                "currentPage", page.getNumber(),
                "totalPages", page.getTotalPages(),
                "pageSize", page.getSize(),
                "totalElements", page.getTotalElements()
        );
        return metadata;
    }
}
