<#import "parts/common.ftl" as c>
<@c.page>
User Editor
<form action="/user" method="post">
    <input type="text" name="username" value="${user.username?if_exists}">
    <#list roles as role>
        <div>
            <label> <input type="checkbox" name="${role?if_exists}" ${user.roles?seq_contains(role)?string("checked","")}>${role}</label>
        </div>
    </#list>
    <input type="hidden" value="${user.id?if_exists}" name="userId">
    <input type="hidden" value="${_csrf.token?if_exists}" name="_csrf">
    <button type="submit">Save</button>
</form>
</@c.page>