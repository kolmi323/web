<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="container text-center">
    <div class="row">
        <div class="col"></div>
        <div class="col">
            <form action="register" method="post">
                <div class="mb-3">
                    <label for="inputName" class="form-label">User name</label>
                    <@spring.formInput "form.name" "class=\"form-control\" id=\"inputName\" placeholder=\"Enter name\"" "name"/>
                    <@spring.showErrors "<br/>"/>
                </div>
                <div class="mb-3">
                    <label for="inputEmail" class="form-label">Email address</label>
                    <@spring.formInput "form.email" "class=\"form-control\" id=\"inputEmail\" placeholder=\"Enter email\"" "email"/>
                    <@spring.showErrors "<br/>"/>
                </div>
                <div class="mb-3">
                    <label for="inputPassword" class="form-label">Password</label>
                    <@spring.formInput "form.password" "class=\"form-control\" id=\"inputPassword\" placeholder=\"Password\"" "password"/>
                    <@spring.showErrors "<br/>"/>
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