<#import "parts/common.ftl" as c>
<@c.page>

<div class="form-row" xmlns="http://www.w3.org/1999/html">
    <div class="form-group col-md-6">
        <form method="get" action="/main">
            <input type="text" name="filter" class="form-control" value="${filter?if_exists}">
            <button type="submit" class="btn btn-primary my-2">Search</button>
        </form>
    </div>
</div>

<a class="btn btn-primary my-2" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false">
    Add new Message
</a>

<div class="collapse" id="collapseExample">
    <div class="form-group">
        <form method="post" enctype="multipart/form-data">
            <input type="text" class="form-control col-md-6 my-1" name="text" placeholder="Введите сообщение"/>
            <input type="text" class="form-control col-md-6 my-1" name="tag" placeholder="Тэг">
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label  col-md-7" for="customFile">Choose file</label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token?if_exists}">

            <button type="submit" class="btn btn-primary">Добавить</button>
        </form>
    </div>
</div>

<div class="card-columns">
    <#list messages as message>
        <div class="card my-3">
        <#if message.filename??>
        <img src="/img/${message.filename}" class="card-img-top">
        </#if>
            <div class="m-2">
                <span>${message.text}</span>
                <i>${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                ${message.authorName}
            </div>
        </div>
    <#else>
    No message
    </#list>
</div>
</@c.page>