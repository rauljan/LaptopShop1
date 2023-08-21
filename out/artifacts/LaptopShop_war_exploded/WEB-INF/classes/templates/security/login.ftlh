<#import "../parts/htmlPad.ftlh" as htmlPad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>

<@htmlPad.page "Login">
    <div class="container">
        <#if (RequestParameters.error)??>
            <@eO.errorOutput"Неправильный логин или пароль!"/>
        </#if>
        <#if (activationMessage)??>
            <div class="d-flex flex-grow-1 justify-content-center alert alert-success alert-dismissible fade show">
                ${activationMessage}
                <button type="button" class="close" data-dismiss="alert">&times;</button>
            </div>
        </#if>
        <p class="h1 text-center">Аутентификация:</p>
        <form method="post" action="/login" class="form-group needs-validation w-100" novalidate>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="loginInput">Логин :</label>
                    <input type="text" id="loginInput" class="form-control" name="username"
                           value="${RequestParameters.login!}" required/>
                    <div class="invalid-feedback">Логин введен некорректно</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="passwordInput">Пароль:</label>
                    <input type="password" id="passwordInput" class="form-control" name="password"
                           value="${RequestParameters.password!}" required/>
                    <div class="invalid-feedback">Пароль введен некорректно</div>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-row my-3">
                <div class="col">
                    <a class="btn btn-dark" href="/registration" role="button">Зарегистрироваться</a>
                </div>
                <div class="col">
                    <button type="submit" class="btn btn-danger float-right">Войти</button>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
        <@eB.emptyBreaker inputIds=["loginInput", "passwordInput"]/>
    </div>
</@htmlPad.page>