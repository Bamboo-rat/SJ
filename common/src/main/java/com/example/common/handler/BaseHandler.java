package com.example.common.handler;

public interface BaseHandler<T, R> {
    R execute(T request);
    Class<T> getRequestType(); // Dùng để làm Key trong Map
}