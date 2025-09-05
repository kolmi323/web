<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TransactionCreate</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1">Create an transaction</h1>
        </div>
    </div>
    <form action="add" method="post">
        <div class="mb-3">
            <label for="inputTypeIds" class="form-label">Type ids</label>
            <@spring.formInput "form.typeIds" "class=\"form-control\" id=\"inputTypeIds\" placeholder=\"Enter ids\"" "typesIds"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <br>
        <div class="mb-3">
            <label for="inputSendingId" class="form-label">Type sending id</label>
            <@spring.formInput "form.sendingId" "class=\"form-control\" id=\"inputSendingId\" placeholder=\"Enter sending id\"" "sendingId"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <br>
        <div class="mb-3">
            <label for="inputReceivingId" class="form-label">Type receiving id</label>
            <@spring.formInput "form.receivingId" "class=\"form-control\" id=\"inputReceivingId\" placeholder=\"Enter receiving id\"" "receivingId"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <br>
        <div class="mb-3">
            <label for="inputAmount" class="form-label">Enter amount</label>
            <@spring.formInput "form.amount" "class=\"form-control\" id=\"inputAmount\" placeholder=\"Enter amount\"" "amount"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <div class="container">
            <p>${message}</p>
        </div>
        <button type="submit" class="btn btn-primary">Create</button>
    </form>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>