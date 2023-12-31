<#import "../../parts/htmlPad.ftlh" as htmlPad>
<#import "addModal.ftlh" as addModal>
<#import 'editModal.ftlh' as editModal>
<#import "../../parts/deleteModal.ftlh" as deleteModal>
<#import "../../errorsHandlers/errorOutput.ftlh" as eO>
<#import "../../excel/excelUploadModal.ftlh" as eU>

<@htmlPad.page "Employee">
    <div class="row">
        <div class="col">
            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                    data-target="#employeeAddModal">
                <i class="far fa-plus-square"></i> Добавить нового сотрудника
            </button>
            <@addModal.employeeAdd/>
        </div>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn btn-success mr-2 fixedBtnHeight" data-toggle="modal"
                    data-target="#EmployeeExcelUploadModal">
                <i class="fas fa-file-excel"></i> <i class="fas fa-upload"></i>
            </button>
            <@eU.excelUpload tableNameEn="Employee" tableNameUk="сотрудников"/>
            <a class="btn btn-success fixedBtnHeight" href="/employee/exportExcel" role="button">
                <i class="fas fa-file-excel"></i> <i class="fas fa-download"></i></a>
        </div>
        <div class="col-lg-4 col-sm-5 col-12">
            <button class="btn btn-dark mt-sm-0 mt-2 w-100" type="button" data-toggle="collapse"
                    data-target="#employeeSearch" aria-expanded="false" aria-controls="employeeSearch">
                Найти сотрудника
            </button>
            <div class="collapse form-group" id="employeeSearch">
                <form method="get" action="/employee">
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
                    <div class="form-row my-2">
                        <div class="col">
                            <input type="text" class="form-control" name="shopAddress"
                                   value="${RequestParameters.shopAddress!}" placeholder="Адрес магазина"/>
                        </div>
                    </div>
                    <div class="form-row my-2">
                        <div class="col">
                            <select class="custom-select" name="isWorking">
                                <option value="" selected disabled>Статус сотрудника</option>
                                <option>Работает</option>
                                <option>Уволен</option>
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
        <#if employees?has_content>
            <div class="h1">Список сотрудников:</div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="text-danger" scope="col">Номер сотрудника</th>
                    <th scope="col">Имя</th>
                    <th scope="col">Фамилия</th>
                    <th scope="col">№ магазина</th>
                    <th scope="col">Адрес магазина</th>
                    <th scope="col">Статус</th>
                    <th scope="col">Изменить</th>
                    <th scope="col">Удалить</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list employees as employee>
                    <tr>
                        <th class="text-danger" scope="row">${employee.id}</th>
                        <td>${employee.firstName}</td>
                        <td>${employee.secondName}</td>
                        <#if employee.shop??>
                            <td>${employee.shop.id}</td>
                            <td>${employee.shop.address}</td>
                        <#else>
                            <td>Закрито</td>
                            <td>Закрито</td>
                        </#if>
                        <td>${employee.isWorking?then("Работает", "Уволен")}</td>
                        <td>
                            <button type="button" class="btn btn-outline-dark" data-toggle="modal"
                                    data-target="#employeeEditModal${employee.id?c}">
                                <i class="fa fa-edit"></i>
                            </button>
                            <@editModal.employeeEdit id="${employee.id?c}" currentFirstName="${employee.firstName}"
                            currentSecondName="${employee.secondName}"
                            currentShopAddress="${(employee.shop??)?then(employee.shop.address, '')}"
                            currentWorkingStatus="${employee.isWorking?string('Работает', 'Уволен')}"/>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-danger" data-toggle="modal"
                                    data-target="#deleteModal${employee.id?c}">
                                <i class="fa fa-trash"></i>
                            </button>
                            <@deleteModal.delete id="${employee.id?c}"
                            deletionLink="/employee/delete/${employee.id?c}"/>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        <#else>
            <div class="h3">Не найдено ни одного сотрудника.</div>
        </#if>
    </div>
</@htmlPad.page>