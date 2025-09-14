<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1">Welcome to the <em>account</em> menu!</h1>
        </div>
    </div>
    <div class="btn-group-vertical" role="group" aria-label="Vertical button group">
        <div class="col">
            <form action="account/show" method="get">
                <button type="submit" class="btn btn-primary">Show</button>
            </form>
        </div>
        <br>
        <div class="col">
            <form action="account/add" method="get">
                <button type="submit" class="btn btn-primary">Add</button>
            </form>
        </div>
        <br>
        <div class="col">
            <form action="account/delete" method="get">
                <button type="submit" class="btn btn-primary">Delete</button>
            </form>
        </div>
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>