<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/inputsCleaner.ftlh" as fC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro typeEdit id typeName>
    <div class="modal fade" id="typeEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="typeEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="typeEditLabel${id}">Изменить тип ноутбука</div>
                    <button type="button" class="close closeHeader" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="typeEditForm${id}" action="/type/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <label class="d-inline ml-2" for="editNameInput${id}">Название изменяемого типа:</label>
                        <input type="text" id="editNameInput${id}" class="form-control" name="editName" required/>
                        <div class="invalid-feedback">Название изменяемого типа задано некорректно</div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editNameInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editNameInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="typeEditModal${id}" formId="typeEditForm${id}"/>
                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#typeEditModal${id}').on('show.bs.modal', function () {
                            $("#editNameInput${id}").val("${typeName}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark closeFooter" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="typeEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>