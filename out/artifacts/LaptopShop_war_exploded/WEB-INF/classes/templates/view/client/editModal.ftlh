<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro clientEdit id clientFirstName clientSecondName >
    <div class="modal fade" id="clientEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="clientEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="clientEditLabel${id}">Изменить информацию о клиенте</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="clientEditForm${id}" action="/client/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="firstName">Имя изменяемого покупателя:</label>
                                <input type="text" id="editFirstNameInput${id}" class="form-control" name="editFirstName"
                                       required/>
                                <div class="invalid-feedback">Имя изменяемого покупателя задано некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="secondName">Фамилия изменяемого покупателя:</label>
                                <input type="text" id="editSecondNameInput${id}" class="form-control" name="editSecondName"
                                       required/>
                                <div class="invalid-feedback">Фамилия изменяемого покупателя задано некорректно</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editFirstNameInput${id}", "editSecondNameInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editFirstNameInput${id}", "editSecondNameInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="clientEditModal${id}" formId="clientEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#clientEditModal${id}').on('show.bs.modal', function () {
                            $("#editFirstNameInput${id}").val("${clientFirstName}").change();
                            $("#editSecondNameInput${id}").val("${clientSecondName}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="clientEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>