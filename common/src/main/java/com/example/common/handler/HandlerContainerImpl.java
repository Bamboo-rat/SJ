package com.example.common.handler;

import com.example.common.exception.AppException;
import com.example.common.exception.ErrorCode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class HandlerContainerImpl implements HandlerContainer {

    // Key là Class của Request (ví dụ: LoginRequest.class)
    private final Map<Class<?>, BaseHandler<?, ?>> handlerMap = new HashMap<>();

    // Spring sẽ inject tất cả các class impl BaseHandler vào đây
    public HandlerContainerImpl(List<BaseHandler<?, ?>> handlers) {
        for (BaseHandler<?, ?> handler : handlers) {
            handlerMap.put(handler.getRequestType(), handler);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> R handle(T request) {
        BaseHandler<T, R> handler = (BaseHandler<T, R>) handlerMap.get(request.getClass());

        if (handler == null) {
            throw new AppException(ErrorCode.HANDLER_NOT_FOUND);
        }

        return handler.execute(request);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, R> PageSupport<R> handlePagination(T request) {
        // Tương tự handle nhưng cast kết quả về PageSupport
        return (PageSupport<R>) handle(request);
    }
}
