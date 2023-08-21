<#import "../../errorsHandlers/datesOrderBreaker.ftlh" as dO>
<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro employeeAdd>
    <div class="modal fade" id="employeeAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="employeeAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="employeeAddLabel">Добавить нового сотрудника</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if shopAddresses?has_content>
                    <div class="modal-body">
                        <form method="post" id="employeeAddForm" action="/employee/add"
                              class="form-group needs-validation w-100" novalidate
                              onsubmit="return validateFormAdd()">
                            <div class="form-row mb-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="firstName">Имя нового сотрудника:</label>
                                    <input type="text" id="newFirstNameInput" class="form-control" name="firstName"
                                           required/>
                                    <div class="invalid-feedback">Имя нового сотрудника задано некорректно</div>
                                </div>
                            </div>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="secondName">Фамилия нового сотрудника:</label>
                                    <input type="text" id="newSecondNameInput" class="form-control" name="secondName"
                                           required/>
                                    <div class="invalid-feedback">Фамилия нового сотрудника задано некорректно</div>
                                </div>
                            </div>
                            <div class="form-row my-2">
                                <div class="col">
                                    <label class="d-inline ml-2" for="shopAddress">Адрес магазина:</label>
                                    <select class="custom-select" id="shopSelect" name="shopAddress" required>
                                        <option value="" selected disabled></option>
                                        <#list shopAddresses as address>
                                            <option>${address}</option>
                                        </#list>
                                    </select>
                                    <div class="invalid-feedback">Не выбран магазин</div>
                                </div>
                            </div>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        </form>
                        <@eB.emptyBreaker inputIds=["newFirstNameInput", "newSecondNameInput"]/>
                        <@vC.valueBreaker id="Add" inputIds=["newFirstNameInput", "newSecondNameInput"]/>
                        <@sC.selectsCleaner modalId="employeeAddModal" selectIds=["shopSelect"]/>
                        <@fC.inputsCleaner modalId="employeeAddModal"
                        inputIds=["newFirstNameInput", "newSecondNameInput"]/>
                        <@fSc.fieldsStatusCleaner modalId="employeeAddModal" formId="employeeAddForm"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                        <button type="submit" form="employeeAddForm" class="btn btn-danger">Добавить</button>
                    </div>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не найдено ни одного активного магазина.</div>
                            <div class="h3 text-center">
                                <a href="/shop">Добавьте новый магазин</a> для создания сотрудника.
                            </div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>