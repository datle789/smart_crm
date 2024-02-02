# Sử dụng ảnh OpenJDK làm base image
FROM openjdk:11-jre-slim

# Sao chép JAR vào thư mục /app
COPY target/smart_crm.jar /app/

# Thiết lập thư mục làm việc mặc định
WORKDIR /app

# Chạy ứng dụng khi container được khởi động
CMD ["java", "-jar", "your-app.jar"]