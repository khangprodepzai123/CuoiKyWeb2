<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thanh toán - CuoiKyWeb2</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="mb-0">Thanh toán</h3>
        <a href="/cart" class="btn btn-outline-secondary btn-sm">← Quay lại giỏ hàng</a>
    </div>

    <c:choose>
        <c:when test="${empty cartDetails}">
            <div class="alert alert-info">Giỏ hàng trống. <a href="/">Tiếp tục mua sắm</a></div>
        </c:when>
        <c:otherwise>
            <div class="table-responsive mb-4">
                <table class="table table-striped align-middle bg-white">
                    <thead>
                    <tr>
                        <th>Ảnh</th>
                        <th>Sản phẩm</th>
                        <th class="text-end">Đơn giá</th>
                        <th class="text-end">SL</th>
                        <th class="text-end">Thành tiền</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="cd" items="${cartDetails}">
                        <tr>
                            <td>
                                <c:if test="${not empty cd.product.image}">
                                    <img src="/images/product/${cd.product.image}" alt=""
                                         style="width:64px;height:64px;object-fit:contain;" class="rounded" />
                                </c:if>
                            </td>
                            <td>
                                <a href="/product/${cd.product.id}" class="fw-semibold text-decoration-none">
                                    ${cd.product.name}
                                </a>
                            </td>
                            <td class="text-end">
                                <fmt:formatNumber type="number" value="${cd.price}" /> đ
                            </td>
                            <td class="text-end">${cd.quantity}</td>
                            <td class="text-end">
                                <fmt:formatNumber type="number" value="${cd.price * cd.quantity}" /> đ
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="row g-4">
                <div class="col-md-6">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title mb-3">Thông tin người nhận</h5>
                            <form action="/place-order" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                <div class="mb-3">
                                    <label class="form-label">Họ tên</label>
                                    <input class="form-control" name="receiverName" required />
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Địa chỉ</label>
                                    <input class="form-control" name="receiverAddress" required />
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Số điện thoại</label>
                                    <input class="form-control" name="receiverPhone" required />
                                </div>
                                <button type="submit" class="btn btn-primary w-100">
                                    Xác nhận đặt hàng (COD)
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title mb-3">Tóm tắt</h5>
                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Phí vận chuyển</span>
                                <span>0 đ</span>
                            </div>
                            <div class="d-flex justify-content-between border-top pt-2">
                                <span class="fw-bold">Tổng thanh toán</span>
                                <span class="fw-bold text-primary">
                                    <fmt:formatNumber type="number" value="${totalPrice}" /> đ
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
