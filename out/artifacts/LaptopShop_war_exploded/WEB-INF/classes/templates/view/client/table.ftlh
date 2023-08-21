<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import "editModal.ftlh" as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../../excel/excelUploadModal.ftlh" as eU>
<#setting locale="uk">

<@htmlPad.page "Client">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#clientAddModal">
                <i class="far fa-plus-square"></i> Добавить нового клиента
            </button>
            <@addModal.clientAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn btn-success mr-2 fixedBtnHeight" data-toggle="modal"
                    data-target="#ClientExcelUploadModal">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
            </button>
            <@eU.excelUpload tableNameEn="Client" tableNameUk="клиентов"/>
            <a class="btn btn-success fixedBtnHeight" href="/client/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#clientSearch" aria-expanded="false" aria-controls="clientSearch">
                Найти клиента
            </button>
            <div class="collapse form-group" id="clientSearch">
                <form method="get" action="/client">
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="firstName"
                                   value="${RequestParameters.firstName!}" placeholder="Имя"/>
                        </div>
                        <div class="col">
                            <input type="text" class="form-control" name="secondName"
                                   value="${RequestParameters.secondName!}" placeholder="Фамилия"/>
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
        <#if clients?has_content>
            <div class="h1">Список клиентов:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Номер клиента</th>
                    <th scope="col">Имя</th>
                    <th scope="col">Фамилия</th>
                    <th scope="col">Изменить</th>
                    <th scope="col">Удалить</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list clients as client>
                    <tr>
                        <th class="text-danger" scope="row">${client.id}</th>
                        <td>${client.firstName}</td>
                        <td>${client.secondName}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#clientEditModal${client.id?c}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.clientEdit id="${client.id?c}"clientFirstName="${client.firstName}"
                            clientSecondName="${client.secondName}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${client.id?c}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${client.id?c}"
                            deletionLink="/client/delete/${client.id?c}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не найдено ни одного покупателя.</div>
        </#if>
    </div>
</@htmlPad.page>