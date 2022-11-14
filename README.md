# DinoGhost Mobile
Hiện ứng dụng đã hoàn thành phần lớn, các lỗi và vấn đề gặp phải sẽ đươc cập nhật lên mục [Issues](https://github.com/sownt/dinoghost/issues).

## Tiến độ
- [x] Home Screen
- [x] Cart Screen
- [ ] Checkout Screen (completed UI)
- [x] GET data from REST API
- [ ] POST order (invalid body request, need help)
- [x] Room Database Implements

## Kiến trúc
Kiến trúc chính được sử dụng là MVVM. Ứng dụng được phân thành 2 tầng chính là UI và Data Layer.

## Thư viện sử dụng
- Retrofit để gọi đến api
- RxAndroid để phát và nhận dữ liệu giữa UI và Data Layer
- Room để lưu dữ liệu lên thiết bị
