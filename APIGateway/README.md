```

```

# APIGateway

## Vai trò

- Entry point duy nhất cho client
- Route request đến AuthService/WalletService
- Áp dụng security JWT ở tầng Gateway

## Port

- `8080`

## Route config

Khai báo trong `src/main/resources/application.properties` với prefix:

- `spring.cloud.gateway.server.webmvc.routes[...]`

## Security

- `SecurityConfig` tại `src/main/java/com/example/apigateway/security/SecurityConfig.java`
- JWT filter dùng lại từ module `common`
- CORS dùng bean chung từ `common`

## Tài liệu chi tiết

- `SECURITY_GATEWAY.md`
