package com.example.common.handler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageSupport<T> {
    private final List<T> items;
    private final long total;
    private final int page;
    private final int size;
}
