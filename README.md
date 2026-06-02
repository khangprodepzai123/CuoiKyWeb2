# CuoiKyWeb2 - Website bán laptop (Spring Boot)

Đồ án / project web bán laptop làm theo hướng khóa Spring Boot (tham khảo project laptopshop).  
Gồm phần khách: xem SP, giỏ hàng, đặt hàng COD; phần admin: quản lý user, sản phẩm, đơn hàng.

**Sinh viên thực hiện:** Nguyễn Quốc Gia Khang  
**Công nghệ chính:** Spring Boot 4, Java 21, MySQL, JSP, Spring Security, Spring Session JDBC

---

## 1. Yêu cầu máy

Cài sẵn các thứ sau (bản nào cũng được miễn chạy được):

- **JDK 21**
- **MySQL** (mình dùng 8.x)
- **Maven** (hoặc dùng luôn file `mvnw` / `mvnw.cmd` trong project)
- **IDE:** Eclipse / IntelliJ / VS Code đều được (mình hay chạy Eclipse)

---

## 2. Cài database

1. Mở MySQL Workbench hoặc command line.
2. Tạo database:

```sql
CREATE DATABASE cuoikyweb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. **Không cần** tạo bảng tay. Hibernate sẽ tự tạo/cập nhật bảng khi chạy app (`spring.jpa.hibernate.ddl-auto=update`).

4. Sửa thông tin kết nối trong file:

`src/main/resources/application.properties`

```properties
spring.datasource.username=root
spring.datasource.password=123456
```

Đổi `username` / `password` cho đúng . Mật khẩu MySQL sai là lỗi hay gặp nhất.

---

## 3. Chạy project

### Cách 1: Eclipse

1. Import project Maven: **File → Import → Maven → Existing Maven Projects**
2. Chọn thư mục `CuoiKyWeb2`
3. Đợi Maven tải dependency (lần đầu hơi lâu)
4. Run class `CuoiKyWeb2Application.java` (Run As → Spring Boot App / Java Application)

### Cách 2: Dòng lệnh

```bash
cd CuoiKyWeb2
mvnw.cmd spring-boot:run
```

(Linux/Mac: `./mvnw spring-boot:run`)

App chạy tại: **http://localhost:8090**

> Port mặc định là **8090** (không phải 8080). Nếu báo port bị chiếm thì tắt process Java cũ hoặc đổi `server.port` trong `application.properties`.

---

## 4. Tài khoản demo

Lần đầu chạy app, `DataInitializer` tự tạo 2 account (nếu chưa có trong DB):

| Vai trò | Email | Mật khẩu |
|--------|--------|----------|
| Admin | `admin@cuoikyweb.com` | `Admin@12345` |
| Khách hàng | `user@cuoikyweb.com` | `User@12345` |

- Admin đăng nhập xong vào **http://localhost:8090/admin**
- User thường vào trang chủ mua hàng

Có thể đăng ký thêm tài khoản mới ở `/register` (mặc định role USER).

---

## 5. Hướng dẫn sử dụng nhanh

### Phía khách (USER)

1. Vào **http://localhost:8090** → xem danh sách laptop.
2. Bấm **Chi tiết** hoặc tên SP → xem mô tả, chọn số lượng → **Thêm vào giỏ** (phải đăng nhập).
3. **Giỏ hàng** (`/cart`) → **Thanh toán** (`/checkout`).
4. Nhập họ tên, địa chỉ, SĐT → **Xác nhận đặt hàng** (COD).
5. Xem lại đơn ở **Đơn hàng** (`/order-history`).

### Phía admin (ADMIN)

1. Đăng nhập admin → **Dashboard** `/admin`.
2. **Product:** thêm/sửa/xóa SP, upload ảnh (lưu trong `src/main/webapp/resources/images/product/`).
3. **Order:** xem đơn, đổi trạng thái (PENDING, SHIPPING, COMPLETE, CANCEL).
4. **User:** quản lý tài khoản, upload avatar (`resources/images/avatar/`).

Ảnh hiển thị qua URL dạng: `/images/product/ten-file.png`

---

## 6. Thêm sản phẩm nếu DB trống

Có 2 cách:

- **Admin:** `/admin/product/create` (nên dùng cách này).
- **SQL** (ví dụ):

```sql
USE cuoikyweb;

INSERT INTO products (name, price, detail_desc, short_desc, quantity, sold, factory, target, image)
VALUES (
  'MacBook Air M2',
  28000000,
  'Laptop Apple mỏng nhẹ, pin ổn.',
  'MacBook Air 13 inch.',
  10, 0, 'APPLE', 'MONG-NHE',
  '1711079954090-apple-01.png'
);
```

Tên file `image` phải có thật trong thư mục `resources/images/product/` (project đã copy sẵn vài ảnh mẫu).

---

## 7. Cấu trúc thư mục (tóm tắt)

```
CuoiKyWeb2/
├── src/main/java/.../controller/   # Controller client + admin
├── src/main/java/.../service/      # Business logic
├── src/main/java/.../domain/       # Entity JPA
├── src/main/resources/
│   └── application.properties      # Cấu hình DB, port, JSP
└── src/main/webapp/
    ├── WEB-INF/view/               # File JSP
    └── resources/images/           # Ảnh SP, avatar
```

---

## 8. Lỗi hay gặp

| Triệu chứng | Cách xử lý |
|-------------|------------|
| `Access denied for user` | Sửa user/pass MySQL trong `application.properties` |
| Port 8090 already in use | Tắt app cũ hoặc đổi `server.port` |
| Trang trắng / 404 JSP | Clean project, Maven Update, restart lại |
| Đặt hàng lỗi 500 | Đã fix lỗi `User.cart` — pull code mới, restart app |
| Ảnh không hiện | Kiểm tra tên file trong DB khớp file trong `images/product/` |

---

## 9. Ghi chú

- Project tham khảo bài laptopshop (Hỏi Dân IT), tự chỉnh package `NguyenQuocGiakhang.CuoiKyWeb2`.
- Session lưu JDBC (`spring.session.store-type=jdbc`), đăng nhập có remember-me.
- Thanh toán chỉ mô phỏng **COD**, chưa tích hợp VNPay/Momo.

Nếu chạy không được, kiểm tra lại MySQL đã bật chưa và log console lúc start app (dòng đỏ thường ghi rõ lỗi).

