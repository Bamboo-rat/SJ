# Gateway + Security (đã cập nhật)

Tài liệu này mô tả đúng trạng thái hiện tại của dự án sau khi đã refactor CORS và sửa route Gateway.

## 1) Mục tiêu

1. Tất cả request đi qua Gateway (`8080`).
2. Gateway định tuyến sang service đích:
   - AuthService (`8081`)
   - WalletService (`8082`)
3. Xác thực bằng JWT theo danh sách `public-paths`.
4. CORS cấu hình tập trung ở module `common`.

## 2) File quan trọng

### 2.1 Gateway routing
- File: `APIGateway/src/main/resources/application.properties`
- Prefix đúng cho Spring Cloud Gateway Server MVC 5.x:
  - `spring.cloud.gateway.server.webmvc.routes[0]...`
  - `spring.cloud.gateway.server.webmvc.routes[1]...`

### 2.2 Security
- Gateway security chain:
  - `APIGateway/src/main/java/com/example/apigateway/security/SecurityConfig.java`
- JWT filter dùng chung:
  - `common/src/main/java/com/example/common/security/JwtAuthFilter.java`
  - `common/src/main/java/com/example/common/security/JwtService.java`

### 2.3 CORS dùng chung
- `common/src/main/java/com/example/common/security/CorsConfig.java`

## 3) Luồng xử lý request

1. Request vào Gateway.
2. `JwtAuthFilter` kiểm tra request:
   - Nếu match `app.security.public-paths` → bỏ qua JWT.
   - Nếu không match → bắt buộc có `Authorization: Bearer <token>` hợp lệ.
3. Gateway forward theo route `Path=/auth/**` hoặc `Path=/wallet/**`.
4. Service đích xử lý và trả response.

## 4) Danh sách endpoint public mặc định

- Gateway: `/auth/ping`, `/wallet/ping`, `/auth/login`
- AuthService: `/auth/ping`, `/auth/login`
- WalletService: `/wallet/ping`

## 5) Cách test nhanh

1. Chạy 3 app: AuthService, WalletService, APIGateway.
2. Test public:
   - `GET http://localhost:8080/auth/ping`
   - `GET http://localhost:8080/wallet/ping`
3. Login lấy token:
   - `POST http://localhost:8080/auth/login`
4. Test protected endpoint (khi có endpoint private):
   - Gọi qua Gateway và thêm `Authorization: Bearer <token>`.

## 6) Lưu ý vận hành

- Nếu sửa `application.properties` cho route/security, cần restart service tương ứng.
- Nếu gọi qua Gateway bị `404`, kiểm tra lại prefix route có đúng `spring.cloud.gateway.server.webmvc.routes`.
