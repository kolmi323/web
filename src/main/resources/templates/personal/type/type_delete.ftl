<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TypeDelete</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1">Delete type</h1>
        </div>
    </div>
    <form action="delete" method="post">
        <div class="mb-3">
            <label for="inputId" class="form-label">Id dor delete</label>
            <@spring.formInput "form.id" "class=\"form-control\" id=\"inputId\" placeholder=\"Enter id for delete type\"" "id"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <div class="container">
            <p>${message}</p>
        </div>
        <button type="submit" class="btn btn-primary">Delete</button>
    </form>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>