<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Đăng nhập - CuoiKyWeb2</title>
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="bg-primary">
<div id="layoutAuthentication">
    <div id="layoutAuthentication_content">
        <main>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5">
                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                            <div class="card-header">
                                <h3 class="text-center font-weight-light my-4">Đăng nhập</h3>
                            </div>
                            <div class="card-body">
                                <form method="post" action="/login">
                                    <c:if test="${param.error != null}">
                                        <div class="alert alert-danger">Email hoặc mật khẩu không đúng.</div>
                                    </c:if>
                                    <c:if test="${param.logout != null}">
                                        <div class="alert alert-success">Đăng xuất thành công.</div>
                                    </c:if>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" type="email" placeholder="email@example.com" name="username" required />
                                        <label>Email</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" type="password" placeholder="Password" name="password" required />
                                        <label>Mật khẩu</label>
                                    </div>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <div class="d-grid mt-4">
                                        <button class="btn btn-primary">Đăng nhập</button>
                                    </div>
                                </form>
                                <div class="small text-muted mt-3">
                                    Demo: <code>admin@cuoikyweb.com</code> / <code>Admin@12345</code>
                                </div>
                            </div>
                            <div class="card-footer text-center py-3">
                                <div class="small"><a href="/register">Chưa có tài khoản? Đăng ký</a></div>
                                <div class="small mt-2"><a href="/">← Về trang chủ</a></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="/js/scripts.js"></script>
</body>
</html>
