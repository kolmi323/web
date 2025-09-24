<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>InfoMessage</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="alert alert-info">
        ${message}
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>