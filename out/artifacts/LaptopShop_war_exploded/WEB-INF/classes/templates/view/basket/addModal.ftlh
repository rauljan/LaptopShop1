<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/selectCleaner.ftlh" as sC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro basketAdd>
    <div class="modal fade" id="basketAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="basketAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="basketAddLabel">Добавить новую корзину</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <#if clientIds?has_content>
                    <#if employeeIds?has_content>
                        <div class="modal-body">
                            <form method="post" id="basketAddForm" action="/basket/add"
                                  class="form-group needs-validation w-100" novalidate>
                                <div class="form-row mb-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="employeeId">ID сотрудника:</label>
                                        <select class="custom-select" id="employeeSelect" name="employeeId" required>
                                            <option value="" selected disabled></option>
                                            <#list employeeIds as employeeId>
                                                <option>${employeeId}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не выбран ID сотрудника</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="clientId">ID покупателя:</label>
                                        <select class="custom-select" id="clientSelect" name="clientId" required>
                                            <option value="" selected disabled></option>
                                            <#list clientIds as clientId>
                                                <option>${clientId}</option>
                                            </#list>
                                        </select>
                                        <div class="invalid-feedback">Не выбран ID покупателя</div>
                                    </div>
                                </div>
                                <div class="form-row my-2">
                                    <div class="col">
                                        <label class="d-inline ml-2" for="dateTimeStr">Дата/время покупки:</label>
                                        <input type="datetime-local" id="dateTimeInput" min="2020-02-16T00:00" class="form-control"
                                               name="dateTime" required/>
                                        <div class="invalid-feedback">Некорректная дата/время покупки</div>
                                    </div>
                                </div>
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            </form>
                            <@fC.inputsCleaner modalId="basketAddModal"
                            inputIds=["dateTimeInput"]/>
                            <@sC.selectsCleaner modalId="basketAddModal"
                            selectIds=["employeeSelect", "clientSelect"]/>
                            <@fSc.fieldsStatusCleaner modalId="basketAddModal" formId="basketAddForm"/>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                            <button type="submit" form="basketAddForm" class="btn btn-danger">Добавить</button>
                        </div>
                    <#else>
                        <div class="modal-body">
                            <div class="alert alert-warning" role="alert">
                                <div class="h1 text-center">Не найдено ни одного  сотрудника.</div>
                                <div class="h3 text-center">
                                    <a href="/employee">Добавьте нового сотрудника</a> для создания корзины.
                                </div>
                            </div>
                        </div>
                    </#if>
                <#else>
                    <div class="modal-body">
                        <div class="alert alert-warning" role="alert">
                            <div class="h1 text-center">Не найдено ни одного  активного магазина.</div>
                            <div class="h3 text-center">
                                <a href="/shop">Добавьте новый магазин</a> для создания корзины.</div>
                        </div>
                    </div>
                </#if>
            </div>
        </div>
    </div>
</#macro>