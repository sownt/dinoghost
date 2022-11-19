# DinoGhost Mobile
![Release](https://img.shields.io/github/v/release/sownt/dinoghost?include_prereleases&style=for-the-badge)
![Build](https://img.shields.io/github/workflow/status/sownt/dinoghost/Android%20CI?style=for-the-badge)
![GitHub issues](https://img.shields.io/github/issues/sownt/dinoghost?style=for-the-badge)

## Tiến độ
- [x] Home Screen
- [x] Cart Screen
- [x] Checkout Screen (completed UI)
- [x] GET data from REST API
- [x] POST order (invalid body request, need help)
- [x] Room Database Implements

## Kiến trúc
Kiến trúc chính được sử dụng là MVVM. Ứng dụng được phân thành 2 tầng chính là UI và Data Layer.

## Thư viện sử dụng
- Retrofit để gọi đến api
- RxAndroid để phát và nhận dữ liệu giữa UI và Data Layer
- Room để lưu dữ liệu lên thiết bị
