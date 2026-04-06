# Hướng dẫn cấu trúc Handler (Common)

Tài liệu này giải thích **cấu trúc handler**, **cách hoạt động**, và **luồng xử lý** trong module common.

---

## 1) Mục tiêu của Handler

- Chuẩn hóa cách xử lý request theo pattern **Chain/Handler**.
- Tách **request** và **logic xử lý** ra khỏi controller/service.
- Dễ mở rộng: thêm handler mới mà không sửa code cũ.

---

## 2) Các thành phần chính

### 2.1. BaseHandler
- File: `common/src/main/java/com/example/common/handler/BaseHandler.java`
- Vai trò: định nghĩa **contract** cho mọi handler.

**Ý nghĩa:**
- `execute(request)` là nơi xử lý business.
- `getRequestType()` trả về class của request để map đúng handler.

### 2.2. HandlerContainer
- File: `common/src/main/java/com/example/common/handler/HandlerContainer.java`
- Vai trò: interface tổng để gọi handler.

### 2.3. HandlerContainerImpl
- File: `common/src/main/java/com/example/common/handler/HandlerContainerImpl.java`
- Vai trò: quản lý mapping `RequestType -> Handler`.

**Ý nghĩa:**
- Spring inject tất cả handler vào constructor.
- Container tự build map theo `getRequestType()`.
- Khi gọi `handle(request)`, container tìm đúng handler và gọi `execute()`.

### 2.4. PageSupport
- File: `common/src/main/java/com/example/common/handler/PageSupport.java`
- Vai trò: wrapper kết quả phân trang.

---

## 3) Luồng xử lý (Flow)

1. Client gửi request vào controller/service.
2. Controller/service tạo request object (VD: `LoginRequest`).
3. Controller gọi `handlerContainer.handle(request)`.
4. Container tìm handler tương ứng qua `getRequestType()`.
5. Handler xử lý business và trả kết quả.
6. Controller nhận kết quả và trả response.

---

## 4) Ví dụ minh họa (logic)

**Giả sử có handler:**
- `LoginHandler implements BaseHandler<LoginRequest, LoginResponse>`
- `getRequestType()` trả `LoginRequest.class`

**Luồng chạy:**
- Controller tạo `LoginRequest`
- Container map tới `LoginHandler`
- Gọi `execute()` → trả `LoginResponse`

---

## 5) Lỗi thường gặp

- Không override đúng `getRequestType()` → container không tìm được handler.
- 2 handler trùng request type → map bị ghi đè.
- Không đăng ký handler vào Spring (thiếu `@Component`) → không được inject.

---

## 6) Checklist khi thêm handler mới

1. Tạo request class.
2. Tạo response class.
3. Implement `BaseHandler<T, R>`.
4. Thêm `@Component` cho handler.
5. Implement `getRequestType()` đúng.

---

Nếu bạn muốn, mình sẽ viết sẵn 1 handler demo để bạn chạy thử end-to-end.
