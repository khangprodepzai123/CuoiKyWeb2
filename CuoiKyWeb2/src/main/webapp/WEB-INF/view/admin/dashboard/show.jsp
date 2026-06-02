<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - CuoiKyWeb2</title>
    <link href="/css/styles.css" rel="stylesheet" />
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <span class="navbar-brand">CuoiKyWeb2 Admin</span>
        <div>
            <a href="/" class="btn btn-sm btn-outline-light me-2">Trang chủ</a>
            <form action="/logout" method="post" class="d-inline">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button class="btn btn-sm btn-danger">Đăng xuất</button>
            </form>
        </div>
    </div>
</nav>
<div class="container">
    <h2 class="mb-4">Dashboard</h2>
    <div class="row g-3">
        <div class="col-md-4">
            <div class="card border-primary">
                <div class="card-body">
                    <h5 class="card-title">Người dùng</h5>
                    <p class="display-6">${countUsers}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card border-success">
                <div class="card-body">
                    <h5 class="card-title">Sản phẩm</h5>
                    <p class="display-6">${countProducts}</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card border-warning">
                <div class="card-body">
                    <h5 class="card-title">Đơn hàng</h5>
                    <p class="display-6">${countOrders}</p>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
