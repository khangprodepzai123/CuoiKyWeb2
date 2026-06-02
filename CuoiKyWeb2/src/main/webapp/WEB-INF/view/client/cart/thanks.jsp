<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt hàng thành công - CuoiKyWeb2</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card border-0 shadow text-center p-5">
                <i class="bi bi-check-circle-fill text-success display-1 mb-3"></i>
                <h2 class="fw-bold">Cảm ơn bạn đã đặt hàng!</h2>
                <p class="text-muted mb-4">Đơn hàng đã được ghi nhận với trạng thái PENDING. Chúng tôi sẽ liên hệ sớm.</p>
                <div class="d-flex justify-content-center gap-2 flex-wrap">
                    <a href="/order-history" class="btn btn-primary">Xem lịch sử đơn hàng</a>
                    <a href="/" class="btn btn-outline-secondary">Tiếp tục mua sắm</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
