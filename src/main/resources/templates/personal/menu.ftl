<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Personal</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-3">Welcome to the personal menu, ${name}!</h1>
        </div>
    </div>
    <div class="btn-group-vertical" role="group" aria-label="Vertical button group">
        <div class="col">
            <form action="account" method="get">
                <button type="submit" class="btn btn-primary">Account</button>
            </form>
        </div>
        <br>
        <div class="col">
            <form action="type" method="get">
                <button type="submit" class="btn btn-primary">Type</button>
            </form>
        </div>
        <br>
        <div class="col">
            <form action="transaction" method="get">
                <button type="submit" class="btn btn-primary">Transaction</button>
            </form>
        </div>
        <br>
        <div class="col">
            <form action="report" method="get">
                <button type="submit" class="btn btn-primary">Reports</button>
            </form>
        </div>
        <br>
        <div class="col">
            <form action="/logout" method="get">
                <button type="submit" class="btn btn-primary">Logout</button>
            </form>
        </div>
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>