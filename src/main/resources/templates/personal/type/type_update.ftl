<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TypeUpdate</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1">Update type</h1>
        </div>
    </div>
    <form action="update" method="post">
        <div class="mb-3">
            <label for="inputId" class="form-label">Id type</label>
            <@spring.formInput "form.id" "class=\"form-control\" id=\"inputId\" placeholder=\"Enter id type\"" "id"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <div class="mb-3">
            <label for="inputName" class="form-label">Type new name</label>
            <@spring.formInput "form.newName" "class=\"form-control\" id=\"inputNewName\" placeholder=\"Enter new name\"" "newName"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <div class="container">
            <p>${message}</p>
        </div>
        <button type="submit" class="btn btn-primary">Update</button>
    </form>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>