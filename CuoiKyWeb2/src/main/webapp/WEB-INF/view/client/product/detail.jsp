<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} - CuoiKyWeb2</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <style>
        body { background: #f4f7fb; }
        .product-detail-img {
            max-height: 360px;
            object-fit: contain;
            background: #fff;
            border-radius: 0.5rem;
            padding: 1rem;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-white shadow-sm mb-4">
    <div class="container">
        <a class="navbar-brand fw-bold text-primary" href="/">CuoiKyWeb2</a>
        <div class="d-flex gap-2">
            <a href="/" class="btn btn-outline-secondary btn-sm">Trang chủ</a>
            <sec:authorize access="isAuthenticated()">
                <a href="/cart" class="btn btn-outline-primary btn-sm">Giỏ hàng</a>
            </sec:authorize>
        </div>
    </div>
</nav>

<div class="container pb-5">
    <nav aria-label="breadcrumb" class="mb-3">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="/">Trang chủ</a></li>
            <li class="breadcrumb-item active">Chi tiết sản phẩm</li>
        </ol>
    </nav>

    <div class="row g-4">
        <div class="col-lg-6">
            <div class="card border-0 shadow-sm">
                <div class="card-body text-center">
                    <c:choose>
                        <c:when test="${not empty product.image}">
                            <img src="/images/product/${product.image}" class="img-fluid product-detail-img w-100"
                                 alt="${product.name}" />
                        </c:when>
                        <c:otherwise>
                            <i class="bi bi-laptop display-1 text-secondary"></i>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="col-lg-6">
            <h2 class="fw-bold">${product.name}</h2>
            <p class="text-muted mb-2">${product.factory} · ${product.target}</p>
            <p class="fs-4 fw-bold text-primary mb-3">
                <fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0" /> đ
            </p>
            <p class="mb-3">${product.shortDesc}</p>
            <p class="small text-muted">Còn lại: ${product.quantity} · Đã bán: ${product.sold}</p>

            <div class="d-flex align-items-center gap-2 mb-3">
                <label class="form-label mb-0">Số lượng:</label>
                <div class="input-group" style="width: 130px;">
                    <button type="button" class="btn btn-outline-secondary btn-qty-minus">−</button>
                    <input type="number" id="quantityInput" class="form-control text-center" value="1" min="1"
                           max="${product.quantity}" />
                    <button type="button" class="btn btn-outline-secondary btn-qty-plus">+</button>
                </div>
            </div>

            <sec:authorize access="isAuthenticated()">
                <form action="/add-product-from-view-detail" method="post" class="d-inline">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <input type="hidden" name="id" value="${product.id}" />
                    <input type="hidden" name="quantity" id="quantityHidden" value="1" />
                    <button type="submit" class="btn btn-primary">
                        <i class="bi bi-cart-plus"></i> Thêm vào giỏ
                    </button>
                </form>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <a href="/login" class="btn btn-primary">Đăng nhập để mua</a>
            </sec:authorize>
        </div>
        <div class="col-12">
            <div class="card border-0 shadow-sm">
                <div class="card-header bg-white fw-semibold">Mô tả chi tiết</div>
                <div class="card-body">
                    <p class="mb-0" style="white-space: pre-wrap;">${product.detailDesc}</p>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    (function () {
        const input = document.getElementById('quantityInput');
        const hidden = document.getElementById('quantityHidden');
        const max = parseInt(input.getAttribute('max'), 10) || 999;

        function sync() {
            let v = parseInt(input.value, 10);
            if (isNaN(v) || v < 1) v = 1;
            if (v > max) v = max;
            input.value = v;
            hidden.value = v;
        }

        document.querySelector('.btn-qty-minus').addEventListener('click', function () {
            input.value = Math.max(1, parseInt(input.value, 10) - 1);
            sync();
        });
        document.querySelector('.btn-qty-plus').addEventListener('click', function () {
            input.value = Math.min(max, parseInt(input.value, 10) + 1);
            sync();
        });
        input.addEventListener('change', sync);
        sync();
    })();
</script>
</body>
</html>
