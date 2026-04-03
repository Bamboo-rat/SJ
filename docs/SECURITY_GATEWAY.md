# Hướng dẫn đọc & phân tích code Gateway + Security

Tài liệu này giải thích những phần mình đã thêm vào API Gateway để bạn (intern) hiểu rõ **tại sao cần**, **luồng code**, và **luồng hoạt động**.

---

## 1) Mục tiêu

1. **Gateway routing**: Tất cả request đi qua Gateway và được forward đến các service con.
2. **Security ở Gateway**: Kiểm tra JWT (header `Authorization: Bearer <token>`).
3. **Ping APIs để test**: `/auth/ping` và `/wallet/ping`.

---

## 2) Những file quan trọng

### 2.1. Routing ở Gateway
- File: `src/main/resources/application.properties`
- Các dòng chính:
  - `spring.cloud.gateway.mvc.routes[0].predicates[0]=Path=/auth/**`
  - `spring.cloud.gateway.mvc.routes[0].uri=http://localhost:8081`
  - `spring.cloud.gateway.mvc.routes[1].predicates[0]=Path=/wallet/**`
  - `spring.cloud.gateway.mvc.routes[1].uri=http://localhost:8082`

**Ý nghĩa:**
- Nếu request bắt đầu bằng `/auth/**` → chuyển sang AuthService (port 8081).
- Nếu request bắt đầu bằng `/wallet/**` → chuyển sang WalletService (port 8082).

### 2.2. Security Filter (JWT)
- File: `src/main/java/com/example/apigateway/security/JwtAuthFilter.java`

**Ý nghĩa:**
- Đây là một filter chạy trước khi request được xử lý.
- Nếu request không nằm trong danh sách public (`/auth/ping`, `/wallet/ping`, `/auth/login`) thì phải có header:
  - `Authorization: Bearer <token>`
- Sai hoặc thiếu → trả `401 Unauthorized`.

### 2.3. SecurityConfig
- File: `src/main/java/com/example/apigateway/security/SecurityConfig.java`

**Ý nghĩa:**
- Tắt CSRF (vì API stateless).
- `SessionCreationPolicy.STATELESS`: không dùng session.
- Cho phép public path (ping + login).
- Các path còn lại cần xác thực bằng JWT (filter).

---

## 3) Luồng hoạt động (request flow)

### 3.1. Ví dụ ping public
1. Client gọi: `GET /auth/ping`
2. Gateway nhận request
3. `JwtAuthFilter` bỏ qua vì public path
4. Route `/auth/**` → forward sang AuthService
5. AuthService trả `auth-pong`

### 3.2. Ví dụ request cần bảo vệ
1. Client gọi: `GET /wallet/balance`
2. Gateway nhận request
3. `JwtAuthFilter` kiểm tra header `Authorization: Bearer <token>`
4. Nếu đúng → forward sang WalletService
5. Nếu sai → trả `401`

---

## 4) Vì sao làm như vậy?

- **Gateway là chốt bảo vệ đầu tiên**: nếu request sai, chặn ngay từ đầu → giảm tải service con.
- **JWT phù hợp thực tế**: thống nhất với AuthService, dễ mở rộng phân quyền.
- **Tách routing rõ ràng**: sau này mở rộng thêm Transaction/Notification chỉ cần thêm route.

---

## 5) Cách test nhanh

1. Chạy AuthService (8081) và WalletService (8082)
2. Chạy APIGateway (8080)
3. Login để lấy token:
  - `POST http://localhost:8081/auth/login` với body `{"username":"user","password":"password"}`
4. Test:
  - `GET http://localhost:8080/auth/ping`
  - `GET http://localhost:8080/wallet/ping`
5. Test protected:
  - `GET http://localhost:8080/wallet/any`
  - Thêm header `Authorization: Bearer <token>`

---

## 6) Gợi ý bước tiếp theo cho intern

1. Đưa danh sách public path vào config rõ ràng hơn (YAML hoặc config class).
2. Thêm log filter ở Gateway để trace request.
3. Viết unit test cho security filter.

---

Nếu muốn, mình có thể hướng dẫn tiếp cách đổi từ API key sang JWT hoặc viết test cho Gateway.
