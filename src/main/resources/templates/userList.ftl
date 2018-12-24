<#import "parts/common.ftl" as c>
<@c.page>
List of users
<table>
    <thead>
     <tr>
         <th>Name</th>
         <th>Role</th>
         <th></th>
     </tr>
    </thead>
    <tbody>
    <#list users as user>
    <tr>
        <td>${user.username}</td>
        <td><#list user.roles as role>${role?if_exists}<#sep>, </#list></td>
        <td><a href="/user/${user.id?if_exists}">edit</a></td>
    </tr>
    </#list>
    </tbody>
</table>
</@c.page>