<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AccountCreate</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1">Create an account</h1>
        </div>
    </div>
    <form action="add" method="post">
        <div class="mb-3">
            <label for="inputName" class="form-label">Account name</label>
            <@spring.formInput "form.name" "class=\"form-control\" id=\"inputName\" placeholder=\"Enter name\"" "name"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <div class="mb-3">
            <label for="inputBalance" class="form-label">Enter balance</label>
            <@spring.formInput "form.balance" "class=\"form-control\" id=\"inputBalance\" placeholder=\"Balance\"" "balance"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <button type="submit" class="btn btn-primary">Create</button>
    </form>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>