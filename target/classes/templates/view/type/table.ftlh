<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<@htmlPad.page "Type">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal" data-target="#typeAddModal">
                <i class="far fa-plus-square"></i> Добавить новый тип ноутбука
            </button>
            <@addModal.typeAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn btn-success mr-2 fixedBtnHeight" data-toggle="modal"
                    data-target="#TypeExcelUploadModal">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
            </button>
            <@eU.excelUpload tableNameEn="Type" tableNameUk="типов"/>
            <a class="btn btn-success fixedBtnHeight" href="/type/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i>
            </a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#typeSearch" aria-expanded="false" aria-controls="typeSearch">
                Найти тип
            </button>
            <div class="collapse form-group" id="typeSearch">
                <form method="get" action="/type">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="typeName"
                                   value="${RequestParameters.typeName!}" placeholder="Название типа"/>
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
        <#if types?has_content>
            <div class="row">
                <div class="col">
                    <div class="h1">Список типов:</div>
                </div>
            </div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Номер</th>
                    <th scope="col">Название</th>
                    <th scope="col">Изменить</th>
                    <th scope="col">Удалить</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list types as type>
                    <tr>
                        <th class="text-danger" scope="row">${type.id}</th>
                        <td>${type.name}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#typeEditModal${type.id?c}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.typeEdit id="${type.id?c}" typeName="${type.name}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${type.id?c}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${type.id?c}" deletionLink="/type/delete/${type.id?c}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не найдено ни одного типа.</div>
        </#if>
    </div>
</@htmlPad.page>