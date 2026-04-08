# AuthService

## Vai trò

- Xác thực người dùng
- Cấp phát JWT

## Port

- `8081`

## Endpoint hiện có

- `GET /auth/ping` (public)
- `POST /auth/login` (public)

## Ghi chú

- User demo in-memory: `user/password`
- JWT được sinh bởi `AuthenticationService` và trả qua `LoginResponse`