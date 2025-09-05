<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AccountShow</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1">Your accounts:</h1>
        </div>
    </div>
    <#list accounts as account>
        <p>${account}</p>
    <#else>
        <p>No accounts</p>
    </#list>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>