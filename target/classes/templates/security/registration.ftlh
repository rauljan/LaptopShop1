<#import "../parts/htmlPad.ftlh" as htmlPad>
<#import "../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>

<@htmlPad.page "Registration">
    <div class="container">
        <@eO.errorOutput errorMessage!/>

        <p class="h1 text-center">Регистрация:</p>
        <form method="post" action="/registration" class="form-group needs-validation w-100" novalidate
              modelAttribute="newUser">
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="loginInput">Новый логин :</label>
                    <input type="text" id="loginInput" class="form-control" name="username"
                           value="${RequestParameters.username!}" required/>
                    <div class="invalid-feedback">Новый логин  Введен некорректно</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="emailInput">Новая электронная почта:</label>
                    <input type="email" id="emailInput" class="form-control" name="email"
                           value="${RequestParameters.email!}" required/>
                    <div class="invalid-feedback">новая электронная почта Введена некорректно</div>
                </div>
            </div>
            <div class="form-row my-2">
                <div class="col">
                    <label class="d-inline ml-2" for="passwordInput">Новый пароль:</label>
                    <input type="password" id="passwordInput" class="form-control" name="password"
                           value="${RequestParameters.password!}" required/>
                    <div class="invalid-feedback">Новый пароль Введен некорректно</div>
                </div>
            </div>
            <div class="form-row my-3">
                <div class="col">
                    <a class="btn btn-dark" href="/login" role="button">Войти</a>
                </div>
                <div class="col">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn btn-danger float-right">Зарегистрироваться</button>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </form>
        <@eB.emptyBreaker inputIds=["loginInput", "passwordInput"]/>
    </div>
</@htmlPad.page>