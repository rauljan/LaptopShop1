<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>

<@htmlPad.page "User table">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#userAddModal">
                <i class="far fa-plus-square"></i> Добавить нового пользователя
            </button>
            <@addModal.userAdd/>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#userSearch" aria-expanded="false" aria-controls="userSearch">
                Найти пользователя
            </button>
            <div class="collapse form-group" id="userSearch">
                <form method="get" action="/user">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="username"
                                   value="${RequestParameters.username!}" placeholder="Логин "/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="email"class="form-control" name="email"
                                   value="${RequestParameters.email!}" placeholder="E-mail"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <select class="custom-select" name="isActive">
                                <option value="" selected disabled>Статус пользователя</option>
                                <option>Активный</option>
                                <option>Неактивный</option>
                            </select>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger float-right">
                        <i class="fa fa-search"></i>
                    </button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>

    <div class="mt-4">
        <div class="row">
            <div class="col">
                <@eO.errorOutput errorMessage!/>
            </div>
        </div>
        <#if users?has_content>
            <div class="h1">Список пользователей:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Номер пользователя</th>
                    <th scope="col">Логин </th>
                    <th scope="col">Роль</th>
                    <th scope="col">E-mail</th>
                    <th scope="col">Статус пользователя</th>
                    <th scope="col">Изменить</th>
                    <th scope="col">Удалить</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list users as user>
                    <tr>
                        <th class="text-danger" scope="row">${user.id}</th>
                        <td>${user.username}</td>
                        <td>${user.role}</td>
                        <td>${user.email}</td>
                        <td>${user.isActive?then("Активный", "Неактивный")}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#userEditModal${user.id?c}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.userEdit id="${user.id?c}" currentUsername="${user.username}"
                            currentPassword="${user.password}" currentRole="${user.role}"
                            activeStatus="${user.isActive?string('Активный', 'Неактивный')}"
                            currentEmail="${user.email}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${user.id?c}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${user.id?c}"
                            deletionLink="/user/delete/${user.id?c}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не найдено ни одного  пользователя.</div>
        </#if>
    </div>
</@htmlPad.page>