<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Start</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <h1>Welcome!</h1>
        </div>
        <div class="col"></div>
    </div>
    <div class="row">
        <div class="col">
            <form action="register" method="get">
                <button type="submit" class="btn btn-primary">Registration</button>
            </form>
        </div>
        <div class="col"></div>
        <div class="col">
            <form action="login-form" method="get">
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
        </div>
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>