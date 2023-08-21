<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro userEdit id currentUsername currentPassword activeStatus currentRole currentEmail>
    <div class="modal fade" id="userEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="userEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="userEditLabel${id}">Изменить информацию о пользователе</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="userEditForm${id}" action="/user/edit/${id}"
                          class="form-group needs-validation w-100" novalidate>
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="editUsernameInput${id}">Изменяемый логин :</label>
                                <input type="text" id="editUsernameInput${id}" class="form-control"
                                       name="editUsername" required/>
                                <div class="invalid-feedback">Изменяемый логин введен некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="emailInput${id}">Изменяемая электронная почта:</label>
                                <input type="email" id="emailInput${id}" class="form-control" name="editEmail" required/>
                                <div class="invalid-feedback">Измененная почта введена некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="editPasswordInput${id}">Изменяемый пароль:</label>
                                <input type="password" id="editPasswordInput${id}" class="form-control"
                                       name="editPassword" required/>
                                <div class="invalid-feedback">Измененный пароль введен некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <#assign isActiveChoises = ["Активный", "Неактивный"]>
                                <label class="d-inline ml-2" for="activeSelect${id}">Статус пользователя:</label>
                                <select class="custom-select" id="activeSelect${id}" name="editActive">
                                    <#list isActiveChoises as choice>
                                        <option value="${choice}">${choice}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-row mt-3 ml-2">
                            <div class="col">
                                <label class="radio control-label">Изменяемая роль:</label>
                                <#list roles as role>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="editRole"
                                               id="${role}${id}" value="${role}"/>
                                        <label class="form-check-label" for="${role}${id}">
                                            ${role}
                                        </label>
                                    </div>
                                </#list>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editUsernameInput${id}", "editPasswordInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="userEditModal${id}" formId="userEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $("#userEditModal${id}").on("show.bs.modal", function () {
                            $("#editUsernameInput${id}").val("${currentUsername}").change();
                            $("#activeSelect${id}").val("${activeStatus}").change();
                            $("#${currentRole}${id}").prop("checked", true);
                            $("#emailInput${id}").val("${currentEmail}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="userEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>