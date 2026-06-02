<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng - CuoiKyWeb2</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">Giỏ hàng</h3>
        <a href="/" class="btn btn-outline-secondary">← Tiếp tục mua sắm</a>
    </div>

    <c:choose>
        <c:when test="${empty cartDetails}">
            <div class="alert alert-info">Giỏ hàng của bạn đang trống.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-striped align-middle">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Sản phẩm</th>
                        <th class="text-end">Đơn giá</th>
                        <th class="text-end">Số lượng</th>
                        <th class="text-end">Thành tiền</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="cd" items="${cartDetails}">
                        <tr>
                            <td>${cd.id}</td>
                            <td>
                                <div class="fw-semibold">${cd.product.name}</div>
                                <div class="text-muted small">${cd.product.factory} - ${cd.product.target}</div>
                            </td>
                            <td class="text-end">
                                <fmt:formatNumber type="number" value="${cd.price}" /> đ
                            </td>
                            <td class="text-end">${cd.quantity}</td>
                            <td class="text-end">
                                <fmt:formatNumber type="number" value="${cd.price * cd.quantity}" /> đ
                            </td>
                            <td class="text-end">
                                <form method="post" action="/delete-cart-product/${cd.id}">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <button class="btn btn-sm btn-outline-danger">Xóa</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="d-flex justify-content-end">
                <div class="card" style="min-width: 320px;">
                    <div class="card-body">
                        <div class="d-flex justify-content-between">
                            <span class="text-muted">Tổng tiền</span>
                            <span class="fw-bold">
                                <fmt:formatNumber type="number" value="${totalPrice}" /> đ
                            </span>
                        </div>
                        <div class="mt-3 small text-muted">
                            (Checkout sẽ làm ở module Order/Checkout tiếp theo.)
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>

