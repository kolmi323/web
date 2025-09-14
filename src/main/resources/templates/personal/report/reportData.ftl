<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ReportData</title>
    <link href="/css/bootstrap.min.css", rel="stylesheet">
</head>
<body>
<div class="text-center">
    <div class="container">
        <div class="row">
            <h1 class="display-1">Info for <em>${type_report}</em> transaction</h1>
        </div>
    </div>
    <form action="${type_report}" method="post">
        <div class="mb-3">
            <label for="inputDateAfter" class="form-label">Date after</label>
            <@spring.formInput "form.dateAfter" "class=\"form-control\" id=\"inputDateAfter\" placeholder=\"Enter Date After\"" "dateAfter"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <br>
        <div class="mb-3">
            <label for="inputDateBefore" class="form-label">Date before</label>
            <@spring.formInput "form.dateBefore" "class=\"form-control\" id=\"inputDateBefore\" placeholder=\"Enter Date Before\"" "dateBefore"/>
            <@spring.showErrors "<br/>"/>
        </div>
        <button type="submit" class="btn btn-primary">Get report</button>
    </form>
</div>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>