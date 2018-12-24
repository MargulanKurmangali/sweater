<#macro login path isRegisterForm>
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <div class="col-sm5">
            <input type="text" name="username" class="form-control" placeholder="Username"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> Password:</label>
        <div class="col-sm5">
            <input type="password" name="password" class="form-control" placeholder="Password"/>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token?if_exists}">
    <#if !isRegisterForm>
        <a class="btn btn-primary" href="/registration"> Sign Up </a></#if>
    <button class="btn btn-primary" type="submit"><#if isRegisterForm> Sign Up <#else>Sign In</#if></button>
</form>
</#macro>
<#macro logout>
      <form action="/logout" method="post">
          <input type="hidden" name="_csrf" value="${_csrf.token?if_exists}">
          <button class="btn btn-primary" type="submit" >Sign Out</button>
      </form>
</#macro>