<#import "parts/common.ftl" as c>
<@c.page>

<div class="form-row" xmlns="http://www.w3.org/1999/html">
    <div class="form-group col-md-6">
        <form method="get" action="/main">
            <input type="text" name="filter" class="form-control" value="${filter?if_exists}">
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>
</div>

<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false">
 Add new Message
   </a>

<div class="collapse" id="collapseExample">
    <div class="form-group">
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Введите сообщение"/>
        <input type="text" name="tag" placeholder="Тэг">
        <div class="custom-file">
        <input type="file" name="file" id="customFile">
        <label class="custom-file-label" for="customFile">Choose file</label>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token?if_exists}">

        <button type="submit" class="btn btn-primary">Добавить</button>
    </form>
    </div>
</div>

<div>Список сообщений</div>
<form method="get" action="/main">
    <input type="text" name="filter" value="${filter?if_exists}">

    <button type="submit">Найти</button>
</form>
    <#list messages as message>
    <div>
        <b>${message.id?if_exists}</b>
        <span>${message.text?if_exists}</span>
        <i>${message.tag?if_exists}</i>
        <strong>${message.authorName?if_exists}</strong>
        <div>
             <#if message.filename??>
                 <img src="/img/${message.filename}">
             </#if>
        </div>
    </div>
    <#else>
    No messages
    </#list>
</@c.page>