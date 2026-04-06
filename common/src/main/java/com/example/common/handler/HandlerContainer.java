package com.example.common.handler;

public interface HandlerContainer {
    <T, R> R handle(T request);

    <T, R> PageSupport<R> handlePagination(T request);
}
