# 📱 Aplikasi Android - News App Kotlin Jetpack Compose

Aplikasi ini dibangun menggunakan **Kotlin** dan **Jetpack Compose** sebagai toolkit UI modern dari Android. Project ini juga memanfaatkan arsitektur modular, dependency injection dengan Hilt, serta menyimpan data secara lokal menggunakan DataStore.

---

## 🚀 Alasan Menggunakan Jetpack Compose

Jetpack Compose dipilih karena:

- Penulisan UI lebih ringkas dan intuitif sehingga bisa cepat dengan deadline terbatas
- Terintegrasi langsung dengan `ViewModel` dan `State`
- Cocok untuk arsitektur modern seperti MVVM
- Memudahkan dalam membuat UI dinamis dan animasi
- Meski begitu, saya juga **familiar dengan XML** dan siap jika proyek membutuhkan pendekatan konvensional

---

## 🛠️ Teknologi & Library

| Fitur | Library | Deskripsi |
|-------|---------|-----------|
| **Dependency Injection** | `hilt-android`, `hilt-compiler`, `hilt-navigation-compose` | Manajemen dependency |
| **Navigasi** | `navigation-compose` | Navigasi antar composable screen |
| **Networking** | `retrofit`, `retrofit-gson`, `okhttp-logging` | Ambil data dari API Spaceflight News |
| **Image Loader** | `coil-compose` | Menampilkan gambar dari internet |
| **Icon** | `material-icons-extended` | Ikon tambahan Material Design |
| **Penyimpanan Lokal** | `data-store` | Menyimpan data akun, recent search, dll |
| **Background Task** | `androidx-work` | Auto logout walaupun app dimatikan |

---

## ✨ Fitur Aplikasi

### 🔐 Login & Register
- User dapat mendaftar dengan **nama, email, dan password**
- Data disimpan menggunakan DataStore
- Validasi login berdasarkan data yang tersimpan

### ⏰ Auto Logout
- Logout otomatis setelah **10 menit** (test mode: 10 detik)
- Timer tetap berjalan meski aplikasi **di-kill**
- Ketika logout, muncul **notifikasi push**

### 📰 Fetch Artikel
- Menggunakan **Retrofit** untuk mengambil artikel dari [SpaceflightNews API](https://api.spaceflightnewsapi.net)
- Menampilkan daftar artikel: judul, gambar, ringkasan, waktu terbit, dan kategori

### 🔎 Pencarian & Filter
- Mendukung **search** artikel
- Menyimpan **recent search** di DataStore
- Mendukung **filter berdasarkan news site**
- UI recent search ditampilkan dengan gaya **staggered layout**

### 📄 Detail Artikel
- Klik item membuka screen detail
- Menampilkan data lengkap artikel
- Menggunakan **SavedStateHandle** untuk navigasi antar objek

---

## 🧩 Arsitektur

- **MVVM (Model - ViewModel - View)**
- State di-handle dengan `mutableStateOf` dan `collectAsState`
- Lifecycle-aware (`viewModelScope`, `LaunchedEffect`, `remember`)
- Navigasi berbasis route dengan parameter `NavController`

---

## 📷 Screenshot

<p align="center">
  <img src="https://github.com/user-attachments/assets/2c1b2aff-2472-4755-9777-d26a61471c2b" alt="Home" width="250"/>
  <img src="https://github.com/user-attachments/assets/252ce873-74c7-4552-9ef5-df9d5a09cb9c" alt="Search" width="250"/>
  <img src="https://github.com/user-attachments/assets/a0d0892f-dba6-4f5f-a65e-325011b1f290" alt="Search 2" width="250"/>
</p>

---

## 📷 Demo

https://github.com/user-attachments/assets/db21fb7f-4561-47d6-b17a-2dc4e1f08f15

https://github.com/user-attachments/assets/b78a74c6-9b62-4909-a323-7bdc51e2de26

---

## 💡 Penutup

Aplikasi ini menunjukkan kemampuan dalam:
- Membangun UI modern dengan Jetpack Compose
- Menggunakan DataStore sebagai penyimpanan lokal
- Menangani logout otomatis dan notifikasi background
- Menerapkan prinsip clean architecture dengan modularisasi
