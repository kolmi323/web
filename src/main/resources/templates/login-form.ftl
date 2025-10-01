<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <form action="/login" method="post">
                <div class="mb-3">
                    <label for="exampleInputEmail" class="form-label">Email address</label>
                    <input name="email" class="form-control" id="inputEmail" placeholder="Enter email" type="text"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword" class="form-label">Password</label>
                    <input name="password" class="form-control" id="inputPassword" placeholder="Password" type="password"/>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>