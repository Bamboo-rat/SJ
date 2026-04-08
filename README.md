# SJ - Microservice Wallet (hiện trạng)

## 1) Modules hiện có

- `APIGateway` (port `8080`)
- `AuthService` (port `8081`)
- `WalletService` (port `8082`)
- `common` (DTO, exception, handler pattern, JWT/CORS dùng chung)

## 2) Chức năng đã triển khai

- Gateway routing qua Spring Cloud Gateway Server MVC
- Đăng nhập và phát JWT tại AuthService (`/auth/login`)
- Endpoint health check:
	- `/auth/ping`
	- `/wallet/ping`
- Security JWT ở Gateway + service con qua `JwtAuthFilter`
- CORS tập trung trong `common`

## 3) Cấu hình route đúng (quan trọng)

Trong `APIGateway/src/main/resources/application.properties` dùng prefix:

- `spring.cloud.gateway.server.webmvc.routes[...]`

Không dùng prefix cũ `spring.cloud.gateway.mvc.routes[...]`.

## 4) Chạy dự án

Tại thư mục root:

- `mvn clean install`

Sau đó chạy từng service (3 terminal):

1. `AuthService`
2. `WalletService`
3. `APIGateway`

## 5) Test nhanh qua Gateway

- `GET http://localhost:8080/auth/ping` -> `auth-pong`
- `GET http://localhost:8080/wallet/ping` -> `wallet-pong`
- `POST http://localhost:8080/auth/login` -> lấy JWT

## 6) Roadmap (chưa triển khai)

- Nghiệp vụ wallet balance đầy đủ
- Transaction service
- Notification service async
- Redis idempotency / Saga flow
