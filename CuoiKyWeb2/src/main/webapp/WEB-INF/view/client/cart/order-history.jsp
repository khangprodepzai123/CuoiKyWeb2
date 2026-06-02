<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử đơn hàng - CuoiKyWeb2</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <h3>Lịch sử đơn hàng</h3>
    <a href="/" class="btn btn-link">← Trang chủ</a>
    <c:choose>
        <c:when test="${empty orders}">
            <div class="alert alert-info mt-3">Bạn chưa có đơn hàng nào.</div>
        </c:when>
        <c:otherwise>
            <table class="table table-striped mt-3">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Trạng thái</th>
                    <th>Tổng tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.status}</td>
                        <td>${order.totalPrice}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
