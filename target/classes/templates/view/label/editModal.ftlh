<#import "../../errorsHandlers/emptyFieldsBreaker.ftlh" as eB>
<#import "../../errorsHandlers/valueConsistentBreaker.ftlh" as vC>
<#import "../../modalInputManage/formStatusCleaner.ftlh" as fSc>

<#macro labelEdit id labelBrand labelModel>
    <div class="modal fade" id="labelEditModal${id}" data-backdrop="static" tabindex="-1"
         role="dialog" aria-labelledby="labelEditLabel${id}" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-title h3" id="labelEditLabel${id}">Изменить наименование</div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form method="post" id="labelEditForm${id}" action="/label/edit/${id}"
                          class="form-group needs-validation w-100" novalidate onsubmit="return validateForm${id}()">
                        <div class="form-row mb-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="brand">Название бренда:</label>
                                <input type="text" id="editBrandInput${id}" class="form-control" name="editBrand" required/>
                                <div class="invalid-feedback">Название бренда задано некорректно</div>
                            </div>
                        </div>
                        <div class="form-row my-2">
                            <div class="col">
                                <label class="d-inline ml-2" for="model">Название изменяемой модели:</label>
                                <input type="text" id="editModelInput${id}" class="form-control" name="editModel" required/>
                                <div class="invalid-feedback">Название изменяемой модели задано некорректно</div>
                            </div>
                        </div>
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </form>
                    <@eB.emptyBreaker inputIds=["editBrandInput${id}", "editModelInput${id}"]/>
                    <@vC.valueBreaker id="${id}" inputIds=["editBrandInput${id}", "editModelInput${id}"]/>
                    <@fSc.fieldsStatusCleaner modalId="labelEditModal${id}" formId="labelEditForm${id}"/>

                    <!-- Restore initial value on opening of modal -->
                    <script>
                        $('#labelEditModal${id}').on('show.bs.modal', function () {
                            $("#editBrandInput${id}").val("${labelBrand}").change();
                            $("#editModelInput${id}").val("${labelModel}").change();
                        })
                    </script>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-dark" data-dismiss="modal">Отменить</button>
                    <button type="submit" form="labelEditForm${id}" class="btn btn-danger">Изменить</button>
                </div>
            </div>
        </div>
    </div>
</#macro>