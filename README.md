## Hệ thống Ví điện tử
Kiến trúc Microservice với design patterns là Chain of Responsibility (Handler)
Security: Sử dụng JWT để quản lý phân quyền và xác thực giao dịch.
Database: MySQL và Redis để lưu các request tạm thời, chống spam (Idempotency).

### Phân tách các nghiệp vụ (Business Services)
1. Auth Service (Bảo mật):

Sử dụng Keycloak hoặc Spring Security + JWT.
Nhiệm vụ: Đăng ký, đăng nhập, cấp phát Token, phân quyền.

2. Wallet Service (Quản lý ví):

Lưu trữ số dư (Balance) của từng người dùng.
Áp dụng Optimistic Locking để tránh việc 2 request cùng trừ tiền một lúc khiến số dư bị sai.

3. Transaction Service (Trái tim của hệ thống):

Xử lý logic nạp tiền, chuyển tiền, thanh toán.
Áp dụng Chain of Responsibility (Handler) để kiểm tra các điều kiện thanh toán và Strategy Pattern để tính phí cho từng loại giao dịch.

4. Notification Service (Thông báo):

Gửi Email/SMS/Push cho người dùng khi giao dịch xong.
Nên xử lý bất đồng bộ (Asynchronous) để không làm chậm quá trình thanh toán.

### Cách các Service "nói chuyện" với nhau

Giao tiếp đồng bộ (Synchronous - OpenFeign): Khi User bấm chuyển tiền, Transaction Service gọi trực tiếp sang Wallet Service để kiểm tra số dư ngay lập tức. Nếu ví không đủ tiền, báo lỗi luôn.

Giao tiếp bất đồng bộ (Asynchronous - Kafka/RabbitMQ): Khi giao dịch thành công, Transaction Service chỉ cần "ném" một tin nhắn vào hàng chờ (Queue) rồi báo thành công cho User. Notification Service sẽ tự nhặt tin nhắn đó để gửi mail sau. 

### Một số chú ý

Nếu Wallet Service trừ tiền thành công nhưng Transaction Service lại sập trước khi lưu lịch sử, tiền của khách sẽ bị mất dấu => áp dụng Saga Pattern
