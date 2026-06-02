<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CuoiKyWeb2 - Cửa hàng Laptop</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        body { background: #f4f7fb; }
        .hero {
            background: linear-gradient(135deg, #0d6efd 0%, #6610f2 100%);
            color: #fff;
            border-radius: 1rem;
            padding: 3rem 2rem;
            margin-bottom: 2rem;
        }
        .product-card { border: none; box-shadow: 0 8px 24px rgba(0,0,0,.08); transition: transform .2s; }
        .product-card:hover { transform: translateY(-4px); }
        .product-img {
            height: 180px;
            background: linear-gradient(145deg, #e9ecef, #dee2e6);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 3rem;
            color: #6c757d;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-white shadow-sm mb-4">
    <div class="container">
        <a class="navbar-brand fw-bold text-primary" href="/">CuoiKyWeb2</a>
        <div class="d-flex align-items-center gap-2">
            <sec:authorize access="!isAuthenticated()">
                <a class="btn btn-outline-primary btn-sm" href="/login">Đăng nhập</a>
                <a class="btn btn-primary btn-sm" href="/register">Đăng ký</a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <span class="text-muted small me-2">Xin chào, ${sessionScope.fullName}</span>
                <a class="btn btn-outline-secondary btn-sm" href="/order-history">Đơn hàng</a>
                <sec:authorize access="hasRole('ADMIN')">
                    <a class="btn btn-warning btn-sm" href="/admin">Admin</a>
                </sec:authorize>
                <form action="/logout" method="post" class="d-inline">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <button class="btn btn-danger btn-sm">Đăng xuất</button>
                </form>
            </sec:authorize>
        </div>
    </div>
</nav>

<div class="container pb-5">
    <div class="hero">
        <h1 class="fw-bold">Chào mừng đến CuoiKyWeb2</h1>
        <p class="mb-0 opacity-75">Module đăng nhập, đăng ký và phân quyền đã sẵn sàng. Đăng nhập để trải nghiệm đầy đủ.</p>
    </div>

    <h3 class="mb-3">Sản phẩm nổi bật</h3>
    <c:choose>
        <c:when test="${empty products}">
            <div class="alert alert-info">Chưa có sản phẩm trong database. Bạn có thể thêm sau ở module Product.</div>
        </c:when>
        <c:otherwise>
            <div class="row g-4">
                <c:forEach var="product" items="${products}">
                    <div class="col-md-6 col-lg-4">
                        <div class="card product-card h-100">
                            <div class="product-img">
                                <i class="bi bi-laptop"></i>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title">${product.name}</h5>
                                <p class="text-muted small mb-2">${product.shortDesc}</p>
                                <p class="fw-bold text-primary mb-0">
                                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
