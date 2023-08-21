<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@htmlPad.page "HDD">
    <@security.authorize access="!hasAnyAuthority('MANAGER', 'CEO')">
        <div class="container">
    </@security.authorize>
    <div class="row">
        <div class="col">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                        data-target="#hddAddModal">
                    <i class="far fa-plus-square"></i> Добавить новый HDD диск
                </button>
                <@addModal.hddAdd/>
            </@security.authorize>
        </div>
        <div class="d-flex justify-content-center">
            <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                <button type="button" class="btn btn-success mr-2 fixedBtnHeight" data-toggle="modal"
                        data-target="#HddExcelUploadModal">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
                </button>
                <@eU.excelUpload tableNameEn="Hdd" tableNameUk="HDD дисков"/>
            </@security.authorize>
            <@security.authorize access="isAuthenticated()">
                <a class="btn btn-success fixedBtnHeight" href="/hdd/exportExcel" role="button">
                    <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i>
                </a>
            </@security.authorize>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#hddSearch" aria-expanded="false" aria-controls="hddSearch">
                Найти HDD диск
            </button>
            <div class="collapse form-group" id="hddSearch">
                <form method="get" action="/hdd">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="model"
                                   value="${RequestParameters.model!}" placeholder="Модель"/>
                        </div>
                        <div class="col-4">
                            <input type="number" min="1" class="form-control" name="memory"
                                   value="${RequestParameters.memory!}" placeholder="Память"/>
                        </div>
                        <div class="col-4">
                            <input type="number" min="1" class="form-control" name="price"
                                   value="${RequestParameters.price!}" placeholder="Цена"/>
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
        <#if hdds?has_content>
            <div class="h1">Список HDD дисков:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                        <th class="text-danger" scope="col">Номер на складе</th>
                    </@security.authorize>

                    <th scope="col">Модель</th>
                    <th scope="col">Память(GiB)</th>
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
                <#list hdds as hdd>
                    <tr>
                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <th class="text-danger" scope="row">${hdd.id}</th>
                        </@security.authorize>

                        <td>${hdd.model}</td>
                        <td>${hdd.memory}</td>
                        <td>${hdd.price}</td>
                        <@security.authorize access="!hasAnyAuthority('MANAGER', 'CEO')">
                            <td><button class="btn btn-outline-dark">Добавить в корзину</button></td>
                        </@security.authorize>
                        <@security.authorize access="hasAnyAuthority('MANAGER', 'CEO')">
                            <td>
                                <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                        data-target="#hddEditModal${hdd.id?c}">
                                    <i class="fa fa-edit"></i>
                                </button>
                                <@editModal.hddEdit id="${hdd.id?c}" hddModel="${hdd.model}" hddMemory="${hdd.memory?c}" hddPrice ="${hdd.price}"/>
                            </td>
                            <td>
                                <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                        data-target="#deleteModal${hdd.id?c}">
                                    <i class="fa fa-trash"></i>
                                </button>
                                <@deleteModal.delete id="${hdd.id?c}"
                                deletionLink="/hdd/delete/${hdd.id?c}"/>
                            </td>
                        </@security.authorize>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не найдено ни одного HDD диска.</div>
        </#if>
    </div>
</@htmlPad.page>