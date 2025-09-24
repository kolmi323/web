<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>InfoError</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="alert alert-danger">
        ${error}
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>