<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ReportShow</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1"><em>${type_report}</em> transaction:</h1>
        </div>
    </div>
    <#list transactions as type, amount>
        <p>${type}: ${amount}</p>
    <#else>
        <p>No transaction</p>
    </#list>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>