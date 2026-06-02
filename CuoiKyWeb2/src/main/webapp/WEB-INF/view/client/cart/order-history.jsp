<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử đơn hàng - CuoiKyWeb2</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">Lịch sử đơn hàng</h3>
        <a href="/" class="btn btn-outline-secondary btn-sm">← Trang chủ</a>
    </div>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="alert alert-info">Bạn chưa có đơn hàng nào.</div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive">
                <table class="table table-striped align-middle bg-white">
                    <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Sản phẩm</th>
                        <th class="text-end">Đơn giá</th>
                        <th class="text-end">SL</th>
                        <th class="text-end">Thành tiền</th>
                        <th>Trạng thái</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr class="table-secondary">
                            <td colspan="3">
                                <strong>Đơn #${order.id}</strong>
                                <span class="text-muted small ms-2">
                                    ${order.receiverName} · ${order.receiverPhone}
                                </span>
                            </td>
                            <td colspan="2" class="text-end fw-semibold">
                                <fmt:formatNumber type="number" value="${order.totalPrice}" /> đ
                            </td>
                            <td><span class="badge bg-warning text-dark">${order.status}</span></td>
                        </tr>
                        <c:forEach var="od" items="${order.orderDetails}">
                            <tr>
                                <td>
                                    <c:if test="${not empty od.product.image}">
                                        <img src="/images/product/${od.product.image}" alt=""
                                             style="width:56px;height:56px;object-fit:contain;" class="rounded" />
                                    </c:if>
                                </td>
                                <td>
                                    <a href="/product/${od.product.id}" class="text-decoration-none">
                                        ${od.product.name}
                                    </a>
                                </td>
                                <td class="text-end">
                                    <fmt:formatNumber type="number" value="${od.price}" /> đ
                                </td>
                                <td class="text-end">${od.quantity}</td>
                                <td class="text-end">
                                    <fmt:formatNumber type="number" value="${od.price * od.quantity}" /> đ
                                </td>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
