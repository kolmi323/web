<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TypeShow</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1">Your types:</h1>
        </div>
    </div>
    <#list types as type>
        <p>${type}</p>
    <#else>
        <p>No type</p>
    </#list>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>