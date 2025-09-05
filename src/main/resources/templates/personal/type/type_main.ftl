<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Type</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1">Welcome to the <em>type</em> menu!</h1>
        </div>
        <div class="btn-group-vertical" role="group" aria-label="Vertical button group">
            <div class="col">
                <form action="type/show" method="get">
                    <button type="submit" class="btn btn-primary">Show</button>
                </form>
            </div>
            <br>
            <div class="col">
                <form action="type/add" method="get">
                    <button type="submit" class="btn btn-primary">Add</button>
                </form>
            </div>
            <br>
            <div class="col">
                <form action="type/update" method="get">
                    <button type="submit" class="btn btn-primary">Update</button>
                </form>
            </div>
            <br>
            <div class="col">
                <form action="type/delete" method="get">
                    <button type="submit" class="btn btn-primary">Delete</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>