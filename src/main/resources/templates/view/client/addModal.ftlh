<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro clientAdd>
    <div class="modal fade" id="clientAddModal" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="clientAddLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="clientAddLabel">Добавить нового клиента</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="clientAddForm" action="/client/add"
                          class="form-group needs-validation w-100"
                          novalidate modelAttribute="newClient" onsubmit="return validateFormAdd()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="firstName">Имя нового покупателя:</label>
                                <input type="text" id="newFirstNameInput" class="form-control" name="firstName"
                                       required/>
                                <div class="invalid-feedback">Имя нового покупателя задано некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="secondName">Фамилия нового покупателя:</label>
                                <input type="text" id="newSecondNameInput" class="form-control" name="secondName"
                                       required/>
                                <div class="invalid-feedback">Фамилия нового покупателя задано некорректно</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["newFirstNameInput", "newSecondNameInput"]/>
                    <@vC.valueBreaker id="Add" inputIds=["newFirstNameInput", "newSecondNameInput"]/>
                    <@fC.inputsCleaner modalId="clientAddModal"
                    inputIds=["newFirstNameInput", "newSecondNameInput", "newDateRegInput"]/>
                    <@fSc.fieldsStatusCleaner modalId="clientAddModal" formId="clientAddForm"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="clientAddForm" class="btn btn-danger">Добавить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>