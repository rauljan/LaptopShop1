<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro userAdd>
    <div class="modal fade" id="userAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="userAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="userAddLabel">Добавить нового пользователя</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="userAddForm" action="/user/add" class="form-group needs-validation w-100"
                          novalidate modelAttribute="newUser">
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="newLoginInput">новый логин :</label>
                                <input type="text" id="newLoginInput" class="form-control" name="username" required/>
                                <div class="invalid-feedback">новый логин веден некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="emailInput">новая электронная почта:</label>
                                <input type="email" id="emailInput" class="form-control" name="email" required/>
                                <div class="invalid-feedback">новая электронная почта введена некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="newPasswordInput">новый пароль:</label>
                                <input type="password" id="newPasswordInput" class="form-control" name="password" required/>
                                <div class="invalid-feedback">новый пароль введен некорректно</div>
                            </div>
                        </div>
                        <div class="form-row mt-3 ml-2">
                            <div class="col">
                                <label class="radio control-label">новая роль:</label>
                                <#list roles as role>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" id="radio${role?index}" name="role"
                                               value="${role}" ${role?is_first?string("checked", "")} />
                                        <label class="form-check-label" for="radio${role?index}">
                                            ${role}
                                        </label>
                                    </div>
                                </#list>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["newLoginInput", "newPasswordInput"]/>
                    <@fC.inputsCleaner modalId="userAddModal" inputIds=["newLoginInput", "newPasswordInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="userAddModal" formId="userAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="userAddForm" class="btn btn-danger">Добавить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>