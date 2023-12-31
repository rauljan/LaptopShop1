<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@htmlPad.page "Display">
    <@security.authorize access="!hasAnyAuthority('MANAGER', 'CEO')">
        <div class="container">
    </@security.authorize>
    <div class="row">
        <div class="col">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                        data-target="#displayAddModal">
                    <i class="far fa-plus-square"></i> Добавить новый дисплей
                </button>
                <@addModal.displayAdd/>
            </@security.authorize>
        </div>
        <div class="d-flex justify-content-center">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button"class="btn btn-success mr-2 fixedBtnHeight" data-toggle="modal"
                        data-target="#DisplayExcelUploadModal">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
                </button>
                <@eU.excelUpload tableNameEn="Display" tableNameUk="дисплеев"/>
            </@security.authorize>
            <@security.authorize access="isAuthenticated()">
                <a class="btn btn-success fixedBtnHeight" href="/display/exportExcel" role="button">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i>
                </a>
            </@security.authorize>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#displaySearch" aria-expanded="false" aria-controls="displaySearch">
                Найти дисплей
            </button>
            <div class="collapse form-group" id="displaySearch">
                <form method="get" action="/display">
                    <div class="form-row">
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="model"
                                   value="${RequestParameters.model!}" placeholder="Модель"/>
                        </div>
                        <div class="col-4">
                            <input type="text" class="form-control mr-1 mt-2" name="type"
                                   value="${RequestParameters.type!}" placeholder="Тип"/>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-5">
                            <input type="number" step="0.1" min="0.1" class="form-control mr-1 mt-2" name="diagonal"
                                   value="${RequestParameters.diagonal!}" placeholder="Диагональ"/>
                        </div>
                        <div class="col">
                            <input type="text" class="form-control mr-1 mt-2" name="resolution"
                                   value="${RequestParameters.resolution!}" placeholder="Разрешение"/>
                        </div>
                        <div class="col-4">
                            <input type="number" min="1" class="form-control" name="price"
                                   value="${RequestParameters.price!}" placeholder="Цена"/>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-danger ml-md-2 my-2 float-right">
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
        <#if displays?has_content>
            <div class="h1">Список дисплеев:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th class="text-danger" scope="col">Номер на складе</th>
                    </@security.authorize>

                    <th scope="col">Модель</th>
                    <th scope="col">Тип</th>
                    <th scope="col">Диагональ</th>
                    <th scope="col">Разрешение</th>
                    <th scope="col">Цена</th>
                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th scope="col">Изменить</th>
                        <th scope="col">Удалить</th>
                    </@security.authorize>
                    <@security.authorize access="hasAnyAuthority('USER')">
                        <th scope="col">Корзина</th>
                    </@security.authorize>

                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th></th>
                        <th></th>
                    </@security.authorize>
                </tr>
                </thead>
                <tbody>
                <#list displays as display>
                    <tr>
                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <th class="text-danger" scope="row">${display.id}</th>
                        </@security.authorize>

                        <td>${display.model}</td>
                        <td>${display.type}</td>
                        <td>${display.diagonal}</td>
                        <td>${display.resolution}</td>
                        <td>${display.price}</td>
                        <@security.authorize access="!hasAnyAuthority('MANAGER', 'CEO')">
                            <td><button class="btn btn-outline-dark">Добавить в корзину</button></td>
                        </@security.authorize>

                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <td>
                                <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                        data-target="#displayEditModal${display.id?c}">
                                    <i class="fa fa-edit"></i>
                                </button>
                                <@editModal.displayEdit id="${display.id?c}" displayModel="${display.model}"
                                displayType="${display.type}" displayDiagonal="${display.diagonal}"
                                displayResolution="${display.resolution}" displayPrice="${display.price}"/>
                            </td>
                            <td>
                                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                        data-target="#deleteModal${display.id?c}">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <@deleteModal.delete id="${display.id?c}"
                                deletionLink="/display/delete/${display.id?c}"/>
                            </td>
                        </@security.authorize>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не найдено ни одного дисплея.</div>
        </#if>
    </div>
    <@security.authorize access="!hasAnyAuthority('MANAGER', 'CEO')">
        </div>
    </@security.authorize>
</@htmlPad.page>