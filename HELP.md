# Dự án Spring Boot - Hướng dẫn sử dụng

## Nội dung

- [Giới thiệu](#giới-thiệu)
- [Cài đặt môi trường](#cài-đặt-môi-trường)
- [Cấu hình Spring Security](#cấu-hình-spring-security)
- [Cấu hình Thymeleaf](#cấu-hình-thymeleaf)
- [Cấu hình Cloudinary](#cấu-hình-cloudinary)
- [Cấu hình MSSQL](#cấu-hình-mssql)
- [Chạy ứng dụng](#chạy-ứng-dụng)
- [Lỗi thường gặp](#lỗi-thường-gặp)

## Giới thiệu

Dự án này là một ứng dụng Spring Boot tích hợp Spring Security, Thymeleaf, Cloudinary và MSSQL để quản lý và hiển thị dữ liệu từ cơ sở dữ liệu, đồng thời sử dụng Cloudinary để xử lý và lưu trữ hình ảnh.

## Cài đặt môi trường 

1. **Cài đặt JDK 21**: Cài đặt JDK 21 từ [Oracle JDK](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html).
2. **Cài đặt Maven**: Tải và cài đặt Maven từ [Maven Downloads](https://maven.apache.org/download.cgi).
3. **Cài đặt MS SQL Server**: Cài đặt MSSQL Server hoặc sử dụng dịch vụ MSSQL trên cloud.



## Sơ đồ tổ chức dữ liệu
project-root/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── fpoly/
│   │   │   │   ├── electroland/
│   │   │   │   │   ├── ElectrolandApplication.java     # Lớp khởi động Spring Boot
│   │   │   │   │   ├── config/                         # Các lớp cấu hình
│   │   │   │   │   │   └── SecurityConfig.java         # Cấu hình Spring Security
│   │   │   │   │   ├── controller/                     # Các controller cho giao diện người dùng
│   │   │   │   │   │   ├── AuthController.java         # Xử lý đăng nhập, đăng ký
│   │   │   │   │   │   └── HomeController.java         # Trang chủ hoặc các endpoint khác
│   │   │   │   │   ├── restController/                 # Các API REST (các controller cho RESTful services)
│   │   │   │   │   │   ├── UserRestController.java     # API xử lý người dùng
│   │   │   │   │   │   └── ProductRestController.java  # API xử lý sản phẩm
│   │   │   │   │   ├── model/                          # Các model/entity
│   │   │   │   │   │   ├── User.java                   # Model người dùng
│   │   │   │   │   │   └── Product.java                # Model sản phẩm (ví dụ)
│   │   │   │   │   ├── repository/                     # Các lớp repository cho database
│   │   │   │   │   │   └── UserRepository.java         # Repository cho người dùng
│   │   │   │   │   ├── service/                        # Các lớp service
│   │   │   │   │   │   ├── UserService.java            # Service xử lý logic người dùng
│   │   │   │   │   │   └── CloudinaryService.java      # Service xử lý upload ảnh lên Cloudinary
│   │   │   │   │   ├── util/                           # Các lớp tiện ích (nếu có)
│   │   │   │   │   │   └── PasswordUtil.java           # Lớp hỗ trợ mã hóa mật khẩu (nếu có)
│   │   ├── resources/
│   │   │   ├── application.properties                  # Cấu hình ứng dụng
│   │   │   ├── static/                                # Các file tĩnh (CSS, JavaScript, hình ảnh)
│   │   │   │   ├── css/                               # Các file CSS
│   │   │   │   │   ├── style.css                      # Các file CSS chính
│   │   │   │   │   └── custom.css                     # Các file CSS tùy chỉnh
│   │   │   │   ├── js/                                # Các file JavaScript
│   │   │   │   │   ├── script.js                      # Các file JavaScript chính
│   │   │   │   │   └── custom.js                      # Các file JavaScript tùy chỉnh
│   │   │   │   └── images/                            # Các file hình ảnh
│   │   │   │       ├── logo.png                       # Ví dụ hình ảnh logo
│   │   │   │       └── banner.jpg                     # Ví dụ hình ảnh banner
│   │   │   ├── templates/                             # Các template Thymeleaf (JSP hoặc HTML)
│   │   │   │   ├── auth/                              # Các template liên quan đến xác thực người dùng
│   │   │   │   │   ├── login.html                     # Trang đăng nhập
│   │   │   │   │   └── register.html                  # Trang đăng ký
│   │   │   │   ├── home/                              # Các template liên quan đến trang chủ
│   │   │   │   │   └── home.html                      # Trang chủ chính
│   │   │   │   ├── layout/                            # Các layout dùng chung cho nhiều trang
│   │   │   │   │   ├── header.html                    # Phần đầu trang (header)
│   │   │   │   │   └── footer.html                    # Phần chân trang (footer)
│   │   │   │   └── shared/                            # Các phần template dùng chung
│   │   │   │       └── messages.html                  # Ví dụ phần thông báo, các lỗi...
│   ├── test/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── AuthControllerTest.java        # Kiểm thử AuthController
│   │   │           └── UserServiceTest.java           # Kiểm thử UserService
│   ├── pom.xml                                           # File cấu hình Maven
│
├── .gitignore                                            # Các file cần bỏ qua trong Git
└── README.md                                             # Hướng dẫn sử dụng dự án


